package com.liufuhao.crowd.mapper;

import com.liufuhao.crowd.entity.po.OrderAddressPO;
import com.liufuhao.crowd.entity.po.OrderAddressPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderAddressPOMapper {
    int countByExample(OrderAddressPOExample example);

    int deleteByExample(OrderAddressPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderAddressPO record);

    int insertSelective(OrderAddressPO record);

    List<OrderAddressPO> selectByExample(OrderAddressPOExample example);

    OrderAddressPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderAddressPO record, @Param("example") OrderAddressPOExample example);

    int updateByExample(@Param("record") OrderAddressPO record, @Param("example") OrderAddressPOExample example);

    int updateByPrimaryKeySelective(OrderAddressPO record);

    int updateByPrimaryKey(OrderAddressPO record);
}