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

resource "aws_ecs_cluster" "cl" {
  name = "A7-FarGate"
}

module "backend" {
  source = "./modules/backend"
  cl_id = aws_ecs_cluster.cl.id
}

module "frontend" {
  source = "./modules/frontend"
  backend_host = module.backend.backend_host
  cl_id = aws_ecs_cluster.cl.id
}

output "backend" {
  value = module.backend
}
