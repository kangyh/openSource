#!/bin/sh
#author: zhangqiang

#Current home
CURR_HOME=$(dirname $(readlink -f $0))

#jvm options
JAVA_OPTS="-Xms2g -Xmx4g -Djava.awt.headless=true -XX:MaxPermSize=256m -server -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=85  -Xnoclassgc -Xverify:none -XX:+CMSClassUnloadingEnabled -XX:+CMSPermGenSweepingEnabled"
JAR_FILE="$CURR_HOME/../risk_server-1.0-SNAPSHOT.jar"



log_path='/hy/logs/risk'
riqi=`date +"%Y%m%d%H%M%S"`
CMD="nohup java $JAVA_OPTS -jar $JAR_FILE > ${log_path}/console_$(hostname).log 2>&1 &"

PID_FILE="$CURR_HOME/server.pid"

###################################
#startup
###################################
start() {
   pid=""
   if [ -f "$PID_FILE" ]; then
      pid=`cat "$PID_FILE"`
   fi
   if [ "$pid" != "" ] && [ -d "/proc/$pid" ]; then
      echo "Process has been started with pid: $pid, ignore."
   else
      if [ -f "${log_path}/console_$(hostname).log" ]; then
         mv ${log_path}/console_$(hostname).log ${log_path}/console_$(hostname)_$riqi.log
         gzip ${log_path}/console_$(hostname)_$riqi.log
      fi
	  mkdir -p ${log_path}
      eval "$CMD"
      pid=$!
      echo "$pid" > "$PID_FILE"
      echo "Process started at $pid."
   fi
}

###################################
#stop
###################################
stop() {
   pid=""
   if [ -f "$PID_FILE" ]; then
      pid=`cat "$PID_FILE"`
   fi
   if [ "$pid" == "" ] || [ ! -d "/proc/$pid" ]; then
      echo "Process is not running, ignore."
   else
      echo "Trying to killing process $pid gracefully."
      kill -15 $pid > /dev/null 2>&1
      sleep 3
      if [ -d "/proc/$pid" ]; then
         echo "Process still alive, killing it in anger!"
	     kill -9 $pid > /dev/null 2>&1
      fi
      echo "Process stopped"
   fi
   if [ -f "$PID_FILE" ]; then
      rm -f $PID_FILE
   fi
}

###################################
#status
###################################
status() {
   pid=""
   if [ -f "$PID_FILE" ]; then
      pid=`cat "$PID_FILE"`
   fi
   if [ "$pid" != "" ] && [ -d "/proc/$pid" ];  then
      echo "Process is running! (pid=$pid)"
   else
      echo "Process is not running"
			exit 1
   fi
}
 
###################################

###################################
#Accept only 1 argument:{start|stop|restart|status}
###################################
case "$1" in
   'start')
      start
      ;;
   'stop')
     stop
     ;;
   'restart')
     stop
     start
     ;;
   'status')
     status
     ;;
  *)
     echo "Usage: $0 {start|stop|restart|status}"
     exit 1
esac
exit 0
