package ru.perm.v.integrtest.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.perm.v.integrtest.model.Person;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;


class PersonRepositoryTest extends IntegrationRepositoryTest {

    @Autowired
    PersonRepository repository;

    @Test
    @Transactional
    void create() {
        Person p = new Person("NAME", 18);

        Person saved = repository.save(p);

        assertEquals(p.getName(), saved.getName());
        assertEquals(p.getAge(), saved.getAge());
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