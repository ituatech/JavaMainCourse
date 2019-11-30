#!/usr/bin/env bash

REMOTE_DEBUG="-agentlib:jdwp=transport=dt_socket,address=14025,server=y,suspend=n"
MEMORY="-Xms64m -Xmx64m -XX:MaxMetaspaceSize=32m"
GC_SERIAL="-XX:+UseSerialGC"
GC_PARALLEL="-XX:+UseParallelGC"
GC_CONCURRENT="-XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -XX:+ScavengeBeforeFullGC -XX:+CMSScavengeBeforeRemark -XX:+UseParNewGC"
GC_G1="-XX:+UseG1GC"
GC_LOG=" -verbose:gc -Xlog:gc*:file=./logs/gc_pid__%t_%p.log -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=100M"
JMX="-Dcom.sun.management.jmxremote.port=15025 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"
DUMP="-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./dumps/"

#-XX:OnOutOfMemoryError="kill -3 %p"  Unix thread dump

java $REMOTE_DEBUG $MEMORY $GC_SERIAL $GC_LOG $JMX $DUMP -jar target/L06.1-GC_log.jar > jvm.out
