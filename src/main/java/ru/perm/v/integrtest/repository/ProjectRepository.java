package ru.perm.v.integrtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.perm.v.integrtest.model.IPersonProject;
import ru.perm.v.integrtest.model.Project;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query(value =
            "select person_id as personId, project_id as projectId from person_project",
            nativeQuery = true
    )
    List<IPersonProject> getIProjectPerson();
}
