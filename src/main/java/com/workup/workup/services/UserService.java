package com.workup.workup.services;

import com.workup.workup.dao.UsersRepository;
import com.workup.workup.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersDao;

    public List<User> getUsersByKeyword(String keyword) {
        return usersDao.getUsersByKeyword(keyword);
    }

}
