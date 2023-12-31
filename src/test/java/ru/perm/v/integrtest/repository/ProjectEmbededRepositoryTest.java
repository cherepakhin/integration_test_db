package ru.perm.v.integrtest.repository;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.perm.v.integrtest.model.IPersonProject;
import ru.perm.v.integrtest.model.Person;
import ru.perm.v.integrtest.model.PersonProject;
import ru.perm.v.integrtest.model.Project;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureEmbeddedDatabase
@Slf4j
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

    @Test
    @Transactional
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/test_project.sql")
    void getProject() {
        Project selected1000 = repository.getOne(1001L);

        assertEquals("PROJECT_1001", selected1000.getName());
        assertEquals("PERSON_1000", selected1000.getOwner().getName());
    }

    @Test
    @Transactional
    public void getIProjectPerson() {
        Person person = new Person("PERSON_1", 1);

        Project project = new Project("PROJECT_1", null);

        Project saved = repository.saveAndFlush(project);
        person.getProjects().add(project);
        Person savedPerson = personRepository.saveAndFlush(person);

        assertEquals("PROJECT_1", saved.getName());
        assertEquals(1, savedPerson.getProjects().size());

        List<IPersonProject> links = repository.getIProjectPerson();
        log.info("===================={}", links.get(0).getInfo());
        assertEquals(1, links.size());
    }

    @Test
    @Transactional
    public void getProjectPerson() {
        Person person = new Person("PERSON_1", 1);

        Project project = new Project("PROJECT_1", null);

        Project saved = repository.saveAndFlush(project);
        person.getProjects().add(project);
        Person savedPerson = personRepository.saveAndFlush(person);

        assertEquals("PROJECT_1", saved.getName());
        assertEquals(1, savedPerson.getProjects().size());

        List<PersonProject> links = personRepository.getProjectPerson();
        log.info("===================={}", links.get(0).toString());
        assertEquals(1, links.size());
    }
}