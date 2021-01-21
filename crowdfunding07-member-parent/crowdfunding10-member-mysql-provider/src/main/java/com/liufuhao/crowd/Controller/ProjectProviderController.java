package com.liufuhao.crowd.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liufuhao.crowd.entity.vo.DetailProjectVO;
import com.liufuhao.crowd.entity.vo.PortalTypeVO;
import com.liufuhao.crowd.entity.vo.ProjectVO;
import com.liufuhao.crowd.service.ProjectService;
import com.liufuhao.crowd.util.ResultEntity;

@RestController
public class ProjectProviderController {
    @Autowired
    private ProjectService projectService;
    
    @RequestMapping("/get/project/detail/remote/{projectId}")
    public ResultEntity<DetailProjectVO> getDetailProjectVORemote(@PathVariable("projectId") Integer projectId) {
        try {
            DetailProjectVO detailProjectVO = projectService.getDetailProjectVO(projectId);
            return ResultEntity.successWithData(detailProjectVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
    
    @RequestMapping("/get/portal/type/project/data/remote")
    public ResultEntity<List<PortalTypeVO>> getPortalTypeProjectDataRemote() {
        try {
            List<PortalTypeVO> portalTypeVOList = projectService.getPortalTypeVO();
            return ResultEntity.successWithData(portalTypeVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/save/project/vo/remote")
    public ResultEntity<String> saveProjectVORemote(
            @RequestBody ProjectVO projectVO,
            @RequestParam("memberId") Integer memberId) {
        try {
            // 调用“本地”Service 执行保存
            projectService.saveProject(projectVO, memberId);
            return ResultEntity.successWithOutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    ;
}
