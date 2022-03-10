#! /bin/bash
imageId=`docker images | grep -w backend_511 | awk '{print $3}'`
if [ "$imageId" != "" ];then
	docker rmi -f backend_511
fi
containerId=`docker ps -a | grep -w backend_511 | awk '{print $1}'`
if [ "$containerId" != "" ];then
	docker stop backend_511
	docker rm backend_511
fi
