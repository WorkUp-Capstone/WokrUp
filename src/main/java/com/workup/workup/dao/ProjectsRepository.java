package com.workup.workup.dao;


import com.workup.workup.models.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "from Project p join Category c on p.id = c.id " +
            "where p.title like %:keyword% or p.description like %:keyword% or c.name like %:keyword%")
    List<Project> getProjectsByKeyword(@Param("keyword")String keyword);

@Query(value = "SELECT * FROM projects WHERE projects.id = (?1)", nativeQuery = true)
        Project getProjectById(Long projectId);

    @NotNull
    List<Project> findAll();

    // need to add "find ALL by" projects in a list
    List<Project> getAllProjectsByDeveloperUser (User developer);

    List<Project> getAllprojectsByStatusAndUser(String status, User user);

    List<Project> findByCategoriesContains(Category name);
}
