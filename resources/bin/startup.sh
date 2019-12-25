#!/bin/sh
   
# Author      : 章云
# Date        : 2019/9/19 10:49
# Description : 启动脚本

# 当前文件所在目录，这里是相对路径
LOCAL_PATH=`dirname $0`
# 当前文件所在目录转为绝对路径
LOCAL_PATH=`cd ${LOCAL_PATH}/../;pwd`
# 加载环境变量
source ${LOCAL_PATH}/bin/framework-env.sh

#保留gc日志文件
GC_FILE="${LOG_PATH}/gc.${APP_NAME}.log"
GC_LOG_OPTS="-XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:${GC_FILE} "
if [[ -e ${GC_FILE} ]]
then
    mv ${GC_FILE} ${LOG_PATH}/gc.${APP_NAME}_$(date +"%Y%m%d").log  #重启程序会重置这个文件，这里做个保留
fi

#保留hprof文件
DUMP_FILE="${LOG_PATH}/znv-fss-${APP_NAME}.hprof "
DUMP_LOG_OPTS="-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${DUMP_FILE} "
if [[ -e ${DUMP_FILE} ]]
then
    mv ${DUMP_FILE} ${LOG_PATH}/znv-fss-${APP_NAME}_$(date +"%Y%m%d").hprof  #重启程序这个文件无法覆盖，这里要重命名
fi

# 设置 classpath
CLASSPATH=${CLASSPATH}:${LOCAL_PATH}
for f in ${LOCAL_PATH}/fss-*.jar; do
    CLASSPATH=${CLASSPATH}:${f}
done

for f in ${LOCAL_PATH}/lib/*.jar; do
    CLASSPATH=${CLASSPATH}:${f}
done

# java 参数
JAVA_OPTS="-Xmx4g -Xms4g -Xmn3g -Dlog4j.configuration=file:${LOCAL_PATH}/conf/log4j.xml "${GC_LOG_OPTS}${DUMP_LOG_OPTS}
# 启动进程
function startup() {
    PID=`${JPS_EXEC} | grep -v "grep" | grep ${APP_NAME} | awk '{print $1}'`
    if [[ -z "${PID}" ]]
    then
        {
        nohup ${JAVA_EXEC} ${JAVA_OPTS} -classpath ${CLASSPATH} ${CLASS_NAME} 1>/dev/null 2>${LOG_PATH}/kafka-es-error.out
        }&
    else
        echo "${APP_NAME} running as process ${PID}.Stop it first."
        exit 0
    fi
    #判断程序是否启动
    i=0
    while((i<10))
    do
        PID=`${JPS_EXEC} | grep -v "grep" | grep ${APP_NAME} | awk '{print $1}'`
        if [[ -z "${PID}" ]]
        then
            sleep 1s
            i=$(($i+1))
        else
            echo "Starting ${APP_NAME} success as process ${PID}"
            break
        fi
    done
    PID=`${JPS_EXEC} | grep -v "grep" | grep ${APP_NAME} | awk '{print $1}'`
    if [[ -z "${PID}" ]]
    then
        echo "Starting ${APP_NAME} failed!"
        exit 1
    fi
}

startup

kill -2 $$
exit 0
