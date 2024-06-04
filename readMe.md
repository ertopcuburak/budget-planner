To build docker image

docker build -t budget-planner .

To run docker image
docker run --name budget-planner --link ty-db-container 