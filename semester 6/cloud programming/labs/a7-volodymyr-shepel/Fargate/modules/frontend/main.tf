resource "aws_security_group" "frontend_sg" {
  name        = "SecurityGroupFrontendTask7"
  description = "Allow traffic to frontend service"
  vpc_id      = "vpc-021abdbb7cdf4589a"

}

resource "aws_vpc_security_group_ingress_rule" "allow_http" {
  security_group_id = aws_security_group.frontend_sg.id
  cidr_ipv4         = "0.0.0.0/0"
  ip_protocol       = "tcp"
  from_port         = 8080
  to_port           = 8081
}

resource "aws_vpc_security_group_egress_rule" "allow_all_traffic_ipv4" {
  security_group_id = aws_security_group.frontend_sg.id
  cidr_ipv4         = "0.0.0.0/0"
  ip_protocol       = "-1"
}



data "aws_iam_role" "existing_role" {
  name = var.existing_role_name
}


resource "aws_ecs_task_definition" "frontend_task_def" {
  family                   = "FrontendTask7"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = "512"
  memory                   = "1024"
  execution_role_arn       = data.aws_iam_role.existing_role.arn
  task_role_arn            = data.aws_iam_role.existing_role.arn

  container_definitions = jsonencode([
    {
      name      = "frontend"
      image     = var.front_img_uri
      cpu       = 512
      memory    = 1024
      essential = true
      portMappings = [
        {
          containerPort = var.frontend_port
          hostPort      = var.frontend_port
          protocol      = "tcp"
        }
      ]
      environment = [
        {
          name  = "IP_ADDRESS"
          value = "${var.backend_host}:${var.backend_port}"
        }
      ]
    }
  ])
}

resource "aws_ecs_service" "frontend_service" {
  name            = "FrontendTask7Service"
  cluster         = var.cl_id
  task_definition = aws_ecs_task_definition.frontend_task_def.arn
  desired_count   = 1
  launch_type     = "FARGATE"

  enable_ecs_managed_tags = true
  wait_for_steady_state   = true

  network_configuration {
    assign_public_ip = true
    subnets         = [var.subnet_id]
    security_groups = [aws_security_group.frontend_sg.id]
  }
}
