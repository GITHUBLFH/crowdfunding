package com.liufuhao.crowd.mapper;

import com.liufuhao.crowd.entity.po.ProjectPO;
import com.liufuhao.crowd.entity.po.ProjectPOExample;
import com.liufuhao.crowd.entity.vo.DetailProjectVO;
import com.liufuhao.crowd.entity.vo.PortalProjectVO;
import com.liufuhao.crowd.entity.vo.PortalTypeVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProjectPOMapper {
    int countByExample(ProjectPOExample example);

    int deleteByExample(ProjectPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProjectPO record);

    int insertSelective(ProjectPO record);

    List<ProjectPO> selectByExample(ProjectPOExample example);

    ProjectPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByExample(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByPrimaryKeySelective(ProjectPO record);

    int updateByPrimaryKey(ProjectPO record);

	void insertTypeRelationship(@Param("typeIdList") List<Integer> typeIdList, @Param("projectId") Integer projectId);

	void insertTagRelationship(@Param("typeIdList") List<Integer> typeIdList, @Param("projectId") Integer projectId);
	
	List<PortalTypeVO> selectPortalTypeVOList();
	
	List<PortalProjectVO> selectPortalProjectVOList();

    DetailProjectVO selectDetailProjectVO(Integer projectId);
	
}