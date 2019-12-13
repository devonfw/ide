package com.devonfw.java.modules.swttest;

import java.io.File;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * General Eclipse Plug-in Tests
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class Devon4jAppTest {

  private static SWTWorkbenchBot bot;

  /** Root path of the projects */
  private static String path = null;

  /**
   * Initialization of bot instance and directory path.
   */
  @BeforeClass
  public static void initBot() {

    bot = new SWTWorkbenchBot();
    // Project should be inside the Directory(directory name)/temp/projects
    File currDir = new File("\\temp\\projects");
    path = currDir.getAbsolutePath();
  }

  /**
   * Import devon4jApp from given path
   */

  @Test
  public void importJavaProject() {

    bot.menu("File").menu("Import...").click();

    bot.tree().expandNode("Maven").select("Existing Maven Projects");
    bot.button("Next >").click();
    bot.comboBoxWithLabel("Root Directory:").setText(path);
    bot.button("Refresh").click();
    bot.button("Finish").click();
    bot.toolbarButtonWithTooltip("&Restore").click();
    bot.waitUntil(new AllJobsAreFinished(), 100000);
    runProjectTestCases();

  }

  private void runProjectTestCases() {

    bot.tree().getTreeItem("devon4japp-core").select();
    bot.tree().getTreeItem("devon4japp-core").expand();

    bot.tree().getTreeItem("devon4japp-core").getNode("src/test/java").expand();
    bot.tree().getTreeItem("devon4japp-core").getNode("src/test/java")
        .getNode("com.devon4japp.devon4japp.general.service.impl.rest").expand();
    bot.waitUntil(new AllJobsAreFinished(), 10000);
    bot.tree().getTreeItem("devon4japp-core").getNode("src/test/java")
        .getNode("com.devon4japp.devon4japp.general.service.impl.rest").getNode("SecurityRestServiceImplTest.java")
        .select().contextMenu("Run As").menu("1 JUnit Test").click();
    bot.waitUntil(new AllJobsAreFinished(), 10000);
  }

  /**
   * Reset bot workbench
   */
  @AfterClass
  public static void afterClass() {

    bot.resetWorkbench();
  }

}