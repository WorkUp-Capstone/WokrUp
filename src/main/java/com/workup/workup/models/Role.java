package com.workup.workup.models;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (nullable = false)
    private String role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
    private List<User> users;

    // Constructors
    public Role() {}

    public Role(String role) {
        this.role = role;
    }

    public Role(long id, String role, List<User> users) {
        this.id = id;
        this.role = role;
        this.users = users;
    }

    public Role(String role, List<User> users) { this.role = role; this.users = users; }

    // Getters/Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
