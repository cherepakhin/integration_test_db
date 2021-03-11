package ru.perm.v.integrtest.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.perm.v.integrtest.model.PersonProject;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class PersonRepositoryImpl implements PersonRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<PersonProject> getProjectPerson() {
        Query query = entityManager.createNativeQuery(
                "select person_id as personId, project_id as projectId from person_project",
                "PersonProjectMapping");
        return query.getResultList();
    }
}
