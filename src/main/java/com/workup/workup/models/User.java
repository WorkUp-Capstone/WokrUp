package com.workup.workup.models;


//import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import com.workup.workup.services.UniqueEmail;
import org.checkerframework.common.aliasing.qual.Unique;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @UniqueEmail(message = "")
    @Email(message = "Email is invalid")
    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 250)
    @Min(value = 8, message = "Password must be 8 characters long")
    private String password;

    private String passwordRepeat;

    @Column(nullable = false, length = 250)
//    @KeywordField
    private String first_name;

    @Column(nullable = false, length = 250)
//    @KeywordField
    private String last_name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Project> projectList;

    private boolean passwordsEqual;

    // Empty constructor for Spring
    public User() {}

    //Insert Constructor
    public User(long id,String email, String password, String first_name, String last_name, Role role)
    {
        this.id = id;
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.role = role;
    }

    public User(String email, String password, String first_name, String last_name, Role role)
    {
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.role = role;
    }

    //copy constructor for login credentials
    public User(User copy) {
        id = copy.id; // This line is SUPER important! Many things won't work if it's absent
        email = copy.email;
        password = copy.password;
        role = copy.role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeat() { return passwordRepeat; }

    public void setPasswordRepeat(String passwordRepeat) { this.passwordRepeat = passwordRepeat; }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Project> getProject() {
        return projectList;
    }

    public void setProject(List<Project> project) {
        this.projectList = project;
    }

    public void setPasswordsEqual(boolean passwordsEqual) { this.passwordsEqual = passwordsEqual; }

    @AssertTrue(message = "Passwords should match")
    public boolean isPasswordsEqual() { return passwordsEqual && password.equals(passwordRepeat); }

    public String getFullName(){
        return this.first_name + " " + this.last_name;
    }
}
