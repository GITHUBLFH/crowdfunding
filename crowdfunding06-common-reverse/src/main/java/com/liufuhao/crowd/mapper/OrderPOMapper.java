package com.liufuhao.crowd.mapper;

import com.liufuhao.crowd.entity.po.OrderPO;
import com.liufuhao.crowd.entity.po.OrderPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderPOMapper {
    int countByExample(OrderPOExample example);

    int deleteByExample(OrderPOExample example);

    int insert(OrderPO record);

    int insertSelective(OrderPO record);

    List<OrderPO> selectByExample(OrderPOExample example);

    int updateByExampleSelective(@Param("record") OrderPO record, @Param("example") OrderPOExample example);

    int updateByExample(@Param("record") OrderPO record, @Param("example") OrderPOExample example);
}