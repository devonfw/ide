import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.wizards.datatransfer.ProjectConfigurator;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.wizards.datatransfer.SmartImportJob;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.jdt.internal.ui.workingsets.IWorkingSetIDs;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.internal.wizards.datatransfer.RecursiveImportListener;
import java.nio.file.Files;
import java.nio.file.Path;

class SysoutListener implements RecursiveImportListener {
  public void projectCreated(IProject project) {
    System.out.println("Created " + project);
  }

  public void projectConfigured(IProject project, ProjectConfigurator configurator) {
    System.out.println("Configured " + project + " using " + configurator);
  }

  public void errorHappened(IPath location, Exception ex) {
    System.out.println("Error: " + location);
    ex.printStackTrace();
  }        
}

class MyWorkbenchAdvisor extends org.eclipse.ui.application.WorkbenchAdvisor {
  public String getInitialWindowPerspectiveId() {
    return null;
  }
 
  private Set getOrCreateWorkingSets(Collection workingSetNames) {
    IWorkingSetManager workingSetManager = PlatformUI.getWorkbench().getWorkingSetManager();
    Set workingSets = new HashSet();
    for (String workingSetName : workingSetNames) {
      IWorkingSet workingSet = workingSetManager.getWorkingSet(workingSetName);
      if (workingSet == null) {
        workingSet = workingSetManager.createWorkingSet(workingSetName, new IProject[0]);
        workingSet.setId(IWorkingSetIDs.RESOURCE);
        workingSetManager.addWorkingSet(workingSet); 
      }
      workingSets.add(workingSet); 
    }         
    return workingSets;
  }
  
  private void doImport(String projectDirectoryName, Collection workingSetNames) {
    File projectDirectory = new File(projectDirectoryName);
    if (projectDirectory == null) {
      throw new IllegalStateException("Cannot open " + projectDirectoryName);
    }
    SmartImportJob job = new SmartImportJob(projectDirectory, getOrCreateWorkingSets(workingSetNames), true, true);
    Map proposals = job.getImportProposals(null);
    job.setDirectoriesToImport(proposals.keySet());
    job.setListener(new SysoutListener());
    job.schedule();
    job.join();
  }
  
  private Map parseImportConfig(String configFileLocation) {
    System.out.println("Parsing config " + configFileLocation);
    Properties configProperties = new Properties();
    configProperties.load(Files.newInputStream(Path.of(configFileLocation).toAbsolutePath()));
    Map configMap = new HashMap();
    for(String key : configProperties.keySet()) {
      if (!key.startsWith("path.")) {
        continue;
      }
      if (!key.split("\\.").length == 2) {
        System.out.println("Config key " + key + " is not of expected format path.N - skipping");
        continue;
      }
      int number = Integer.parseInt(key.split("\\.")[1]);
      Path path = Path.of(configProperties.get("path." + number));
      if (!path.isAbsolute()) {
        // If path is relative, create path relative to configFileLocation
        path = Path.of(Path.of(configFileLocation).getParent().toString(), path.toString()).toAbsolutePath();
      }
      Collection workingSetNames = Collections.EMPTY_LIST;
      String workingSetConfig = configProperties.get("workingsets." + number);
      if (workingSetConfig != null) {
        workingSetNames = Arrays.asList(workingSetConfig.split(","));
      }
      configMap.put(path, workingSetNames);
    }
    System.out.println("Found " + configMap.keySet().size() + " configs.");
    return configMap;
  }       
  
  public void preStartup() {
    try {
      String configFileLocation = System.getenv("DEVON_IMPORT_CONFIG");
      if (configFileLocation == null) {
        throw new IllegalStateException("Systemproperty DEVON_IMPORT_CONFIG must be set.");
      }
      Map importConfigMap = parseImportConfig(configFileLocation);
      for (Map.Entry e : importConfigMap.entrySet()) {
        String path = e.getKey();
        Collection workingSetNames = e.getValue();   
        System.out.println("Importing " + path + " for working sets " + workingSetNames);       
        doImport(path, workingSetNames);              
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  public void postStartup() {
    PlatformUI.getWorkbench().close();           
  }
}

// Groovy Script startes here...
System.out.println("Starting eclipse instance for import...");

// We are running in the context of antrunner. Import, Workingset etc. need some
// parts of the normale IDE infratructure running. So we init it here.

// Register Adapters, e.g. to allow persistence of workingsets work (see https://bugs.eclipse.org/bugs/show_bug.cgi?id=513188)
org.eclipse.ui.ide.IDE.registerAdapters();

// Create Workbench, this make the eclispe windows appear, but it is necessary for the importer job.
display = PlatformUI.createDisplay();
PlatformUI.createAndRunWorkbench(display, new MyWorkbenchAdvisor());
System.out.println("End.");