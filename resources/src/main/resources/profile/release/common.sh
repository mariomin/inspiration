#!/bin/bash

function startup_application {
BASEDIR=$(cd `dirname $0`; pwd)
MAIN=$1
CONFIG=$2
SYSTEM=$3

create_log_directory
get_delimiter
import_extension
java -server -d64 -Xms4G -Xmx8G -cp "${BASEDIR}/bin/*${DELIMITER}${BASEDIR}/lib/*${DELIMITER}${BASEDIR}/conf/*${DELIMITER}${BASEDIR}/conf/common/*${DELIMITER}" -Dlocation="${BASEDIR}" -Dlog4j.configurationFile="${BASEDIR}/conf/common/log4j2.xml" -Dats.home="${BASEDIR}" -Dats.type="${SYSTEM}" ${MAIN} ${CONFIG} >> ${LOGS}/screen.log &
echo "starting ${SYSTEM}"
echo "config: ${BASEDIR}/${CONFIG}"
echo "log: ${LOGS}/app.log"
}


function kill_processor {
BASEDIR=$(cd `dirname $0`; pwd)
SYSTEM=$1
OS=`uname`

if  [[ ${OS} =~ "Linux" ]] 
then
	PROCESS=`ps -ef | grep ${BASEDIR} | grep ${SYSTEM} | grep -v stop | awk '{print $2}'`
	for i in ${PROCESS}
	do
		echo "kill ${SYSTEM} process id: $i, location: ${BASEDIR}"
		kill -9 $i
	done
else
	echo "This script isn't for Windows, please kill processes manually."
fi
}

function startup_web {
import_extension
get_script_suffix_name
SCRIPT=/bin/startup${SUFFIX}
DIR=${BASEDIR}/web/tomcat

cd ${DIR}
./${SCRIPT}
}


function execute_script {
BASEDIR=$(cd `dirname $0`; pwd)
MAIN=$1
CONFIG=$2
DATASOURCE=$3
SYSTEM=$4

create_log_directory
get_delimiter

java -server -d64 -Xms1G -Xmx2G -cp "*${DELIMITER}${BASEDIR}${DELIMITER}${BASEDIR}/bin/*${DELIMITER}${BASEDIR}/lib/*${DELIMITER}${BASEDIR}/conf/*${DELIMITER}${BASEDIR}/conf/common/*" -Dlog4j.configurationFile="${BASEDIR}/conf/common/log4j2.xml" -Dats.home="${BASEDIR}" -Dats.type="${SYSTEM}" ${MAIN} ${CONFIG} ${DATASOURCE} >> ${LOGS}/screen.log &
echo "starting ${SYSTEM}"
echo "config: ${BASEDIR}/${CONFIG}"
echo "log: ${LOGS}/app.log"
}


function get_script_suffix_name {
OS=`uname`

if  [[ ${OS} =~ "Linux" ]] 
then 
	eval "SUFFIX='.sh'"
else
	eval "SUFFIX='.bat'"
fi
}


function create_log_directory {
BASEDIR=$(cd `dirname $0`; pwd)
LOGS=${BASEDIR}/log/${SYSTEM}

if [ ! -d ${LOGS} ]
then
        mkdir -p ${LOGS}
fi
}

function get_delimiter {
OS=`uname`

if  [[ ${OS} =~ "Linux" ]] 
then 
	DELIMITER=':'
else
	DELIMITER=';'
fi
}


function import_extension {
get_delimiter
export LD_LIBRARY_PATH=${BASEDIR}/ext/linux_x64/${DELIMITER}${BASEDIR}/ext/windows_x64/
}
