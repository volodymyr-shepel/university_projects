# Define variables
ECR_REGISTRY="637423510497.dkr.ecr.us-east-1.amazonaws.com"
BACKEND_REPO="backend_tic_tac_toe"
FRONTEND_REPO="frontend_tic_tac_toe"
REGION="us-east-1"

# Check if backend repository exists, create if it does not
if ! aws ecr describe-repositories --repository-names $BACKEND_REPO --region $REGION > /dev/null 2>&1; then
  aws ecr create-repository --repository-name $BACKEND_REPO --region $REGION
fi

# Check if frontend repository exists, create if it does not
if ! aws ecr describe-repositories --repository-names $FRONTEND_REPO --region $REGION > /dev/null 2>&1; then
  aws ecr create-repository --repository-name $FRONTEND_REPO --region $REGION
fi

# Navigate to backend directory, build the Docker image, and push it to ECR
cd ../backend
mvn clean install
docker build --no-cache -t $ECR_REGISTRY/$BACKEND_REPO:latest .
aws ecr get-login-password --region $REGION | docker login --username AWS --password-stdin $ECR_REGISTRY/$BACKEND_REPO
docker push $ECR_REGISTRY/$BACKEND_REPO:latest

# Navigate to frontend directory, build the Docker image, and push it to ECR
cd ../frontend
mvn clean install
docker build --no-cache -t $ECR_REGISTRY/$FRONTEND_REPO:latest .
aws ecr get-login-password --region $REGION | docker login --username AWS --password-stdin $ECR_REGISTRY/$FRONTEND_REPO
docker push $ECR_REGISTRY/$FRONTEND_REPO:latest
