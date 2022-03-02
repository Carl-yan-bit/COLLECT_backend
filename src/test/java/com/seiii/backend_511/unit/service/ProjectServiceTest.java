package com.seiii.backend_511.unit.service;

import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.po.project.Project;
import com.seiii.backend_511.service.project.ProjectService;
import com.seiii.backend_511.util.CONST;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.project.ProjectVO;
import com.seiii.backend_511.vo.project.UserProjectVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
@Transactional
public class ProjectServiceTest {
    @Resource
    ProjectService projectService;

    @Test
    public void testCreateProject(){
        Project project = new Project();
        project.setCreateTime(new Date());
        project.setTestTime(new Date());
        project.setDescription("我也没什么可说的");
        project.setType(CONST.STATE_OPEN);
        project.setWorkerAmount(1000);
        ResultVO<ProjectVO> ans = projectService.createProject(new ProjectVO(project));
        project.setUserId(100);
        //这里项目没填完，应该创建失败
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        project.setName("孙钰昇的项目");
        ans = projectService.createProject(new ProjectVO(project));
        //这里用户id不存在
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        project.setUserId(1);
        ans = projectService.createProject(new ProjectVO(project));
        Assert.assertEquals(CONST.REQUEST_SUCCESS,ans.getCode().intValue());
    }
    @Test
    public void testGetProjectsByUserId(){
        PageInfo<ProjectVO> ans = projectService.getProjectsByUserId(2,2);
        Assert.assertEquals(5,ans.getSize());
    }
    @Test
    public void testJoinedProjects(){
        PageInfo<ProjectVO> ans = projectService.getJoinedProjects(3,1);
        Assert.assertEquals(1,ans.getSize());
    }
    @Test
    public void testGetProjectsByID(){
        Assert.assertEquals("testproject2",projectService.getProjectById(2).getDescription());
    }
    @Test
    public void testGetALLProjects(){
        Assert.assertEquals(5,projectService.getAllProjects(2).getSize());
    }
    @Test
    public void testGetActiveProjects(){
        PageInfo<ProjectVO> p = projectService.getActiveProjects(2);
        Assert.assertEquals(4,p.getSize());
    }
    @Test
    public void testJoinProjects(){
        UserProjectVO userProjectVO = new UserProjectVO();
        userProjectVO.setUserId(3);
        userProjectVO.setProjectId(2);
        ResultVO<ProjectVO> ans = projectService.joinProject(userProjectVO);
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        //这里是因为用户已经加入项目
        userProjectVO.setUserId(30);
        ans = projectService.joinProject(userProjectVO);
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        //不存在的用户加入项目
        userProjectVO.setUserId(3);
        userProjectVO.setProjectId(100);
        ans = projectService.joinProject(userProjectVO);
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        //用户加入不存在的项目
        userProjectVO.setUserId(2);
        userProjectVO.setProjectId(1);
        ans = projectService.joinProject(userProjectVO);
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        //项目人数已满
        userProjectVO.setUserId(1);
        userProjectVO.setProjectId(2);
        ans = projectService.joinProject(userProjectVO);
        Assert.assertEquals(CONST.REQUEST_SUCCESS,ans.getCode().intValue());
    }
    @Test
    public void quitProject(){
        UserProjectVO userProjectVO = new UserProjectVO();
        userProjectVO.setUserId(3);
        userProjectVO.setProjectId(1);
        ResultVO<ProjectVO> ans = projectService.quitProject(userProjectVO);
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        //这里是因为用户没有加入项目
        userProjectVO.setUserId(30);
        ans = projectService.quitProject(userProjectVO);
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        //不存在的用户退出
        userProjectVO.setUserId(3);
        userProjectVO.setProjectId(100);
        ans = projectService.quitProject(userProjectVO);
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        //用户退出不存在的项目
        userProjectVO.setUserId(2);
        userProjectVO.setProjectId(2);
        ans = projectService.quitProject(userProjectVO);
        Assert.assertEquals(CONST.REQUEST_SUCCESS,ans.getCode().intValue());
    }
    @Test
    public void testUpdateProject(){
        Project project = new Project();
        project.setId(1);
        project.setCreateTime(new Date());
        project.setTestTime(new Date());
        project.setDescription("我也没什么可说的");
        project.setType(CONST.STATE_OPEN);
        project.setWorkerAmount(1000);
        ResultVO<ProjectVO> ans = projectService.updateProject(new ProjectVO(project));
        project.setUserId(100);
        //这里项目没填完，应该创建失败
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        project.setName("孙钰昇的项目");
        ans = projectService.updateProject(new ProjectVO(project));
        //这里用户id不存在
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        project.setUserId(1);
        ans = projectService.updateProject(new ProjectVO(project));
        Assert.assertEquals(CONST.REQUEST_SUCCESS,ans.getCode().intValue());
    }
    @Test
    public void testDeleteProject(){
        ResultVO<ProjectVO> ans = projectService.deleteProject(300);
        Assert.assertEquals(CONST.REQUEST_FAIL,ans.getCode().intValue());
        ans = projectService.deleteProject(1);
        Assert.assertEquals(CONST.REQUEST_SUCCESS,ans.getCode().intValue());
    }
    @Test
    public void testGetProjectMembers(){
        Assert.assertEquals(2,projectService.getProjectNumbers(2).getData().intValue());
    }
}
