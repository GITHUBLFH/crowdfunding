package com.liufuhao.crowd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liufuhao.crowd.entity.Menu;
import com.liufuhao.crowd.entity.MenuExample;
import com.liufuhao.crowd.mapper.MenuMapper;
import com.liufuhao.crowd.service.api.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuMapper menuMapper;

	 @Override
	    public List<Menu> getAll() {
	        return menuMapper.selectByExample(new MenuExample());
	    }

	    @Override
	    public void saveMenu(Menu menu) {
	        menuMapper.insert(menu);
	    }

	    @Override
	    public void updateMenu(Menu menu) {
	        // 由于pid没有传入所以要选择有选择的更新
	        menuMapper.updateByPrimaryKeySelective(menu);
	    }

	    @Override
	    public void removeMenu(Integer id) {
	        menuMapper.deleteByPrimaryKey(id);
	    }
	
	
}
