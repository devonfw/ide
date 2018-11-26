#!/bin/bash
#cd `dirname $0`

if [ "$1" == "" ]; then

	export WORKSPACE=$MAIN_BRANCH
	export MODE="-c"

. scripts/environment-project


   if [ ! -d $WORKSPACE_PLUGINS_PATH ]; then
        echo "Could not find workspace $WORKSPACE"
	echo "Update aborted"
	exit 1
   fi

fi

if [ "$1" == "--new" ]; then
	export MODE="-cn"


	        if [ "$2" == "" ]; then
		export WORKSPACE=$MAIN_BRANCH

	        else
		export WORKSPACE=$2
                fi

	
 else
	export WORKSPACE=$1
	export MODE="-c"

fi



. scripts/environment-project


if [ ! -d $WORKSPACE_PLUGINS_PATH ]; then
    echo "Could not find workspace $WORKSPACE"
	echo "Update aborted"
	end
    exit 1
fi
   


java -jar $SCRIPTS_PATH/$ECLIPSE_CONFIGURATOR $MODE





