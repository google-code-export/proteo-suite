--------------------------------------------------------------------
P R O T E O S U I T E   Ver 0.2.0                                                       
--------------------------------------------------------------------
 Software for Analysis of Quantitative Proteomics Data (Ver 0.2.0)
--------------------------------------------------------------------

---------------------
1. About ProteoSuite
---------------------

ProteoSuite is an open source framework for the analysis of quantitative proteomics data. 
The aim of the software is to provide bench scientists with an application to analyse their 
data using the most common techniques and standards.

More information about ProteoSuite can be found at:
http://www.proteosuite.org

---------------------
2. License
---------------------

This software is released under the Apache 2.0 license which means that it can 
be used for commercial and non-commercial purposes.
See Apache 2.0 license for further details. 
http://www.apache.org/licenses/LICENSE-2.0.html

---------------------
3. Installation
---------------------

ProteoSuite can run in multiple platforms (Windows, Linux and Mac). 
Use one of the following set of instructions depending on your operating system.

----------
Windows:
----------
i) Download the latest release from 
   http://code.google.com/p/proteo-suite/downloads/list

ii)Copy the .jar file into one of your local drives and create a folder for the program. 
   e.g. C:\ProteoSuite

iii) Locate and execute (double click) the jar file (proteosuite-X.X.X.jar) to start ProteoSuite.

Tip: You can create a shortcut to this file and place it into your desktop.

----------
Linux:
----------
i) Download the latest release from 
   http://code.google.com/p/proteo-suite/downloads/list

ii) Copy the .jar file into one of your local drives and create a folder for the program. 

iii) Create a shortcut (launcher) to ProteoSuite and place it into your desktop.
	i.e. Right click on your desktop. Then "Create Launcher", 
	Enter the following information for the launcher:
		Type: Application
		Name: ProteoSuite
		Command: java -jar /home/your_user_account/Downloads/proteosuite-X.X.X.jar
		Comment: ProteoSuite   
iv) Double click on the launcher. 
   
----------
MacOS:
----------

------------------------------------------------------
//  The next section is only intended for programmers
------------------------------------------------------

---------------------
4. Source code 
---------------------

This project has been developed using NetBeans 7.0.1. We have used Maven to manage
dependencies. 

Note: ProteoSuite uses xTracker for quantitation methods. 
      See xTracker documentation under: 
	  http://code.google.com/p/x-tracker/
	  http://www.x-tracker.info/	  

---------------------
5. SVN content
---------------------

Directories available on the SVN public repository 
(http://code.google.com/p/proteo-suite/source/browse/):

Folder/File							Description								Comments
-------------------------------		-------------------------------			-------------------------------
* Plugins							xTracker XSD files						Needed for running quantitation
* src								ProteoSuite source code					Needed
* config.xml						ProteoSuite configuration file			Needed for default settings
* pom.xml							ProteoSuite maven Project Object Model	Needed
* README.txt						This file								Not needed for compiling
* xtracker.xsd						xTrcaker XSD files						Needed for running quantitation

Maven manual dependencies:
As GUI releases are usually modified by programmers, we have selected several stable versions.
The following libraries need to be installed manually:
* jcommon-1.0.16.jar			jfree.org		(For general charts)
* jfreechart-1.0.13.jar			jfree.org		(For general charts)
* utilities-3.0.16.jar			compomics.org	(For GUIs, spectrum and chromatogram)
* x-Tracker-1.0-SNAPSHOT.jar	xtracker.info	Visit http://code.google.com/p/x-tracker/

Note: These libraries are included under src/main/resources/lib/

---------------------
6. Technical support
----------------------

In case of technical problems please consult the documentation available at:
     http://www.proteosuite.org/
or send an e-mail to:
     support@proteosuite.org


