
resource "aws_vpc" "a5_vpc" {
  cidr_block = "10.0.0.0/16"
}
resource "aws_subnet" "a5_subnet" {
  vpc_id     = aws_vpc.a5_vpc.id
  cidr_block = "10.0.101.0/24"
}
resource "aws_internet_gateway" "a5_gw" {
  vpc_id = aws_vpc.a5_vpc.id
}
resource "aws_route_table" "a5_route_table" {
  vpc_id = aws_vpc.a5_vpc.id
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.a5_gw.id
  }
}
resource "aws_route_table_association" "a5_table_association" {
  subnet_id      = aws_subnet.a5_subnet.id
  route_table_id = aws_route_table.a5_route_table.id
}

resource "aws_security_group" "allow_ssh_http" {
  name        = "allow_ssh_http"
  description = "Allow SSH and HTTP inbound traffic and all outbound traffic"
  vpc_id      = aws_vpc.a5_vpc.id
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
  from_port         = 80
  to_port           = 80
}
resource "aws_vpc_security_group_ingress_rule" "allow_https" {
  security_group_id = aws_security_group.allow_ssh_http.id
  cidr_ipv4         = "0.0.0.0/0"
  ip_protocol       = "tcp"
  from_port         = 443
  to_port           = 443
}
resource "aws_vpc_security_group_ingress_rule" "allow_ssh" {
  security_group_id = aws_security_group.allow_ssh_http.id
  cidr_ipv4         = "0.0.0.0/0"
  ip_protocol       = "tcp"
  from_port         = 22
  to_port           = 22
}