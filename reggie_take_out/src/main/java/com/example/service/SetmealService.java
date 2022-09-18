package com.example.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.SetmealDto;
import com.example.pojo.Dish;
import com.example.pojo.Setmeal;

public interface SetmealService extends IService<Setmeal>{
    //分页查询方法
    Page<SetmealDto> getSetMeal(Integer page,Integer pageSize,String name);
    //根据id进行回显数据
    SetmealDto selectMeal(Long id);

    void updateSetmeal(SetmealDto setmealDto);

    void addSetmeal(SetmealDto setmealDto);

    void deleteSetmeal(Long[] ids);
}
