CODE_HOME=/home/tomcat
PROJECTNAME=formation8
JARNAME=formation8-0.0.1-SNAPSHOT
cd $CODE_HOME/$PROJECTNAME
pid=`ps -ef |grep $PROJECTNAME |grep -v "grep" |awk '{print $2}' `
if [ $pid ]; then
    echo "App  is  running  and pid=$pid"
    kill -9 $pid
fi
nohup java -jar $CODE_HOME/$PROJECTNAME/$JARNAME.jar > cmd.out 2>&1 &
