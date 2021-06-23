package com.workup.workup.models;


import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (nullable = false)
    private String role;

    // Constructors
    public Roles() {}

    public Roles(long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Roles(String role) { this.role = role; }

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
}
