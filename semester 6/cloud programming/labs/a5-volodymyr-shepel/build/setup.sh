#!/bin/bash

sudo apt update -y
sudo apt install git -y
sudo apt install -y curl
curl -fsSL https://get.docker.com -o get-docker.sh
sudo sh get-docker.sh
sudo usermod -aG docker ubuntu
sudo systemctl enable docker
sudo systemctl start docker

git clone https://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@github.com/pwr-cloudprogramming/a5-volodymyr-shepel.git

cd a5-volodymyr-shepel/frontend
sudo chmod +x mvnw
cd src/main/resources/static/js
sudo chmod 777 tictactoe.js

sed -i "s/<BACKEND-IP>/$(curl -s http://169.254.169.254/latest/meta-data/public-ipv4)/g" tictactoe.js
cd ../../../../..
cd ../backend
sudo chmod +x mvnw
cd ../build

chmod 1777 run.sh
sed -i 's/\r$//' run.sh
sudo ./run.sh