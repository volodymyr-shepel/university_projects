output "vpc_with_https_id" {
  value = aws_vpc.a5_vpc.id
}

output "subnet" {
    value = aws_subnet.a5_subnet.id
}

output "sec_group" {
  value = aws_security_group.allow_ssh_http.id
}