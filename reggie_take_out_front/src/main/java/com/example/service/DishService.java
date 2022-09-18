package com.example.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.DishDto;
import com.example.pojo.Dish;

import java.util.List;

public interface DishService extends IService<Dish>{

    List<DishDto> selectWithFlavors();
}
