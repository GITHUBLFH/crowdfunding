package com.liufuhao.crowd.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.liufuhao.crowd.api.MySQLRemoteService;
import com.liufuhao.crowd.constant.CrowdConstant;
import com.liufuhao.crowd.entity.vo.PortalTypeVO;
import com.liufuhao.crowd.util.ResultEntity;

@Controller
public class PortalController {
	
	@Autowired
    private MySQLRemoteService mySQLRemoteService;
	
	@RequestMapping("/")
	public String showPortalPage(Model model) {
		// 1.调用MySQLRemoteService提供的方法查询首页要显示的数据
        ResultEntity<List<PortalTypeVO>> resultEntity = mySQLRemoteService.getPortalTypeProjectDataRemote();
        // 2.检查查询结果
        String result = resultEntity.getResult();
        if ("SUCCESS".equals(result)) {
            // 3.获取查询结果的数据
            List<PortalTypeVO> list = resultEntity.getData();
            // 4,存入模型
            model.addAttribute(CrowdConstant.ATTR_NAME_PORTAL_DATA, list);
        }
		// 应该加载数据的
		
		return "portal";
	}

}
