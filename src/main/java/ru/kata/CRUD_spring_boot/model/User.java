package ru.kata.CRUD_spring_boot.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Version
    private int version;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "secondName")
    private String secondName;
    private int age;

    @OneToMany //(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Role> roles = new LinkedHashSet<>();

    public User() {
    }

    public User(String firstName, String secondName, int age) {
        this.firstName = firstName;
        this.age = age;
        this.secondName = secondName;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getAge() {
        return age;
    }

    public String getSecondName() {
        return secondName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return String.format("User{id=%d, firstName='%s', secondName='%s', age=%d, roles=%s}",
                id, firstName, secondName, age, Arrays.toString(roles.toArray()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName, age, roles);
    }

    @Override
    public boolean equals(Object o) {
        if ((o == null) || (o.getClass() != User.class)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        return (firstName.equals(((User) o).getFirstName())
                && secondName.equals(((User) o).getSecondName())
                && age == ((User) o).getAge()
                // compare two set
                && (!roles.containsAll(((User) o).getRoles()))
                && (!((User) o).getRoles().containsAll(roles))
        );
    }
}

