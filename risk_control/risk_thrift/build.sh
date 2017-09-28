#!/bin/bash
export JAVA_HOME=/home/work/jdk-1.7
export MAVEN_HOME=/home/work/apache-maven-3.3.3
export PATH=${JAVA_HOME}/bin:${MAVEN_HOME}/bin:${PATH}

cd `dirname $0`
BUILD_DIR=`pwd`

echo $BUILD_DIR

function check_error()
{
    if [ ${?} -ne 0 ]
    then
        echo "Error! Please Check..."
        exit 1
    fi
}
PROFILE=online

rm -rf output
mkdir output

if [ $# -ne 0 ]; then
    PROFILE=$1
fi

mvn clean compile package -U -P$PROFILE -Dmaven.test.skip=true 
check_error

mv ./target/bin ./target/lib ./target/conf  ./output/
