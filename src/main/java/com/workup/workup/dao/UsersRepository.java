package com.workup.workup.dao;

import com.workup.workup.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;

@Repository
// USER REPOSITORY
// USER MODEL NOT CONNECTED YET! DELETE WHEN CONNECTED!!!! //
public interface UsersRepository extends JpaRepository<User, Long> {


    User findByEmail(String email);

    User findByRole(Role role);
}
