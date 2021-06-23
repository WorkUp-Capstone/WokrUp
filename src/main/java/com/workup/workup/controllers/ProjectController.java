package com.workup.workup.controllers;

import dao.ProjectsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProjectController {

    //need to inject Dependencies for PROJECTS and USERS:
//private final ProjectsRepository projectDao;
//
//public OwnerProfileController(ProjectsRepository projectDao){
//    this.projectDao = projectDao;
//}


    //display ALL projects
    @GetMapping("/owner-profile/projects")
    public String projectsIndex(Model model){
        //model.addAttribute("allProjects", daoName.findAll());
        return "projects/index"; // ?? may need return refactor
    }

    //display selected single project
    @GetMapping("/owner-profile/projects/{id}")
    public String showProject(@PathVariable long id, Model model){
        //model.addAttribute("showProject", daoName.getById(id));
        return "projects/show";
    }

    //create a Project
    @GetMapping("/owner-profile/projects/create")
    public String viewProjectCreateForm(Model model){
        //model.addAttribute("project", new Project());
        return "projects/create";
    }

    //save created project
    @PostMapping("/owner-profile/projects/create")
    public String projectCreateForm(){
        //need to include @requestParams, dao.getById(id), setters, dao.save(project)
        return "redirect:/projects/index"; //not sure where we would be redirecting the user here?
    }

    //edit selected project
    @GetMapping("/owner-profile/projects/edit{id}")
    public String editProjectForm(@PathVariable long id, Model model){
        //Object object = daoname.getBy(id);
        //model.addAttribute("project", project);
        return "projects/edit";
    }

    //edit and save project
    @PostMapping("/owner-profile/projects/edit{id}")
    public String editProject(@PathVariable long id){
        //need to include @RequestParams, Dao.getById(id), setters, dao.save(object)
        return "redirect:/projects/{id}"; //where are we redirecting them? Profile or home
    }
}
