pentaho-plugin-skeletons
========================

This repo contains skeleton projects providing boilerplate files for easily creating plugins for Pentaho products. With the advent of SPARKL, this project will likely only house desktop client plugins, like those for PDI.

Anyone wishing to contribute to this project should offer as much inline commenting and code snippets as possible, as well as at least one build mechanism, preferably the one supported by Pentaho at the time. Currently this is Ant/Ivy/Subfloor, but may change to something like Maven in the future.  Having said that, the pdi-step-skeleton subproject uses Gradle, but will be easy to Maven-ize.
