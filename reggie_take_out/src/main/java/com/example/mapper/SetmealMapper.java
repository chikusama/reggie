package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.SetmealDto;
import com.example.pojo.Dish;
import com.example.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

public interface SetmealMapper extends BaseMapper<Setmeal> {

    //定义一个返回page类型的方法
    Page<SetmealDto> getSetMeat(Page<SetmealDto> setmealDtoPage,@Param("name") String name);
}
