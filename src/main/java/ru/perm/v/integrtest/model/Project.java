package ru.perm.v.integrtest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project")
public class Project {
    @Id
    @SequenceGenerator(name = "projectSequence", sequenceName = "PROJECT_SEQUENCE", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "projectSequence")
    Long id;
    @Size(min = 3, max = 200, message
            = "Name must be between 3 and 200 characters")
    String name;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    Person owner;

    public Project(String name, Person owner) {
        this.name = name;
        this.owner = owner;
    }

    // По умолчанию не д.б. null.
    // По умолчанию д.б. Person с id=0
    @PrePersist
    void preInsert() {
        if (this.owner == null) {
            this.owner = new Person();
            this.owner.setId(0L);
        }
    }

}
