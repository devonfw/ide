// Module to manage file system
var fs = require('fs');

// Properties
var SETTINGS_PATH = "conf/.m2/settings.xml";
var S2_SERVER = "<!-- S2_server -->";
var S2_PROFILE = "<!-- S2_profile -->";
var S2_ACTIVEPROFILE = "<!-- S2_activeProfile -->";

// Script arguments
var S2_USER = process.argv[2];
var S2_PASS = process.argv[3];
var EngagementName = process.argv[4];
var ciaas = process.argv[5];

console.log("\n *** CONFIGURATION PARAMETERS ***" + 
			"\n User: " + S2_USER + 
			"\n Password: " + S2_PASS+ 
			"\n Engagement Name: " + EngagementName + 
			"\n Configuring for CIAAS: " + ciaas + 
			"\n ********************************\n");

checkParameters(S2_USER, S2_PASS, EngagementName);

// Managing the settings.xml file
if(fileExists(SETTINGS_PATH)){
	configureSettingsXml();
}else{
	throw ("The "+ SETTINGS_PATH+ " can not be found.");
}





// FUNCTIONS --------------------------------------------------------
function fileExists(path){
	try{
		fs.statSync(path);
		return true;
	}catch(err){
		return false;
	}
}

function checkParameters(user, pass, engagementName){
	if (!user) throw ("Missing user parameter.");
	if (!pass) throw ("Missing password parameter.");
	if (!engagementName) throw ("Missing Engagement-name parameter.");
}

function checkCompatibility(content){
	if (content.indexOf(S2_SERVER) > -1 &&
		content.indexOf(S2_PROFILE) > -1 &&
		content.indexOf(S2_ACTIVEPROFILE) > -1){
		return true;
	}else{
		return false;
	}	
}

function configureSettingsXml(){
	console.log("[INFO] settings.xml file found...");
	var settingsContent = fs.readFileSync(SETTINGS_PATH, "utf8");

	if(checkCompatibility(settingsContent)){
		console.log("[INFO] settings.xml is compatible with autoconfiguration of the connection...");

		if (ciaas == "true"){
			configureSettingsWithCiaas(settingsContent);
		}else{
			configureSettings(settingsContent);
		}

		console.log("[INFO] settings.xml for Shared Services configured successfully.");
			
	}else{
		throw ("The settings.xml seems not to be compatible with Shared Services autoconfiguration or is already configured.");
	}
}

function configureSettingsWithCiaas(content){

	content = content.replace(S2_SERVER, injectCIAASservers(S2_USER, S2_PASS));

	content = content.replace(S2_PROFILE, injectCIAASprofiles(EngagementName));

	content = content.replace(S2_ACTIVEPROFILE, injectCIAASactiveProfile());

	fs.writeFileSync(SETTINGS_PATH, content);
}

function configureSettings(content){

	content = content.replace(S2_SERVER, injectS2server(S2_USER,S2_PASS));

	content = content.replace(S2_PROFILE, injectS2profile(EngagementName));

	content = content.replace(S2_ACTIVEPROFILE, injectS2activeProfile());

	fs.writeFileSync(SETTINGS_PATH, content);
}

function injectS2server(user, pass){
	return "\t<server>\n" +
	    		"\t\t<id>mrm.apps2.capgemini.com</id>\n" +
        		"\t\t<username>"+user+"</username>\n" + 
        		"\t\t<password>"+pass+"</password>\n" +
			"\t</server>";
}

function injectS2profile(engagementName){
	return "\t<profile>\n" +
    			"\t\t<id>s2</id>\n" +
    			"\t\t<repositories>\n" +  
      				"\t\t\t<repository>\n" +
        				"\t\t\t\t<id>mrm.apps2.capgemini.com</id>\n" +
        				"\t\t\t\t<url>http://mrm.apps2.capgemini.com/"+engagementName+"</url>\n" +
      				"\t\t\t</repository>\n" +
    			"\t\t</repositories>\n" +
			"\t</profile>";
}

function injectS2activeProfile(){
	return "\t<activeProfile>s2</activeProfile>";
}

function injectCIAASservers(user, pass){
	return "\t<server>\n" +
      			"\t\t<username>"+user+"</username>\n" +
      			"\t\t<password>"+pass+"</password>\n" +
      			"\t\t<id>central</id>\n" +
    		"\t</server>\n" +
    		"\t<server>\n" +
      			"\t\t<username>"+user+"</username>\n" +
      			"\t\t<password>"+pass+"</password>\n" +
      			"\t\t<id>snapshots</id>\n" +
    		"\t</server>";
}

function injectCIAASprofiles(engagementName){
	return "\t<profile>\n" +
      			"\t\t<repositories>\n" +
        			"\t\t\t<repository>\n" +
          				"\t\t\t\t<snapshots>\n" +
            				"\t\t\t\t\t<enabled>false</enabled>\n" +
          				"\t\t\t\t</snapshots>\n" +
          				"\t\t\t\t<id>central</id>\n" +
          				"\t\t\t\t<name>"+engagementName+"</name>\n" +
          				"\t\t\t\t<url>http://mrm.apps2.capgemini.com/"+engagementName+"</url>\n" +
        			"\t\t\t</repository>\n" +
        			"\t\t\t<repository>\n" +
          				"\t\t\t\t<snapshots />\n" +
          				"\t\t\t\t<id>snapshots</id>\n" +
          				"\t\t\t\t<name>"+engagementName+"</name>\n" +
          				"\t\t\t\t<url>http://mrm.apps2.capgemini.com/"+engagementName+"</url>\n" +
        			"\t\t\t</repository>\n" +
      			"\t\t</repositories>\n" +
      			"\t\t<pluginRepositories>\n" +
        			"\t\t\t<pluginRepository>\n" +
          				"\t\t\t\t<snapshots>\n" +
            				"\t\t\t\t\t<enabled>false</enabled>\n" +
          				"\t\t\t\t</snapshots>\n" +
          				"\t\t\t\t<id>central</id>\n" +
          				"\t\t\t\t<name>"+engagementName+"</name>\n" +
          				"\t\t\t\t<url>http://mrm.apps2.capgemini.com/"+engagementName+"</url>\n" +
        			"\t\t\t</pluginRepository>\n" +
        			"\t\t\t<pluginRepository>\n" +
          				"\t\t\t\t<snapshots />\n" +
          				"\t\t\t\t<id>snapshots</id>\n" +
          				"\t\t\t\t<name>"+engagementName+"</name>\n" +
          				"\t\t\t\t<url>http://mrm.apps2.capgemini.com/"+engagementName+"</url>\n" +
        			"\t\t\t</pluginRepository>\n" +
      			"\t\t</pluginRepositories>\n" +
      			"\t\t<id>artifactory</id>\n" +
    		"\t</profile>";
}

function injectCIAASactiveProfile(){
	return "\t<activeProfile>artifactory</activeProfile>";
}
