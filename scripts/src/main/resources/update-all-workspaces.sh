#! /bin/bash
cd `dirname $0`
cd workspaces

for file in *
do
  if [ -d "$file" ]
  then
   ../create-or-update-workspace "$file"
  fi
done

cd ../workspaces_vs

for file in *
do
  if [ -d "$file" ]
  then
   ../create-or-update-workspace-vs "$file"
  fi
done
