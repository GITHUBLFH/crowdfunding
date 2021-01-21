package com.liufuhao.crowd.service;


import java.util.List;

import com.liufuhao.crowd.entity.vo.AddressVO;
import com.liufuhao.crowd.entity.vo.OrderProjectVO;
import com.liufuhao.crowd.entity.vo.OrderVO;

public interface OrderService {
    OrderProjectVO getOrderProjectVO(Integer projectId, Integer returnId);

    List<AddressVO> getAddressVOList(Integer memberId);

    void saveAddress(AddressVO addressVO);

    void saveOrder(OrderVO orderVO);
}
