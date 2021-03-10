package ru.perm.v.integrtest.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonTest {
    @Test
    void name() {
        Person p = new Person("NAME", 1);
        assertEquals("NAME", p.getName());
    }
}