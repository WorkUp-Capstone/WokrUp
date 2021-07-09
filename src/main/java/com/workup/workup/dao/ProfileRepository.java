package com.workup.workup.dao;

import com.workup.workup.models.Profile;
import com.workup.workup.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findById(long id);

    Profile getProfileByUserIs(User user);
  
    List<Profile> getAllByUserRole_Id(Long id);

    @Query(value = "SELECT Distinct profiles.id FROM profiles" +
            "    JOIN profile_categories ON profiles.id = profile_categories.profile_id" +
            "    JOIN categories ON profile_categories.category_id = categories.id" +
            "    JOIN users ON profiles.user_id = users.id" +
            "    WHERE " +
            "  (Match(name) AGAINST (?1)) OR" +
            "  (Match(city, state) AGAINST (?1)) OR" +
            "  (Match(first_name, last_name) AGAINST (?1))"
            , nativeQuery = true)
    List<Long> devSearch(String searchString);
}
