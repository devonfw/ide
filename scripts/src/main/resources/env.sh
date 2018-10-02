#!/bin/bash
#
# Source this script from a BASH shell to setup the shell for the corresponding project.
# 

pushd "$(dirname "$BASH_SOURCE")" >/dev/null
export OASP_PROJECT_HOME=`pwd`

. $OASP_PROJECT_HOME/scripts/environment-project

popd > /dev/null