package com.liufuhao.crowd.service;


import java.util.List;

import com.liufuhao.crowd.entity.vo.DetailProjectVO;
import com.liufuhao.crowd.entity.vo.PortalTypeVO;
import com.liufuhao.crowd.entity.vo.ProjectVO;

public interface ProjectService {
	
    void saveProject(ProjectVO projectVO, Integer memberId);
    
    List<PortalTypeVO> getPortalTypeVO();
    
    DetailProjectVO getDetailProjectVO(Integer projectId);
}
