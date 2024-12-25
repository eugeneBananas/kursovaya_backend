# Используем официальный образ Java 17
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем jar-файл приложения (замените "app.jar" на имя вашего jar-файла)
COPY build/libs/*.jar app.jar

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]

# Указываем порт, на котором приложение будет работать
EXPOSE 8082