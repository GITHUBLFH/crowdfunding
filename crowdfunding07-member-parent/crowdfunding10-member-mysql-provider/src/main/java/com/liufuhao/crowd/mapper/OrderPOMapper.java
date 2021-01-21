package com.liufuhao.crowd.mapper;



import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.liufuhao.crowd.entity.po.OrderPO;
import com.liufuhao.crowd.entity.po.OrderPOExample;
@Service
public interface OrderPOMapper {
    int countByExample(OrderPOExample example);

    int deleteByExample(OrderPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderPO record);

    int insertSelective(OrderPO record);

    List<OrderPO> selectByExample(OrderPOExample example);

    OrderPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderPO record, @Param("example") OrderPOExample example);

    int updateByExample(@Param("record") OrderPO record, @Param("example") OrderPOExample example);

    int updateByPrimaryKeySelective(OrderPO record);

    int updateByPrimaryKey(OrderPO record);
}