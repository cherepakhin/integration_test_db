package ru.perm.v.integrtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.perm.v.integrtest.model.Project;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
