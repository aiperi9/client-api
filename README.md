Это проект Spring Boot для управления клиентами и их контактами с использованием PostgreSQL и Docker Compose.

## Стек технологий

- **Spring Boot** (версия 3.x)
- **PostgreSQL** (для базы данных)
- **Docker** и **Docker Compose**
- **Maven** (для сборки и управления зависимостями)
- **Swagger** (для API документации)

## Требования

- Docker и Docker Compose должны быть установлены на вашем компьютере.

## Установка и запуск
### Клонируйте репозиторий:

```bash
git clone https://github.com/aiperi9/client-api.git
cd client-api
```

### Запустите контейнеры с помощью Docker Compose:
```bash 
  docker-compose up --build 
  ```

### Проверьте, что все работает:

   После запуска контейнеров, приложение будет доступно по адресу 

```bash
http://localhost:8080 
```
а база данных PostgreSQL будет слушать на порту 5432.


### Использование Swagger:

Для взаимодействия с API через интерфейс Swagger, перейдите по следующему адресу в браузере:

```bash
   http://localhost:8080/swagger-ui/index.html
```