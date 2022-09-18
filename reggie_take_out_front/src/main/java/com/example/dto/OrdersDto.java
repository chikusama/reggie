package com.example.dto;

import com.example.pojo.OrderDetail;
import com.example.pojo.Orders;
import lombok.Data;

import java.util.List;


@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;
	
}
