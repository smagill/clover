#!/usr/bin/env bash

set -e

if test $# != 1
then
  echo "ERROR: version missing"
  exit 255
fi

VERSION=$1

for P in . parent
do
  mvn versions:set -DnewVersion=$VERSION -pl $P
done
