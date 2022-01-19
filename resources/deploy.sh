#!/bin/bash

if [[ $# -lt 1 ]]; then
    printf "\nUsage: deploy.sh <installation_path>\n\n"
    exit
fi

INSTALLPATH=$1

if [ -d $INSTALLPATH ]; then
  rm -rf $INSTALLPATH
fi

mkdir $INSTALLPATH
mkdir $INSTALLPATH/resources
mkdir $INSTALLPATH/outputs
cp -ivr /Users/mlautman/Documents/programming/java/paligo_utilities/out/artifacts/paligo_utilities_jar/* $INSTALLPATH/
cp -ivr /Users/mlautman/Documents/programming/java/paligo_utilities/resources/* $INSTALLPATH/resources/
echo "All done!"
echo "Run with"
echo "cd $1 && java -jar paligo_utilities.jar && cd .."