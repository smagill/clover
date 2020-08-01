#!/usr/bin/env bash

set -e

if [ -z $1 ];then
  echo "ERROR: Issue directory not provided!"
  exit -1
fi

ISSUE_DIR=$1
if [ ! -d ${ISSUE_DIR} ];then
    echo "ERROR: Issue directory not found: ${ISSUE_DIR}"
    exit -1
fi

FONT_FILE=${HOME}/cossmass/binder/fonts/Snowslider/SnowSL_Std.OTF
if [ ! -f ${FONT_FILE} ];then
    echo "ERROR: Font file not found: ${FONT_FILE}"
    exit -1
fi

JAR=${PWD}/runner/target/clover-runner.jar
if [ ! -f ${JAR} ];then
    echo "ERROR: Jar file not found: ${JAR}"
    exit -1
fi

(
    cd ${ISSUE_DIR}
    java -Dfont-file=${FONT_FILE} -jar ${JAR}
)
