package com.workup.workup.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="categories")
public class ProjectCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<Project> projects;

    public ProjectCategory() {
    }

    public ProjectCategory(String name, List<Project> projects) {
        this.name = name;
        this.projects = projects;
    }

    public ProjectCategory(long id, String name, List<Project> projects) {
        this.id = id;
        this.name = name;
        this.projects = projects;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> ads) {
        this.projects = ads;
    }
}
