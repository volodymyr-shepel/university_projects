# Define an AWS security group for the backend service
resource "aws_security_group" "backend_sg" {
  name        = "SecurityGroupBackendTask7"
  description = "Allow traffic to backend service"
  vpc_id      = "vpc-021abdbb7cdf4589a"
}


# Define an egress rule for the backend security group
resource "aws_vpc_security_group_ingress_rule" "allow_http" {
  security_group_id = aws_security_group.backend_sg.id
  cidr_ipv4         = "0.0.0.0/0"
  ip_protocol       = "tcp"
  from_port         = 8080
  to_port           = 8080
}

resource "aws_vpc_security_group_egress_rule" "allow_all_traffic_ipv4" {
  security_group_id = aws_security_group.backend_sg.id
  cidr_ipv4         = "0.0.0.0/0"
  ip_protocol       = "-1"
}


data "aws_iam_role" "existing_role" {
  name = var.existing_role_name
}

resource "aws_ecs_task_definition" "backend_task_def" {
  family                   = "BackendTask7"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = "512"
  memory                   = "1024"
  execution_role_arn       = data.aws_iam_role.existing_role.arn
  task_role_arn            = data.aws_iam_role.existing_role.arn

  container_definitions = jsonencode([
    {
      name      = "backend"
      image     = var.back_img_uri
      cpu       = 512
      memory    = 1024
      essential = true
      portMappings = [
        {
          containerPort = var.backend_port
          hostPort      = var.backend_port
          protocol      = "tcp"
        }
      ]
    }
  ])
}

resource "aws_ecs_service" "backend_service" {
  name            = "BackendTask7Service"
  cluster         = var.cl_id
  task_definition = aws_ecs_task_definition.backend_task_def.arn
  desired_count   = 1
  launch_type     = "FARGATE"

  enable_ecs_managed_tags = true
  wait_for_steady_state   = true

  network_configuration {
    assign_public_ip = true
    subnets         = [var.subnet_id]
    security_groups = [aws_security_group.backend_sg.id]
  }
}

data "aws_network_interface" "interface_tags" {
  filter {
    name   = "tag:aws:ecs:serviceName"
    values = [aws_ecs_service.backend_service.name]
  }
}