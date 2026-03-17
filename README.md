# Final_project

Maven-проект с UI/API-автотестами для https://qa-desk.stand.praktikum-services.ru.

## Стек
- Java 11
- Maven
- Selenide
- RestAssured
- Cucumber + Gherkin
- JUnit 5
- Allure
- PicoContainer

## Что покрыто
- Успешная регистрация с уникальным email
- Попытка повторной регистрации
- Авторизация существующего пользователя
- Создание объявления
- Редактирование своего объявления
- Удаление своего объявления

## Запуск
```bash
mvn test -Dheadless=true
```

## Полезные параметры
- `-Dbase.url=https://qa-desk.stand.praktikum-services.ru`
- `-Dapi.url=https://qa-desk.stand.praktikum-services.ru/api`
- `-Dbrowser=chrome`
- `-Dheadless=true`

## Allure
```bash
mvn allure:serve
```

## Примечание
В этой среде проект успешно проходит стадию компиляции (`mvn test -DskipTests=true`). Полноценный запуск UI-тестов упирался в локальный конфликт JUnit Platform provider внутри текущего окружения Codex, поэтому перед сдачей рекомендуется выполнить `mvn test -Dheadless=true` на целевой машине с установленным Chrome.
