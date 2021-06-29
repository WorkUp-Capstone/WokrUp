package com.workup.workup.dao;

import com.workup.workup.models.Profile;
import com.workup.workup.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findById(long id);
}