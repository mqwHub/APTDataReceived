!/bin/bash
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`
CONF_DIR=$DEPLOY_DIR/conf
LIB_DIR=$DEPLOY_DIR/lib
LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`
STDOUT_FILE=$LOGS_DIR/stdout.log
JAVA_MEM_OPTS=""
BITS=`java -version 2>&1 | grep -i 64-bit`
if [ -n "$BITS" ]; then
    JAVA_MEM_OPTS=" -server 
    -Xmx2g 
    -Xms2g 
    -Xmn256m 
    -XX:PermSize=128m 
    -Xss256k 
    -XX:+DisableExplicitGC 
    -XX:+UseConcMarkSweepGC 
    -XX:+CMSParallelRemarkEnabled 
    -XX:+UseCMSCompactAtFullCollection 
    -XX:LargePageSizeInBytes=128m 
    -XX:+UseFastAccessorMethods 
    -XX:+UseCMSInitiatingOccupancyOnly 
    -XX:CMSInitiatingOccupancyFraction=70
    -XX:+PrintGCDetails 
    -Xloggc:$DEPLOY_DIR/gc.log
    -XX:+HeapDumpOnOutOfMemoryError 
    -XX:HeapDumpPath=$DEPLOY_DIR "
else
    JAVA_MEM_OPTS=" -server -Xms1g -Xmx1g -XX:PermSize=128m -XX:SurvivorRatio=2 -XX:+UseParallelGC "
fi
JAVA_JMX_OPTS=" -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false "
echo -e "Starting the service..."
nohup java $JAVA_MEM_OPTS $JAVA_JMX_OPTS -classpath $LIB_JARS com.zxsoft.server.TelnetServer 2>&1 &