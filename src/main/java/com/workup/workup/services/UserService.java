//package com.workup.workup.services;
//
//import com.workup.workup.dao.UsersRepository;
//import com.workup.workup.models.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UsersRepository usersRepository;
//
//    //returns list of users
//    public List<User> getUsers(){
//        return usersRepository.findAll();
//    }
//
//    //get users by keyword
//    public List<User> findByKeyword(String keyword){
//        return usersRepository.findByKeyword(keyword);
//    }
//
//}
