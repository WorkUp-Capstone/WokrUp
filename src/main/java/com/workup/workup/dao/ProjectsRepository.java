package com.workup.workup.dao;


import com.workup.workup.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
// PROJECT REPOSITORY
// PROJECT MODEL NOT CREATED OR CONNECTED YET! DELETE WHEN CONNECTED!!!! //
public interface ProjectsRepository extends JpaRepository<Project, Long> {

    Project findByTitle(String title); // select * from Projects where title = ?
    Project findFirstByTitle(String title); // select * from Projects where title = ? limit 1
    Project findByTitleLike(String searchPattern);  //find by title "%INPUT%"

    List<Project> searchByTitle(String term);


}
