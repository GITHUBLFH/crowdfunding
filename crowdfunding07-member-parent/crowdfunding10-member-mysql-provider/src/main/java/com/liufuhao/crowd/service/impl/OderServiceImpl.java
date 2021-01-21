package com.liufuhao.crowd.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.jni.Address;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.liufuhao.crowd.entity.po.AddressPOExample;
import com.liufuhao.crowd.entity.po.OrderAddressPO;
import com.liufuhao.crowd.entity.po.OrderPO;
import com.liufuhao.crowd.entity.vo.AddressVO;
import com.liufuhao.crowd.entity.vo.OrderProjectVO;
import com.liufuhao.crowd.entity.vo.OrderVO;
import com.liufuhao.crowd.mapper.OrderPOMapper;
import com.liufuhao.crowd.mapper.AddressPOMapper;
import com.liufuhao.crowd.mapper.OrderAddressPOMapper;
import com.liufuhao.crowd.service.OrderService;

@Service
@Transactional(readOnly = true)
public class OderServiceImpl implements OrderService {
    @Autowired
    private OrderAddressPOMapper orderProjectMapper;
    @Autowired
    private OrderPOMapper orderPOMapper;
    @Autowired
    private AddressPOMapper addressMapper;

    @Override
    public OrderProjectVO getOrderProjectVO(Integer projectId, Integer returnId) {
        return orderProjectMapper.selectOrderProjectVO(returnId);
    }

    @Override
    public List<AddressVO> getAddressVOList(Integer memberId) {
        AddressPOExample example = new AddressPOExample();
        example.createCriteria().andMemberIdEqualTo(memberId);
        List<Address> addressList = addressMapper.selectByExample(example);
        List<AddressVO> addressVOList = new ArrayList<>();
        for (Address addressPO : addressList) {
            AddressVO addressVO = new AddressVO();
            BeanUtils.copyProperties(addressPO, addressVO);
            addressVOList.add(addressVO);
        }
        return addressVOList;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void saveAddress(AddressVO addressVO) {
        Address address = new Address();
        BeanUtils.copyProperties(addressVO, address);
        addressMapper.insert(address);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void saveOrder(OrderVO orderVO) {
        OrderPO orderPO = new OrderPO();
        BeanUtils.copyProperties(orderVO, orderPO);
        OrderAddressPO orderProject = new OrderAddressPO();
        BeanUtils.copyProperties(orderVO.getOrderProjectVO(), orderProject);
        orderPOMapper.insert(orderPO);
        Integer id = orderPO.getId();
        orderProject.setOrderId(id);
        orderProjectMapper.insert(orderProject);
    }
}
