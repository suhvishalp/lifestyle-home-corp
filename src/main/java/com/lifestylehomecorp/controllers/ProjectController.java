package com.lifestylehomecorp.controllers;

import com.commercetools.api.models.project.Project;
import com.lifestylehomecorp.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/project-details")
    public ResponseEntity<Project> getProjectDetails() {
        return projectService.getProjectDetails();
    }
}
