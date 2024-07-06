terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = ">= 5.1"
    }
  }
  required_version = ">= 1.2.0"
}

provider "aws" {
  region = "us-east-1"
}

resource "aws_vpc" "my_vpc" {
  cidr_block = "10.0.0.0/16"
  tags = {
    Name        = "my-vpc-VS"
    Terraform   = "true"
    Environment = "dev"
  }
}

resource "aws_subnet" "public_subnet" {
  vpc_id                  = aws_vpc.my_vpc.id
  cidr_block              = "10.0.101.0/24"
  availability_zone       = "us-east-1b"
  map_public_ip_on_launch = true
}

resource "aws_internet_gateway" "my_igw" {
  vpc_id = aws_vpc.my_vpc.id

  tags = {
    Name = "my-igw-VS"
  }
}

resource "aws_route_table" "public_route_table" {
  vpc_id = aws_vpc.my_vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.my_igw.id
  }

  tags = {
    Name = "public-route-table"
  }
}

resource "aws_route_table_association" "public_subnet_association" {
  subnet_id      = aws_subnet.public_subnet.id
  route_table_id = aws_route_table.public_route_table.id
}


resource "aws_security_group" "allow_ssh_http" {
  name        = "allow_ssh_http"
  description = "Allow SSH and HTTP inbound traffic and all outbound traffic"
  vpc_id      = aws_vpc.my_vpc.id
  tags = {
    Name = "allow-ssh-http"
  }
}

resource "aws_vpc_security_group_egress_rule" "allow_all_traffic_ipv4" {
  security_group_id = aws_security_group.allow_ssh_http.id
  cidr_ipv4         = "0.0.0.0/0"
  ip_protocol       = "-1" # all ports
}

resource "aws_vpc_security_group_ingress_rule" "allow_http" {
  security_group_id = aws_security_group.allow_ssh_http.id
  cidr_ipv4         = "0.0.0.0/0"
  ip_protocol       = "tcp"
  from_port         = 8080
  to_port           = 8081
}

resource "aws_vpc_security_group_ingress_rule" "allow_ssh" {
  security_group_id = aws_security_group.allow_ssh_http.id
  cidr_ipv4         = "0.0.0.0/0"
  ip_protocol       = "tcp"
  from_port         = 22
  to_port           = 22
}

resource "aws_instance" "tf-web-server" {
  ami                         = "ami-080e1f13689e07408"
  instance_type               = "t2.micro"
  key_name                    = "vockey"
  subnet_id                   = aws_subnet.public_subnet.id
  associate_public_ip_address = true
  vpc_security_group_ids      = [aws_security_group.allow_ssh_http.id]

  provisioner "file" {
    source      = "setup.sh"
    destination = "/home/ubuntu/setup.sh"
  }

  provisioner "remote-exec" {
    inline = [
      "sudo apt update -y",
      "sudo apt install git -y",
      "wget https://download.oracle.com/java/21/latest/jdk-21_linux-x64_bin.tar.gz",
      "sudo mkdir -p /usr/lib/jvm/",
      "sudo tar -xzf jdk-21_linux-x64_bin.tar.gz -C /usr/lib/jvm/",
      "echo 'alias JAVA_HOME=/usr/lib/jvm/jdk-21.0.3' >> ~/.bashrc ",
      "echo 'alias PATH=$PATH:$JAVA_HOME/bin' >> ~/.bashrc",
      "echo 'export JAVA_HOME=/usr/lib/jvm/jdk-21.0.3' | sudo tee -a /etc/environment > /dev/null",
      "source /etc/environment",
      "source ~/.bashrc",
      "sed -i 's/\r$//' /home/ubuntu/setup.sh",
      "sudo chmod +x /home/ubuntu/setup.sh",
      "sudo /home/ubuntu/setup.sh",
    ]
  }

  connection {
    type        = "ssh"
    user        = "ubuntu"
    private_key = file("labsuser.pem")
    host        = self.public_ip
  }


  tags = {
    Name = "My-Terraform-Web-Server"
  }
}
