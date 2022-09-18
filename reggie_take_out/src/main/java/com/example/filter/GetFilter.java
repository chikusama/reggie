package com.example.filter;

import com.example.common.BaseContext;
import com.example.common.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//对所有访问进行拦截
@WebFilter("/*")
@Slf4j
public class GetFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        //对静态页面以及登录等处页面放行
        //定义一个数组存放方形的uri
        String[] uris = {"/backend/**","/employee/login","/employee/logout"};
        //从session中获取用户信息
        HttpServletRequest req = (HttpServletRequest) request;
        AntPathMatcher pathMatcher = new AntPathMatcher();
        //获取uri路径
        String requestURI = req.getRequestURI();
        //遍历String数组进行遍历匹配
        for (String uri : uris) {
            if (pathMatcher.match(uri,requestURI)) {
                //如果符合进行放行
                log.info("请求放行{}",requestURI);
                filterChain.doFilter(request,response);
                return;
            }
        }
        //如果session中没有parameter值则返回R.error
        //获取的参数是controller层中设定到域中的name
        Long employeeId = (Long) req.getSession().getAttribute("employeeId");
        if (employeeId==null) {
            ObjectMapper objectMapper = new ObjectMapper();
            //把数据转换成json数据
            String notlogin = objectMapper.writeValueAsString(R.error("NOTLOGIN"));
            log.info("session没有改数据");
            response.getWriter().write(notlogin);
            return;
        }
        //在当前的请求线程中塞入登录用户的值
        BaseContext.setThreadLocal(employeeId);
        //其他放行
        filterChain.doFilter(request,response);
    }
}
