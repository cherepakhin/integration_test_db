package ru.perm.v.integrtest.model;

import static java.lang.String.format;

public interface IPersonProject {
    Long getPersonId();

    Long getProjectId();

    default String getInfo() {
        return format("IPersonProject personId=%s, projectId=%s", getPersonId(), getProjectId());
    }
}
