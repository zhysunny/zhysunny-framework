#!/bin/sh
   
# Author      : 章云
# Date        : 2019/9/27 10:46
# Description : 配置脚本
#   启动时running设置为true，当running=true时，进程宕机将执行startup.sh，否则不执行
#   关闭时running设置为false

running=false

source /etc/profile

if [[ -z "${JAVA_HOME}" ]]
then
    echo "JAVA_HOME is empty"
    exit 1
fi
JAVA_EXEC=${JAVA_HOME}/bin/java
JPS_EXEC=${JAVA_HOME}/bin/jps

# 进程名
APP_NAME="KafkaToEs"
# 主函数路径
CLASS_NAME="com.znv.fss.${APP_NAME}"
# 日志目录
LOG_PATH=${LOCAL_PATH}/logs
if [[ ! -d ${LOG_PATH} ]]
then
    mkdir -p ${LOG_PATH}
fi

export APP_NAME
export CLASS_NAME
export LOG_PATH