package com.example.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.SetmealDto;
import com.example.mapper.SetmealDishMapper;
import com.example.mapper.SetmealMapper;
import com.example.pojo.Setmeal;
import com.example.pojo.SetmealDish;
import com.example.service.SetmealDishService;
import com.example.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {

}
