# FlexDock
## A Java Swing docking framework
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

*This is a fork of the original flexdock library [opencollab/flexdock](https://github.com/opencollab/flexdock)*

# Original readme

This is the readme for the binary distribution.  If you want to use the
sources to build and run flexdock demos, then see README-BUILD.

Contents
------------
The contents of the binary distributions are:

  LICENSE.txt                   the license statement
  README                        this file
  flexdock-<version>.jar        the flexdock framework
  flexdock-demo-<version>.jar   runnable jar containing demo apps
  release-notes.txt             list of changes, fixes and new features
  docs\*                        user/developer documentation
  docs\apis\...                 javadocs
  jmf\*							Java Media Framework used by the demo
  lib\*							libraries used by this distribution

Running the Demos
---------------------
The demo jar requires both the flexdock-<version>.jar and lib\*.jar.  The
manifest for the demo expects the flexdock-<version>.jar to be in the same
directory and all other dependencies to be located in a subdirectory lib.
If you do not move any of the jars, you can run the demo jar using:

  java -jar flexdock-demo-<version>.jar

or if your GUI shell supports running a jar file by clicking or double
clicking on it's icon, then do that.
