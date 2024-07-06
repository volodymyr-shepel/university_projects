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

module "backend" {
  source = "./modules/backend"
}

module "frontend" {
  source = "./modules/frontend"
  backend_url = module.backend.backend_ebs_url
}

output "backend_address" {
  value = module.backend
}
