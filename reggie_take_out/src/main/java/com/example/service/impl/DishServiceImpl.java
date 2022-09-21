package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.CustomerException;
import com.example.dto.DishDto;
import com.example.mapper.DishMapper;
import com.example.pojo.Dish;
import com.example.pojo.DishFlavor;
import com.example.service.DishFlavorService;
import com.example.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
//开启事务
@Transactional
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    //调用mapper方法
    @Autowired
    DishMapper dishMapper;
    @Autowired
    DishFlavorService dishFlavorService;
    @Autowired
    RedisTemplate redisTemplate;


    @Override
    //删除原来的数据
    @CacheEvict(value = "dish", key = "#dishDto.categoryId+':'+#dishDto.status")
    public void saveDishWithFlavors(DishDto dishDto) {
        //String key = "dish:" + dishDto.getCategoryId() + ":" + dishDto.getStatus();
        //将dishDto存储到本地MySql中
        this.save(dishDto);
        //存入到redis中
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.forEach(flavor -> {
            //给dishflavor的dish_id赋值
            flavor.setDishId(dishDto.getId());

        });
        dishFlavorService.saveBatch(flavors);
        /*//删除redis中的缓存
        redisTemplate.delete(key);*/
    }

    @Override
    //将查询的数据存到redis缓存中
    public Page<DishDto> dishPageInfo(Integer page, Integer pageSize, String name) {
        //调用mapper中的方法传入参数
        //先获得page类型数据
        /*Page<DishDto> pageInfo = new Page<>(page,pageSize);*/
        return dishMapper.dishPageInfo(new Page<DishDto>(page, pageSize), name);
    }

    /**
     * select * from dish,dish_flavor where dish.id = dish_id;
     *
     * @param id
     * @return
     */
    @Override
    public DishDto selectById(Long id) {
        //根据id进行菜品查询
        Dish dish = this.getById(id);
        //创建一个DishDto用来接收对象
        DishDto dishDto = new DishDto();

        //使用BeanUtils进行属性的赋值
        //有值的向无值的赋值
        BeanUtils.copyProperties(dish, dishDto);
        //跟据dish的dish.id对dish_flavor进行查询
        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId, dish.getId());
        //获取flavor的集合
        List<DishFlavor> flavors = dishFlavorService.list(lqw);
        dishDto.setFlavors(flavors);

        return dishDto;
    }

    /**
     * 修改套餐信息
     *
     * @param dishDto
     */

    @Override
    public void updateDish(DishDto dishDto) {
        //String key = "dish:" + dishDto.getCategoryId() + ":" + dishDto.getStatus();
        dishDto.setUpdateUser(null);
        //保存Dish
        this.updateById(dishDto);
        //删除口味
        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        //delete from dish_flavor where dish_id = #{id}
        Long dishDtoId = dishDto.getId();
        lqw.eq(DishFlavor::getDishId, dishDtoId);
        dishFlavorService.remove(lqw);
        //保存口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        List<DishFlavor> dishFlavors = flavors.stream().map(flavor -> {
            flavor.setUpdateTime(null);
            flavor.setUpdateUser(null);
            //遍历flavors,将dishid添加进
            flavor.setDishId(dishDtoId);
            return flavor;
        }).collect(Collectors.toList());//将修改过后的集合整理成新的集合
        dishFlavorService.saveBatch(dishFlavors);
        this.updateById(dishDto);

        /*//删除redis中的缓存
        redisTemplate.delete(key);*/
    }

    /**
     * 显示套餐内的菜品信息
     *
     * @param categoryId
     * @param status
     * @return
     */
    @Override
    public List<Dish> searchDishList(Long categoryId, Integer status) {
        //跟据categoryId进行查询
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Dish::getCategoryId, categoryId);
        lqw.eq(Dish::getStatus, status);
        //根据sort进行升序,updatetimae降序
        lqw.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        return this.list(lqw);
    }

    /**
     * 删除赛品
     *
     * @param ids
     */
    @Override
    public void deleteDish(String[] ids) {

        //根据ids进行查询status为1的数量
        LambdaQueryWrapper<Dish> dishlqw = new LambdaQueryWrapper<>();
        dishlqw.eq(Dish::getStatus, 1);
        dishlqw.in(Dish::getId, ids);

        int count = this.count(dishlqw);

        if (count > 0) {
            throw new CustomerException("菜品启售中,不能删除!");
        }
        //删除关联的菜品口味
        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.in(DishFlavor::getDishId, ids);
        dishFlavorService.remove(lqw);
        //删除菜品
        this.removeByIds(Arrays.asList(ids));

    }
}
