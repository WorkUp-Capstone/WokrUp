package com.workup.workup.dao;

import com.workup.workup.models.Profile;
import com.workup.workup.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findById(long id);

    Profile getProfileByUserIs(User user);

    List<Profile> getAllByUserRole_Id(Long id);


}
