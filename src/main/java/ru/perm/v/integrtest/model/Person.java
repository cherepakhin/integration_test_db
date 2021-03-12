package ru.perm.v.integrtest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SqlResultSetMapping(
        name = "PersonProjectMapping",
        classes = @ConstructorResult(
                targetClass = PersonProject.class,
                columns = {
                        @ColumnResult(name = "personId", type = Long.class),
                        @ColumnResult(name = "projectId", type = Long.class)
                }
        )
)
@Entity
@Table(name = "person")
public class Person {
    @Id
    @SequenceGenerator(name = "personSequence", sequenceName = "PERSON_SEQUENCE", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personSequence")
    Long id;
    @Size(min = 3, max = 200, message
            = "Name must be between 3 and 200 characters")
    @Column(columnDefinition = "varchar(255) default ''")
    String name = "";
    @Min(value = 1, message = "Id should not be less than 1")
    @Column(columnDefinition = "integer default 1")
    Integer age = 1;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "person_project",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    Set<Project> projects = new HashSet<>();

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
