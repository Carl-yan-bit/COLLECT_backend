package com.seiii.backend_511.controller.project;

import com.github.pagehelper.PageInfo;
import com.seiii.backend_511.po.project.Project;
import com.seiii.backend_511.po.project.UserProject;
import com.seiii.backend_511.service.project.ProjectService;
import com.seiii.backend_511.vo.ResultVO;
import com.seiii.backend_511.vo.project.ProjectVO;
import com.seiii.backend_511.vo.project.UserProjectVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Resource
    private ProjectService projectservice;


    /**
     * 创建project
     * POST: /project
     * @param project
     * @return
     */
    @PostMapping("")
    public ResultVO<ProjectVO> createProject(@RequestBody ProjectVO project){
        return projectservice.createProject(project);
    }

    /**
     * 更新project
     * PUT: /project/{pid}
     * @param project
     * @return
     */
    @PutMapping("/{pid}")
    public ResultVO<ProjectVO> updateProject(@PathVariable Integer pid ,@RequestBody ProjectVO project){
        //project.setId(pid);
        return projectservice.updateProject(project);
    }

    /**
     * 删除project
     * DELETE: /project/{pid}
     * @param pid
     * 
     */
    @DeleteMapping("/{pid}")
    public ResultVO<ProjectVO> deleteProject(@PathVariable Integer pid){
        return projectservice.deleteProject(pid);
    }


    /**
     * 查询project
     * GET: /project/{pid}?uid={uid}
     * @param pid, uid
     * @param uid
     * @return
     */
    @GetMapping("/{pid}")
    public ResultVO<ProjectVO> getProjectByIdWithUid(@PathVariable Integer pid,@RequestParam Integer uid){
        return projectservice.getProjectByIdWithUid(pid,uid);
    }

    /**
     * 获取所有project
     * GET: /project?isActive={isActive}&isJoined={isJoined}&uid={uid}&page={page}
     *
     */
    @GetMapping("")
    public PageInfo<ProjectVO> getAllProjects(@RequestParam(required = false, defaultValue = "false") Boolean isActive, @RequestParam(required = false, defaultValue = "false") Boolean isJoined, @RequestParam(required = false, defaultValue = "-1") Integer uid, @RequestParam Integer page){
        if(isActive!=null&&isActive){
            return projectservice.getActiveProjects(page);//获取所有活跃的project
        }
        else if(isJoined!=null&&isJoined){
            return projectservice.getJoinedProjects(uid,page);//获取用户加入过的project
        }
        else if(uid>0){
            return projectservice.getProjectsByUserId(uid,page);//获取用户创建的project
        }
        else return projectservice.getAllProjects(page);//返回所有project
    }

//    /**
//     * 获取所有活跃的project
//     * GET: /project/active/page/{page}
//     * @param page
//     * @return
//     */
//    @GetMapping("/active/page/{page}")
//    public PageInfo<ProjectVO> getActiveProjects(@PathVariable Integer page){
//        return projectservice.getActiveProjects(page);
//    }



//    /**
//     * 获取用户创建的project
//     * GET: /project?uid={uid}&page={page}
//     * @param uid
//     * @param page
//     * @return
//     */
//    @GetMapping("/user/{uid}/page/{page}")
//    public PageInfo<ProjectVO> getProjectsByUserId(@PathVariable Integer uid,@PathVariable Integer page){
//        return projectservice.getProjectsByUserId(uid,page);
//    }

//    /**
//     * 查看用户参加过的project
//     * GET: /project/joined?uid={uid}&page={page}
//     * @param uid
//     * @param page
//     * @return
//     */
//    @GetMapping("/joined")
//    public PageInfo<ProjectVO> getJoinedProjects(@RequestParam Integer uid,@RequestParam Integer page){
//        return projectservice.getJoinedProjects(uid,page);
//    }

    /**
     * 查询project
     * GET: /project/{pid}?uid={uid}
     * @param pid
     * @return
     */
    @Deprecated
    @GetMapping("/withoutUid/{pid}")
    public ProjectVO getProjectById(@PathVariable Integer pid){
        //System.out.println(projectservice.getProjectById(projectId));
        projectservice.onClick(pid);
        return projectservice.getProjectById(pid);
    }



    /**
     * 加入任务
     * POST: /project/join
     * @param userProjectVO
     * @return
     */
    @PostMapping("/join")
    public ResultVO<ProjectVO> joinProject(@RequestBody UserProjectVO userProjectVO){
        return projectservice.joinProject(userProjectVO);
    }

    /**
     * 退出任务
     * POST: /project/quit
     * @param userProjectVO
     * @return
     */
    @PostMapping("/quit")
    public ResultVO<ProjectVO> quitProject(@RequestBody UserProjectVO userProjectVO){
        return projectservice.quitProject(userProjectVO);
    }

    /**
     * 得到当前项目参与用户数量
     * GET: /project/{pid}/memberNum
     * @param pid
     * @return
     */
    @GetMapping("/{pid}/memberNum")
    public ResultVO<Integer> getMemberNum(@PathVariable Integer pid){
        return projectservice.getProjectNumbers(pid);
    }


    /**
     * 获取任务推荐
     * GET: /project/recommendation?uid={uid}
     * @param 
     * @return
     */
    @GetMapping("/recommendation")
    public ResultVO<List<ProjectVO>> getRecommendation(@RequestParam Integer uid){
        return projectservice.getRecommendation(uid);
    }
}
