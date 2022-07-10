#!/bin/sh
usage="Usage: nightcrow-daemon.sh (start|stop|status) <api-server> "

# if no args specified, show usage
if [ $# -le 1 ]; then
  echo $usage
  exit 1
fi

startStop=$1
shift
command=$1
shift

echo "Begin $startStop $command......"

BIN_DIR=`dirname $0`
BIN_DIR=`cd "$BIN_DIR"; pwd`
NIGHTCROW_HOME=`cd "$BIN_DIR/.."; pwd`

source /etc/profile

export HOSTNAME=`hostname`

export NIGHTCROW_PID_DIR=$NIGHTCROW_HOME/pid
export NIGHTCROW_LOG_DIR=$NIGHTCROW_HOME/logs
export NIGHTCROW_CONF_DIR=$NIGHTCROW_HOME/conf
export NIGHTCROW_SQL_DIR=$NIGHTCROW_HOME/sql
export NIGHTCROW_LIB_JARS=$NIGHTCROW_HOME/lib/*

export STOP_TIMEOUT=5

if [ ! -d "$NIGHTCROW_LOG_DIR" ]; then
  mkdir $NIGHTCROW_LOG_DIR
fi

log=$NIGHTCROW_LOG_DIR/nightcrow-$command-$HOSTNAME.out
pid=$NIGHTCROW_PID_DIR/nightcrow-$command.pid

cd $NIGHTCROW_HOME

export NIGHTCROW_OPTS="-server -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xss512k -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:LargePageSizeInBytes=128m -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -XX:+PrintGCDetails -Xloggc:$NIGHTCROW_LOG_DIR/gc.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=dump.hprof -XshowSettings:vm $NIGHTCROW_OPTS"

export DATABASE_TYPE=${DATABASE_TYPE:-"h2"}
export SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE:-"prod"}

if [ "$command" = "api-server" ]; then
  LOG_FILE="-Dlogging.config=classpath:logback-spring.xml"
  CLASS=indi.deeservent.nightcrow.api.ServerApplication
  HEAP_OPTS="-Xms1g -Xmx1g -Xmn512m"
  export NIGHTCROW_OPTS="$HEAP_OPTS $NIGHTCROW_OPTS $API_SERVER_OPTS"
  export SPRING_PROFILES_ACTIVE="${SPRING_PROFILES_ACTIVE},api,${DATABASE_TYPE}"
else
  echo "Error: No command named '$command' was found."
  exit 1
fi

case $startStop in
  (start)
    if [ "$DOCKER" = "true" ]; then
      echo start $command in docker
      export NIGHTCROW_OPTS="$NIGHTCROW_OPTS -XX:-UseContainerSupport"
      exec_command="$LOG_FILE $NIGHTCROW_OPTS -classpath $NIGHTCROW_SQL_DIR:$NIGHTCROW_CONF_DIR:$NIGHTCROW_LIB_JARS $CLASS"
      $JAVA_HOME/bin/java $exec_command
    else
      [ -w "$NIGHTCROW_PID_DIR" ] || mkdir -p "$NIGHTCROW_PID_DIR"

      if [ -f $pid ]; then
        if kill -0 `cat $pid` > /dev/null 2>&1; then
          echo $command running as process `cat $pid`.  Stop it first.
          exit 1
        fi
      fi

      echo starting $command, logging to $log
      exec_command="$LOG_FILE $NIGHTCROW_OPTS -classpath $NIGHTCROW_SQL_DIR:$NIGHTCROW_CONF_DIR:$NIGHTCROW_LIB_JARS $CLASS"
      echo "nohup $JAVA_HOME/bin/java $exec_command > $log 2>&1 &"
      nohup $JAVA_HOME/bin/java $exec_command > $log 2>&1 &
      echo $! > $pid
    fi
    ;;

  (stop)

      if [ -f $pid ]; then
        TARGET_PID=`cat $pid`
        if kill -0 $TARGET_PID > /dev/null 2>&1; then
          echo stopping $command
          kill $TARGET_PID
          sleep $STOP_TIMEOUT
          if kill -0 $TARGET_PID > /dev/null 2>&1; then
            echo "$command did not stop gracefully after $STOP_TIMEOUT seconds: killing with kill -9"
            kill -9 $TARGET_PID
          fi
        else
          echo no $command to stop
        fi
        rm -f $pid
      else
        echo no $command to stop
      fi
      ;;

  (status)
    # more details about the status can be added later
    serverCount=`ps -ef | grep "$NIGHTCROW_HOME" | grep "$CLASS" | grep -v "grep" | wc -l`
    state="STOP"
    #  font color - red
    state="[ \033[1;31m $state \033[0m ]"
    if [[ $serverCount -gt 0 ]];then
      state="RUNNING"
      # font color - green
      state="[ \033[1;32m $state \033[0m ]"
    fi
    echo -e "$command  $state"
    ;;

  (*)
    echo $usage
    exit 1
    ;;

esac

echo "End $startStop $command."
