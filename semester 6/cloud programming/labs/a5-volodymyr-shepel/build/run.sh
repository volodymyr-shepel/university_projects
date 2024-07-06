#!/bin/bash

# Check if the network exists
if ! docker network inspect network_for_cloud &>/dev/null; then
    docker network create network_for_cloud
fi

cd ../backend
./mvnw clean install
docker build --no-cache -t backend_image .
docker run -d -p 8080:8080 --name backend_container --network network_for_cloud backend_image
cd ../frontend
./mvnw clean install
docker build --no-cache -t frontend_image .
docker run -d -p 8081:8081 --name frontend_container --network network_for_cloud frontend_image
