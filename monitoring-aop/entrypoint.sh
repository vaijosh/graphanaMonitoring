#!/usr/bin/env bash

CMD=$1

echo "Command :" $CMD

JAVA_OPTS="$JAVA_OPTS"

case "$CMD" in
    "start")
        echo "Starting SpringBoot application"
        exec java $JAVA_OPTS -jar /root/app.jar
    ;;

    * )
        # custom command
        exec $CMD ${@:2}
    ;;
esac
