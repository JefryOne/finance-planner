# Этап 1: Сборка приложения с помощью Maven
FROM maven:3.8.5-openjdk-11 AS builder

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл pom.xml и загружаем зависимости
COPY pom.xml ./
RUN mvn dependency:go-offline

# Копируем исходный код
COPY src ./src

# Собираем приложение и генерируем файл WAR
RUN mvn clean package

# Этап 2: Запуск приложения с помощью Tomcat
FROM tomcat:9.0-jdk11-openjdk

# Удаляем стандартные приложения Tomcat (опционально)
RUN rm -rf /usr/local/tomcat/webapps/*

# Копируем WAR-файл из предыдущего этапа в Tomcat
COPY --from=builder /app/target/finance-planner.war /usr/local/tomcat/webapps/finance-planner.war

# Указываем директорию для хранения данных
VOLUME ["/usr/local/tomcat/data"]

# Открываем порт 8080
EXPOSE 8080

# Запускаем Tomcat
CMD ["catalina.sh", "run"]