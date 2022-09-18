package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.AddressBookMapper;
import com.example.mapper.DishMapper;
import com.example.pojo.AddressBook;
import com.example.pojo.Dish;
import com.example.service.AddressBookService;
import com.example.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
