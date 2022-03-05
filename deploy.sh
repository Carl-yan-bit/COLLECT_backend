#! /bin/bash
containerId=`docker ps -a | grep -w backend | awk '{print $1}'`
if [ "$containerId" != "" ];then
	docker stop backend
	docker rm backend
fi
