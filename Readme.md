### Опыты с интеграционными тестами

#### С базами данных

Два способа тестирования:
- используя https://www.testcontainers.org/[testcontainers]. Решение основано на запуске базы в docker. Отсюда ограничение - на тестовой машине д.б. развернут docker. Тесты в xref:src/test/java/ru/perm/v/integrtest/repository/
 
- используя встроенную(embeded) БД. Тесты в link:file:src/test/java/ru/perm/v/integrtest/repository/embeded/PersonEmbededRepositoryTest.java