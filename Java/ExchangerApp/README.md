## **Конвертер валют**

Для сборки необходимы версии: 
- Java 17 
- Maven 3.8.1

Чтобы выполнить компиляцию и запуск программы, последовательно выполните следующие команды в текущей директории:

```shell
mvn dependency:resolve
mvn clean compile
mvn exec:java -Dexec.mainClass=com.example.exchangerapp.ExchangerApplication
```
