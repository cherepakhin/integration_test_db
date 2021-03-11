package ru.perm.v.integrtest.repository.embeded;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.perm.v.integrtest.model.Person;
import ru.perm.v.integrtest.repository.PersonRepository;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureEmbeddedDatabase
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/test_person.sql")
class PersonEmbededRepositoryTest {

    @Autowired
    PersonRepository repository;

    @Test
    @Transactional
    void initbase() {
        assertTrue(repository.findById(0L).isPresent());
        assertTrue(repository.findById(200L).isPresent());
    }

    @Test
    @Transactional
    void create() {
        Person p = new Person("NAME", 18);

        Person saved = repository.save(p);

        assertEquals(p.getName(), saved.getName());
        assertEquals(p.getAge(), saved.getAge());

        Optional<Person> personalOptional = repository.findById(200L);
        assertTrue(personalOptional.isPresent());
    }

    @Test
    @Transactional
    void deleteById() {
        Person p1 = new Person("NAME1", 18);
        Person p2 = new Person("NAME2", 19);

        Person saved1 = repository.save(p1);
        Person saved2 = repository.save(p2);

        repository.deleteById(saved1.getId());

        assertFalse(repository.existsById(saved1.getId()));
        assertTrue(repository.existsById(saved2.getId()));
    }
}