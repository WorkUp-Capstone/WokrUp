package com.workup.workup.dao;

import com.workup.workup.models.ProjectImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<ProjectImage, Long> {

  List<ProjectImage> getAllProjectImageByProjectId(Long ownerUserId);

  List<ProjectImage> findAllProjectImagesByProjectId(Long projectId);
}
