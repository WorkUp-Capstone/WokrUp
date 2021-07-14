package com.workup.workup.services;

import com.workup.workup.dao.ProfileRepository;
import com.workup.workup.dao.UsersRepository;
import com.workup.workup.models.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profilesDao;
    @Autowired
    private UsersRepository usersDao;

    public List<Profile> getProfilesByKeyword(String keyword) {
        return profilesDao.getProfilesByKeyword(keyword);
    }

}
