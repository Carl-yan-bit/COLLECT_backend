#! /bin/bash
containerId=`docker ps -a | grep -w backend_511 | awk '{print $1}'`
if [ "$containerId" != "" ];then
	docker stop backend
	docker rm backend
fi
