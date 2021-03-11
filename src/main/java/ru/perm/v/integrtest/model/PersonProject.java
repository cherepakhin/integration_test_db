package ru.perm.v.integrtest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonProject {
    Long personId;
    Long projectId;
}
