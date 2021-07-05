package com.workup.workup.dao;


import com.workup.workup.models.Profile;
import com.workup.workup.models.Project;
import com.workup.workup.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;


@Repository
// PROJECT REPOSITORY
// PROJECT MODEL NOT CREATED OR CONNECTED YET! DELETE WHEN CONNECTED!!!! //
public interface ProjectsRepository extends JpaRepository<Project, Long> {
    Project getProjectByOwnerUserIs(User user);
}
