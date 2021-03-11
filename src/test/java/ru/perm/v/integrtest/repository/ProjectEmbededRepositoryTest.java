package ru.perm.v.integrtest.repository;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.perm.v.integrtest.model.Person;
import ru.perm.v.integrtest.model.Project;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureEmbeddedDatabase
class ProjectEmbededRepositoryTest {

    @Autowired
    ProjectRepository repository;
    @Autowired
    PersonRepository personRepository;

    @Test
    @Transactional
    void create() {
        Project p = new Project("PROJECT_1", null);

        Project saved = repository.save(p);
        System.out.println(saved.getOwner());
        assertEquals("PROJECT_1", saved.getName());
        assertEquals(Long.valueOf(0), saved.getOwner().getId());
    }

    @Test
    @Transactional
    void deleteById() {
        Project p1 = new Project("NAME1", null);
        Project p2 = new Project("NAME2", null);

        Project saved1 = repository.save(p1);
        Project saved2 = repository.save(p2);

        repository.deleteById(saved1.getId());

        assertFalse(repository.existsById(saved1.getId()));
        assertTrue(repository.existsById(saved2.getId()));
    }

    @Test
    @Transactional
    void getById() {
        Person person = new Person("PERSON_1", 1);
        personRepository.save(person);

        Project project = new Project("PROJECT_1", person);
        Project saved = repository.saveAndFlush(project);

        Project selected = repository.getOne(saved.getId());

        assertEquals("PROJECT_1", selected.getName());
        assertEquals("PERSON_1", selected.getOwner().getName());
    }
}