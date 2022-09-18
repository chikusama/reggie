package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.CustomerException;
import com.example.dto.SetmealDto;
import com.example.mapper.SetmealMapper;
import com.example.pojo.Setmeal;
import com.example.pojo.SetmealDish;
import com.example.service.SetmealDishService;
import com.example.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    SetmealMapper setmealMapper;
    @Autowired
    SetmealDishService setmealDishService;

    //重写方法进行查询
    @Override
    public Page<SetmealDto> getSetMeal(Integer page, Integer pageSize, String name) {
        return setmealMapper.getSetMeat(new Page<SetmealDto>(page, pageSize), name);
    }

    /**
     * 请求 URL: http://localhost:8080/dish/list?categoryId=1397844263642378242&status=1
     * 根据categoryId 和 status来查询的
     *
     * @param id
     * @return
     */
    @Override
    public SetmealDto selectMeal(Long id) {
        //根据id进行查询
        Setmeal setmeal = this.getById(id);
        //定义一个SetmealDto值用来接收setmeal
        SetmealDto dto = new SetmealDto();
        //根据setmeal中的id查询setmealdish
        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SetmealDish::getSetmealId, setmeal.getId());
        //select * from setmeal,setmealdish where setmeal.id= setmeal_id
        List<SetmealDish> setmealDishes = setmealDishService.list(lqw);

        dto.setSetmealDishes(setmealDishes);
        //使用BeanUtils方法进行浅克隆
        BeanUtils.copyProperties(setmeal, dto);
        return dto;
    }

    /**
     * 修改
     *
     * @param setmealDto
     */
    @Override
    public void updateSetmeal(SetmealDto setmealDto) {
        //先保存setmeal
        this.updateById(setmealDto);
        //先删除本地的setmeal dish
        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SetmealDish::getSetmealId, setmealDto.getId());
        setmealDishService.remove(lqw);
        //再保存setmeal dish
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        //遍历集合设置setmeal id
        setmealDishes.stream().map(dish -> {
            dish.setUpdateTime(null);
            dish.setUpdateUser(null);
            dish.setSetmealId(setmealDto.getId());
            return dish;
        }).collect(Collectors.toList());
        //批量保存
        setmealDishService.saveBatch(setmealDishes);

    }

    /**
     * 新增
     *
     * @param setmealDto
     */
    @Override
    public void addSetmeal(SetmealDto setmealDto) {
        //先把setmeal保存
        this.save(setmealDto);
        //保存setmealdish
        //insert into setmealdish values(,,,,) where setmeal.id = setmeal_id
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map(dish -> {
            dish.setSetmealId(setmealDto.getId());
            return dish;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 如果状态为在售状态则不能删除
     *
     * @param ids
     */

    @Override
    public void deleteSetmeal(Long[] ids) {
        //根据ids进行状态的查询
        LambdaQueryWrapper<Setmeal> setlqw = new LambdaQueryWrapper<>();
        setlqw.in(Setmeal::getId, Arrays.asList(ids));
        setlqw.eq(Setmeal::getStatus, 1);
        int count = this.count(setlqw);
        if (count > 0) {
            throw new CustomerException("套餐启售中!不能删除");

        }
        //根据setmealid删除表中关联的菜品信息
        //根据id进行查询
        //delete from setmealdish where setmeal_id in (id,id)
        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        lqw.in(SetmealDish::getSetmealId, Arrays.asList(ids));
        setmealDishService.remove(lqw);
        //根据ids删除套餐中的套餐
        this.removeByIds(Arrays.asList(ids));

    }
}
