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

## **2) Конвертер валют**
Для сборки необходимы версии: 
- Java 17 
- Maven 3.8.1

Чтобы выполнить компиляцию и запуск программы, нужно в директории с файлом `pom.xml` выполнить следующие команды:

```shell
mvn dependency:resolve
mvn clean compile
mvn exec:java -Dexec.mainClass=com.example.exchangerapp.ExchangerApplication
```
## **3) Spring-приложение Библиотека (JDBCTemplate)**

- ТЗ представлено в папке `SpringMVC`
- Необходимо изменить файл `src/main/webapp/resources/database.properties.origin`: удалить `.origin` из названия файла и настроить параметры базы данных (используется `postgresql`)
-Данные для настройки базы:  таблица Person(id - int, primary key, autoincrement; name - varchar , birthyear - int > 0) , таблица Book(id - int, primary key, autoincrement; name - varchar; year - int  > 0; author - varchar; readerId - int references Person(id) OnDelete = setnull))

## **4) Spring-приложение Библиотека (Hibernate + JPA)**

- ТЗ представлено в папке `SpringJPA`
- Необходимо изменить файл `src/main/webapp/resources/hibernate.properties.origin`: удалить `.origin` из названия файла и настроить параметры базы данных (используется `postgresql`)
-Данные для настройки базы:  таблица Person(id - int, primary key, autoincrement; name - varchar , birthyear - int > 0) , таблица Book(id - int, primary key, autoincrement; name - varchar; year - int  > 0; author - varchar; taken - timestamp, readerId - int references Person(id) OnDelete = setnull))
