#!/bin/sh
   
# Author      : 章云
# Date        : 2019/9/27 10:42
# Description : 关闭进程

# 当前文件所在目录，这里是相对路径
LOCAL_PATH=`dirname $0`
# 当前文件所在目录转为绝对路径
LOCAL_PATH=`cd ${LOCAL_PATH}/../;pwd`
# 加载环境变量
source ${LOCAL_PATH}/bin/framework-env.sh

PID=`${JPS_EXEC} | grep -v "grep" | grep ${APP_NAME} | awk '{print $1}'`
if [[ -z "${PID}" ]]
then
    echo "${APP_NAME} is not running"
else
    kill ${PID}
    echo "Stopping ${APP_NAME} success"
fi

kill -2 $$
exit 0