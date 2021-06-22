package com.workup.workup.models;


import javax.persistence.*;

@Entity
@Table(name="users")
public class User {

    public User(){
    }


    //Insert Constructor
    public User(long id, String role_id, String email, String password, String first_name, String last_name)
    {
        this.id = id;
        this.role_id = role_id;
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
    }



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String role_id;

    @Column(nullable = false, length = 50)
    private String userName;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 250)
    private String password;

    @Column(nullable = false, length = 250)
    private String first_name;

    @Column(nullable = false, length = 250)
    private String last_name;


}
