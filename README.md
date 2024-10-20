# finance-planner...

Для запуска:

docker build -t finance-planner:latest .

docker run -d -p 8080:8080 --name finance-planner-app finance-planner:latest

http://localhost:8080/finance-planner/expenses