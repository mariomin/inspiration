#!/bin/bash

BASEDIR=$(cd `dirname $0`; pwd)
source ${BASEDIR}/common.sh

startup_application "com.huatai.platform.Platform" "conf/platform/platform.xml" "platform"