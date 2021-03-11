package ru.perm.v.integrtest.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
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

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
