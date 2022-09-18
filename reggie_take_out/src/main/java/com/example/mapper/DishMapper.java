package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.DishDto;
import com.example.pojo.Dish;
import org.apache.ibatis.annotations.Param;


public interface DishMapper extends BaseMapper<Dish> {
    //定义查询语句
    //list中的对象为DishDto
    //接收参数有 page对象,模糊查询数据
    Page<DishDto> dishPageInfo(Page<DishDto> page,@Param("name") String name);
}
