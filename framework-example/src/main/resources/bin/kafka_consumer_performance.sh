#!/bin/sh
   
# Author      : 章云
# Date        : 2020/1/8 10:08
# Description : Kafka生产者性能测试

# 当前文件所在目录，这里是相对路径
LOCAL_PATH=`dirname $0`
# 当前文件所在目录转为绝对路径
LOCAL_PATH=`cd ${LOCAL_PATH}/../;pwd`

source /etc/profile

if [[ -z "${JAVA_HOME}" ]]
then
    echo "JAVA_HOME is empty"
    exit 1
fi
JAVA_EXEC=${JAVA_HOME}/bin/java
JPS_EXEC=${JAVA_HOME}/bin/jps
# 进程名
APP_NAME="PerformanceConsumerMain"
# 主函数路径
CLASS_NAME="com.zhysunny.framework.example.performance.kafka.consumer.${APP_NAME}"

# 设置 classpath
CLASSPATH=${CLASSPATH}:${LOCAL_PATH}

for f in ${LOCAL_PATH}/lib/*.jar; do
    CLASSPATH=${CLASSPATH}:${f}
done

# java 参数
JAVA_OPTS="-Xmx2g -Xms2g -Xmn1g"

cd ${LOCAL_PATH}
${JAVA_EXEC} ${JAVA_OPTS} -classpath ${CLASSPATH} ${CLASS_NAME} $*

kill -2 $$
exit 0