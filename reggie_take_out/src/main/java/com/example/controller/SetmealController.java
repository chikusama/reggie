package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.R;
import com.example.dto.SetmealDto;
import com.example.pojo.Setmeal;
import com.example.service.SetmealDishService;
import com.example.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 套餐管理
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    SetmealService setmealService;
    @Autowired
    SetmealDishService setmealDishService;

    //分页查询
    @GetMapping("/page")
    public R<Page> getSetMeal(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              String name) {
        return R.success(setmealService.getSetMeal(page, pageSize, name));
    }

    //根据id进行回显
    @GetMapping("/{id}")
    public R<SetmealDto> getSingleSetmeal(@PathVariable Long id) {
        return R.success(setmealService.selectMeal(id));
    }

    //修改保存
    //修改也会影响多个分类,因此也全部删除
    @PutMapping
    @CacheEvict(value = "setmeal", allEntries = true)
    public R<String> updateMeal(@RequestBody SetmealDto setmealDto) {
        setmealService.updateSetmeal(setmealDto);
        return R.success("修改成功");
    }

    //新增套餐
    //删除原来的套餐分类
    @PostMapping
    @CacheEvict(value = "setmeal", key = "#setmealDto.categoryId+':'+#setmealDto.status")
    public R<String> addSetmeal(@RequestBody SetmealDto setmealDto) {
        setmealService.addSetmeal(setmealDto);
        return R.success("添加成功");
    }

    //批量删除
    //由于批量删除时会影像多个分类,因此将setmeal类下面的全部缓存删除:  allEntries = true
    @DeleteMapping
    @CacheEvict(value = "setmeal", allEntries = true)
    public R<String> deleteSetmeal(@RequestParam Long[] ids) {
        setmealService.deleteSetmeal(ids);
        return R.success("删除成功");
    }

    //批量修改启售,停售
    @PostMapping("/status/{status}")
    public R<List<Setmeal>> updateStatusBatch(@PathVariable Integer status, @RequestParam Long[] ids) {
        //根据ids进行查询
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.in(Setmeal::getId, Arrays.asList(ids));
        List<Setmeal> setmeals = setmealService.list(lqw);
        //批量修改状态
        LambdaUpdateWrapper<Setmeal> uw = new LambdaUpdateWrapper<>();
        uw.in(Setmeal::getId, Arrays.asList(ids));
        uw.set(Setmeal::getStatus, status);

        //设置修改时间和修改者为null
        for (Setmeal setmeal : setmeals) {
            setmeal.setUpdateTime(null);
            setmeal.setUpdateUser(null);
        }

        setmealService.update(uw);
        return R.success(setmeals);
    }

}
