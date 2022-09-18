package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.R;
import com.example.pojo.Employee;
import com.example.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("employee")
@Slf4j
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    //登录接口
    @PostMapping("login")
    public R<Employee> login(@RequestBody Employee employee, HttpSession session) {
        //根据employee的name进行查询
        QueryWrapper<Employee> qw = new QueryWrapper<>();
        qw.eq("username",employee.getUsername());
        //从数据库中获取对应username的数据
        Employee emp  = employeeService.getOne(qw);
        //如果没有值则表示输入用户名称错误
        if (emp == null) {
            return R.error("用户名输入错误");
        }
        //根据employee的密码跟emp的密码进行对比
        //加密密码
        String hexPassword = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());
        if (!hexPassword.equals(emp.getPassword())) {
            return R.error("密码输入错误");
        }

        //上述两个都没有问题,根据emp的status属性进行判断
        if (emp.getStatus() == 0) {
            return R.error("用户状态不可用");
        }
        //都没有问题存入session中,以方便过滤器过滤
        session.setAttribute("employeeId",emp.getId());
        //清除敏感信息
        emp.setIdNumber("");
        emp.setPassword("");
        //返回根据employee查询的emp信息
        return R.success(emp);

    }

    //退出系统
    //退出系统是根据id进行查询退出
    @PostMapping("logout")
    public R<String> logout(HttpSession session) {
        //转发到index页面,然后清空employee数据
        session.removeAttribute("employeeId");
        return R.success("成功退出!");
    }

    //新增员工
    //static/backend/page/employee/add.html
    @PostMapping
    public R<Employee> addEmp(@RequestBody Employee employee, HttpSession session) {
        //设置默认密码
        String password = "123456";
        //加密
        String hex = DigestUtils.md5DigestAsHex(password.getBytes());
        employee.setPassword(hex);
        //对重复的username进行全局异常捕获
        employeeService.save(employee);
        return R.success(employee);
    }

    //显示员工数据
    //可能需要查询employee因此需要请求体
    @GetMapping("/page")
    public R<Page> selectByPage(@RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                String name) {
        Page<Employee> pageEmployee = new Page<>(page, pageSize);
        QueryWrapper<Employee> qw = new QueryWrapper<>();
        //如果name有值则根据name进行模糊查询
        qw.like(StringUtils.isNotBlank(name), "name", name);
        employeeService.page(pageEmployee, qw);
        return R.success(pageEmployee);
    }

    //员工编辑页面
    @GetMapping("/{id}")
    public R<Employee> selectById(@PathVariable Long id) {
        //修改员工
        Employee employee = employeeService.getBaseMapper().selectById(id);
        return R.success(employee);
    }

    //员工启用禁用
    //update employee set status=? and create_time=? and create_user=? where id = ?
    @PutMapping
    public R<String> updateEmployee(@RequestBody Employee employee) {
        //启用禁用
        //根据id进行修改
        UpdateWrapper<Employee> uw = new UpdateWrapper<>();
        uw.eq("id",employee.getId());
        //将updateTime updateUser值清空
        employee.setUpdateUser(null);
        employee.setUpdateTime(null);
        employeeService.update(employee,uw);
        return R.success("修该成功");
    }
}
