package com.liufuhao.crowd.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.liufuhao.crowd.entity.po.MemberPO;
import com.liufuhao.crowd.entity.vo.DetailProjectVO;
import com.liufuhao.crowd.entity.vo.PortalProjectVO;
import com.liufuhao.crowd.entity.vo.PortalTypeVO;
import com.liufuhao.crowd.mapper.MemberPOMapper;
import com.liufuhao.crowd.mapper.ProjectPOMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyBatisTest {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private MemberPOMapper memberPOMapper;

	@Autowired
	private ProjectPOMapper projectPOMapper;

	private static final Logger log = LoggerFactory.getLogger(MyBatisTest.class);

	@Test
    public void testLoadDetailProjectVO(){
        Integer projectId = 16;
        DetailProjectVO detailProjectVO = projectPOMapper.selectDetailProjectVO(projectId);
        log.debug(detailProjectVO.getProjectName());
        List<String> detailPicturePathList = detailProjectVO.getDetailPicturePathList();
        for (String s : detailPicturePathList) {
        	log.debug(s);
        }
    }
	
	@Test
	public void testLoadTypeData() {
		List<PortalTypeVO> typeVOList = projectPOMapper.selectPortalTypeVOList();
		for (PortalTypeVO portalTypeVO : typeVOList) {
			Integer id = portalTypeVO.getId();
			String name = portalTypeVO.getName();
			String remark = portalTypeVO.getRemark();
			log.info("name = " + name + "remark=" + remark);
			List<PortalProjectVO> portalProjectVOList = portalTypeVO.getPortalProjectVOList();
			for (PortalProjectVO portalProjectVO : portalProjectVOList) {
				if (portalProjectVO == null) {
					continue;
				}
				log.info(portalProjectVO.toString());
			}
		}
	}

	@Test
	public void testMapper() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		String source = "123123";

		String encode = passwordEncoder.encode(source);

		memberPOMapper.insert(new MemberPO(null, "zhangsan", encode, "张三", "zhangsan@qq.com", 1, 1, "张三", "123123", 2));
	}

	@Test
	public void test() throws SQLException {
		Connection connection = dataSource.getConnection();
		log.debug(connection.toString());
	}
}
