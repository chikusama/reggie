package com.example.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.DishDto;
import com.example.mapper.DishMapper;
import com.example.pojo.Dish;
import com.example.pojo.DishFlavor;
import com.example.service.DishFlavorService;
import com.example.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
//开启事务
@Transactional
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    DishMapper dishMapper;
    @Autowired
    DishFlavorService dishFlavorService;
    //调用redisTemplate
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    @Cacheable(value = "dish", key = "#categoryId+':'+#status")
    public List<DishDto> getDish(Long categoryId, Integer status) {
        //定义redis中的key名字
        //String key = "dish:" + categoryId + ":" + status;
        //先从redis中进行查询,如果有直接返回
        //ValueOperations ops = redisTemplate.opsForValue();
        //因为存到redis中的数据是list数据因此,需要强转成list
        //List<DishDto> dishDtos = (List<DishDto>) ops.get(key);
        /*if (dishDtos != null) {
            return dishDtos;
        }*/
        //没有存入redis
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        //根据条件获取
        lqw.eq(Dish::getCategoryId, categoryId);
        lqw.eq(Dish::getStatus, status);
        List<Dish> dishes = this.list(lqw);

        //根据dish的id进行查询
        List<DishDto> dishDtos = dishes.stream().map(dish -> {
            //创建dishDto的集合
            DishDto dishDto = new DishDto();
            //浅克隆
            BeanUtils.copyProperties(dish, dishDto);
            //根据dishId获取flavors
            LambdaQueryWrapper<DishFlavor> lqw2 = new LambdaQueryWrapper<>();
            lqw2.eq(DishFlavor::getDishId, dish.getId());
            List<DishFlavor> flavors = dishFlavorService.list(lqw2);
            //把flavors放入到DishDto中
            dishDto.setFlavors(flavors);
            return dishDto;
        }).collect(Collectors.toList());
        //存入redis缓存中
        //ops.set(key,dishDtos);

        return dishDtos;
    }
}
