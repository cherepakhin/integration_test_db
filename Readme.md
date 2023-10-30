### Опыты с интеграционными тестами

Перед запуском тестов установить Java 11

```shell
export JAVA_HOME=/usr/lib/jvm/java-1.11.0-openjdk-amd64
```

#### Проведение тестов

````shell
./mvnw test
....
[INFO] Results:
[INFO] 
[INFO] Tests run: 16, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
...
````

#### С базами данных

Два способа тестирования:
- используя https://www.testcontainers.org/[testcontainers]. Решение основано на запуске базы в docker. Отсюда ограничение - на тестовой машине д.б. развернут docker. Тесты в src/test/java/ru/perm/v/integrtest/repository/
 
- используя встроенную(embeded) БД. Тесты в src/test/java/ru/perm/v/integrtest/repository/embeded/PersonEmbededRepositoryTest.java