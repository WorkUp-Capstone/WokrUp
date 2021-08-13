package com.workup.workup.dao;

import com.workup.workup.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
// USER REPOSITORY
// USER MODEL NOT CONNECTED YET! DELETE WHEN CONNECTED!!!! //
public interface UsersRepository extends JpaRepository<User, Long> {

  User findByEmail(String email);

  boolean existsByEmail(String email);

  @Query(
      value =
          "from User u where u.first_name like %:keyword% or u.last_name like %:keyword% or u.email like %:keyword%")
  List<User> getUsersByKeyword(@Param("keyword") String keyword);
}
