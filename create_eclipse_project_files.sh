#!/bin/sh

# NOTE: the mule-3.7.0 tests does not work in eclipse if eclipse project
#   files are created from top level project, due to different classpaths.
mvn clean install -DskipTests
mvn eclipse:clean eclipse:eclipse
cd mule-331-test;mvn eclipse:clean eclipse:eclipse;cd -
cd mule-370-with-patch-test;mvn eclipse:clean eclipse:eclipse;cd -
cd mule-370-without-patch-test;mvn eclipse:clean eclipse:eclipse;cd -
