Refactoring of Hudson.java using the Mikado Method
==================================================

Inspired by Gojko Adzic's criticism of the unnecessary use and overuse of a singleton
in Hudson/Jenkins, see http://gojko.net/2011/04/05/how-is-it-even-possible-code-to-be-this-bad/

For a brief intro into the Mikado Method and motivation behind this dojo see
http://theholyjava.wordpress.com/2011/04/16/refactoring-the-legacy-hudson-java-with-the-mikado-method-as-a-code-dojo/

Setup
-----

A verified but not necessarily the best process.

Prerequisities
..............
You need to get the dependencies that jenkins-core depends upon, namely external ones and the jenkins modules it needs:

First get all dependencies of jenkins::

	~$ cd coding-dojo/2011-04-26-refactoring_hudson
	2011-04-26-refactoring_hudson$ mvn -DskipTests  install
	
* This may take quite a while, e.g. over half an hour (N + 5m), based on the load of the repository and your connection
* It may also fail due to timeouts - try multiple times and at different times
* You actually don't need the build to succeed, it suffices if it gets to the point where it is able to produce the jenkins-core jar

In Eclipse:
...........

#. Import - Maven - Existing Maven Projects - select the core/ folder and its pom.xml => project jenkins-core created
#. Make sure that the projects' Compiler compliance level is equal to or higher than 1.5
#. Build the project from the command line to produce generate sources::

	core$ mvn compile

#. ... refresh the project and add the source folders ``target/generated-sources/localizer/`` and ``target/generated-sources/antlr/``

#. Now you may want to verify that all works by running ``mvn test`` from the command line
