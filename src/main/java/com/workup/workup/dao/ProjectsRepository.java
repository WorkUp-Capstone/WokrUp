package com.workup.workup.dao;


import com.workup.workup.models.Project;
import com.workup.workup.models.ProjectImage;
import com.workup.workup.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
// PROJECT REPOSITORY
// PROJECT MODEL NOT CREATED OR CONNECTED YET! DELETE WHEN CONNECTED!!!! //
public interface ProjectsRepository extends JpaRepository<Project, Long> {
    Project getProjectByUserIs(User user);
@Query(value = "SELECT Distinct projects.id FROM projects" +
        "    JOIN project_categories ON projects.id = project_categories.project_id" +
        "    JOIN categories ON project_categories.category_id = categories.id" +
        "    JOIN users ON projects.owner_user_id = users.id" +
        "    JOIN profiles ON users.id = profiles.user_id" +
        "    WHERE " +
        "  (Match(title,description, status) AGAINST (?1)) OR" +
        "  (Match(name) AGAINST (?1)) OR" +
        "  (Match(first_name, last_name) AGAINST (?1)) OR" +
        "  (Match(city, state) AGAINST (?1))", nativeQuery = true)
    List<Long> projectSearch(String keyword);



    // need to add "find ALL by" projects in a list
    List<Project> getAllProjectsByUserIdIs(Long ownerUserId);

}
