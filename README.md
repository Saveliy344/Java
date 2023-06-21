# Java

Список проектов Java:

## **1) Крестики-нолики**

Для сборки необходимы следующие версии:
- Java 17
- Maven 3.8.1

Чтобы выполнить компиляцию и запуск программы, выполните следующие команды в директории с файлом `pom.xml`:

```shell
mvn dependency:resolve
mvn clean compile
mvn exec:java -Dexec.mainClass=com.example.tictactoe.MainApplication
```

## **2) Конвертер валют.**
Для сборки необходимы версии: 
-Java 17 
-Maven 3.8.1.
Чтобы выполнить компиляцию и запуск программы, нужно в директории с файлом pom.xml выполнить следующие команды:
```shell
mvn dependency:resolve
mvn clean compile
mvn exec:java -Dexec.mainClass=com.example.exchangerapp.ExchangerApplication
```
## **3) Spring-приложение Библиотека. **
-ТЗ представлено в папке SpringMVC
-Для запуска приложения используется артефакт Web Application: Exploded
-Необходимо изменить файл src/main/webapp/resources/database.properties.origin: удалить .origin из названия файла и настроить параметры базы данных (используется postgresql).
