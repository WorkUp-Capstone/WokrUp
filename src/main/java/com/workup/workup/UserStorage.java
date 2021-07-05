package com.workup.workup;

import com.workup.workup.models.User;

import java.util.HashSet;
import java.util.Set;

public class UserStorage {

    private static UserStorage instance;
    private Set<User> users;

    private UserStorage() {
        users = new HashSet<>();
    }

    public static synchronized UserStorage getInstance() {
        if (instance == null) {
            instance = new UserStorage();
        }

        return instance;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void checkEmailIfExists(User email) throws Exception {
        if (users.contains(email)) {
            throw new Exception("Hey idiot, you're a clone: " + email);
        }
        users.add(email);
    }

}
