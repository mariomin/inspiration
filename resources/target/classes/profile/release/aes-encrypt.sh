#!/bin/bash
BASEDIR=$(cd `dirname $0`; pwd)
java -server -d64 -Xms128M -Xmx256M -cp "${BASEDIR}/../*:${BASEDIR}/../bin/*:${BASEDIR}/../lib/*:" com.huatai.common.AESEncrypt $1 $2 &