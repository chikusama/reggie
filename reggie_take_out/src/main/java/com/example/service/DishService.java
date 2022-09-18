package com.example.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.DishDto;
import com.example.pojo.Dish;

import java.util.List;

public interface DishService extends IService<Dish>{

    //新增Dish方法
    void saveDishWithFlavors(DishDto dishDto);
    //分页查询方法
    Page<DishDto> dishPageInfo(Integer page, Integer pageSize, String name);
    //根据id进行查询
    DishDto selectById(Long id);
    //修改方法
    void updateDish(DishDto dishDto);
    //根据category和status进行查询
    List<Dish> searchDishList(Long categoryId, Integer status);

    void deleteDish(String[] ids);
}
