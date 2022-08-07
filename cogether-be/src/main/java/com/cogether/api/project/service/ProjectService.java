package com.cogether.api.project.service;

import com.cogether.api.project.domain.*;
import com.cogether.api.project.exception.ProjectNotFoundException;
import com.cogether.api.project.repository.ProjectRepository;
import com.cogether.api.project.repository.ProjectScrapRepository;
import com.cogether.api.project.repository.ProjectSkillRepository;
import com.cogether.api.user.dto.User;
import com.cogether.api.user.exception.UserNotFoundException;
import com.cogether.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectSkillRepository projectSkillRepository;
    private final ProjectScrapRepository projectScrapRepository;
    private final UserRepository userRepository;

    public ProjectResponse.OnlyId create(ProjectRequest.Create_Project create_project){
        User user = userRepository.findById(create_project.getUserId()).orElseThrow(UserNotFoundException::new);
        System.out.println(user.getNickname());
        Project project = create_project.toEntity(user);
        System.out.println(project.getContent());
        Project savedProject = projectRepository.save(project);
        List<String> list = create_project.getSkillList();
        System.out.println(list.get(0));
        for (int i = 0; i < list.size(); i++){
            String skill = list.get(i);
            ProjectSkill projectSkill = ProjectSkill.toEntity(savedProject, skill);
            projectSkillRepository.save(projectSkill);
        }
        return ProjectResponse.OnlyId.build(savedProject);
    }

    public ProjectResponse.OnlyId delete(int projectId){
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
        List<ProjectSkill> list = projectSkillRepository.findAllByProject_Id(projectId);
        for (int i = 0 ; i<list.size(); i++){
            ProjectSkill projectSkill = list.get(i);
            projectSkillRepository.deleteById(projectSkill.getId());
        }
        projectRepository.deleteById(projectId);
        return ProjectResponse.OnlyId.build(project);
    }

    public ProjectResponse.ProjectAll getProjectDetail(int projectId, int userId){
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<ProjectSkill> list = projectSkillRepository.findAllByProject_Id(projectId);
        int check = projectScrapRepository.countAllByProjectAndUser(project, user);
        boolean isScrap = false;
        if(check == 1){
            isScrap = true;
        }
        return ProjectResponse.ProjectAll.build(project, list, isScrap);
    }

    public ProjectResponse.ProjectList getProjectList(int userId){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<ProjectResponse.ProjectAll> projectList = new ArrayList<>();
        List<Project> list = projectRepository.findAll();
        for (int i = 0; i < list.size(); i++){
            Project project = list.get(i);
            List<ProjectSkill> projectSkillList = projectSkillRepository.findAllByProject_Id(project.getId());
            int check = projectScrapRepository.countAllByProjectAndUser(project, user);
            boolean isScrap = false;
            if(check == 1){
                isScrap = true;
            }
            projectList.add(ProjectResponse.ProjectAll.build(project, projectSkillList, isScrap));
        }
        return ProjectResponse.ProjectList.build(projectList);
    }

    public ProjectResponse.OnlyProjectScrapId createScrap(ProjectRequest.Create_ProjectScrap create_projectScrap){
        User user = userRepository.findById(create_projectScrap.getUserId()).orElseThrow(UserNotFoundException::new);
        Project project = projectRepository.findById(create_projectScrap.getProjectId()).orElseThrow(ProjectNotFoundException::new);
        ProjectScrap projectScrap = create_projectScrap.toEntity(project, user);
        ProjectScrap savedProjectScrap = projectScrapRepository.save(projectScrap);
        return ProjectResponse.OnlyProjectScrapId.build(savedProjectScrap);
    }

    public ProjectResponse.OnlyProjectScrapId deleteScrap(int userId, int projectId){
        ProjectScrap projectScrap = projectScrapRepository.findByProject_IdAndUser_Id(projectId, userId);
        projectScrapRepository.deleteById(projectScrap.getId());
        return ProjectResponse.OnlyProjectScrapId.build(projectScrap);
    }
}
