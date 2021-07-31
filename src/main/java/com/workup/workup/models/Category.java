package com.workup.workup.models;

// import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
// import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false)
  //    @KeywordField
  private String name;

  @ManyToMany(mappedBy = "categories")
  private List<Project> projects;

  @ManyToMany(mappedBy = "categories")
  private List<Profile> profiles;

  public Category() {}

  public Category(long id, String name) {
    this.name = name;
    this.id = id;
  }

  public Category(String name, List<Project> projects) {
    this.name = name;
    this.projects = projects;
  }

  public Category(long id, String name, List<Project> projects) {
    this.id = id;
    this.name = name;
    this.projects = projects;
  }

  public Category(List<Profile> profiles, String name) {
    this.name = name;
    this.profiles = profiles;
  }

  public Category(long id, List<Profile> profiles, String name) {
    this.id = id;
    this.name = name;
    this.profiles = profiles;
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

  public void setProjects(List<Project> projects) {
    this.projects = projects;
  }

  public List<Profile> getProfiles() {
    return profiles;
  }

  public void setProfiles(List<Profile> profiles) {
    this.profiles = profiles;
  }
}
