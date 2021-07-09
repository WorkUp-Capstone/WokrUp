package com.workup.workup.dao;

import com.workup.workup.models.User;
import com.workup.workup.UserStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.util.Set;

@Repository
// USER REPOSITORY
// USER MODEL NOT CONNECTED YET! DELETE WHEN CONNECTED!!!! //
public interface UsersRepository extends JpaRepository<User, Long> {


    User findByEmail(String email);

    User findUserById(Long id);

    User findByRole(Role role);

    User findByRoleIsNot(Role role);

    UserStorage getUsersByEmail(String email);

}
