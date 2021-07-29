package com.workup.workup.services;

import com.workup.workup.dao.ProjectsRepository;
import com.workup.workup.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

  @Autowired private ProjectsRepository projectsDao;

  public List<Project> getProjectsByKeyword(String keyword) {
    return projectsDao.getProjectsByKeyword(keyword);
  }
}
