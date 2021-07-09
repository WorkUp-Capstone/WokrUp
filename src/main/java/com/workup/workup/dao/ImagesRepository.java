package com.workup.workup.dao;

import com.workup.workup.models.Project;
import com.workup.workup.models.ProjectImage;
import com.workup.workup.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagesRepository extends JpaRepository<ProjectImage, Long> {

    ProjectImage getProjectImagePathByProject(Project project);

    List<ProjectImage>getAllProjectImageByProjectId(Long ownerUserId);
}
