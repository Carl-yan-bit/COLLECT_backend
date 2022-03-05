containerId = `docker ps -a | grep -w backend `
if [ "$containerId" != ""];then
  docker stop backend
  docker rm backend
fi
