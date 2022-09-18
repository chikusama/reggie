package com.example.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    //重写Response中响应数据,将原本long类型转换成json的long类型,重写成String类型
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        converters.add(0, messageConverter);
    }

    /**
     * 元对象处理器
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            /**
             * 1.什么时机触发填充：在insert into employee values..
             * 2.给哪个属性填充什么内容：createTime/updateTime/createUser/updateUser
             * @param metaObject
             */
            @Override
            public void insertFill(MetaObject metaObject) {
                //下单时间
                this.strictInsertFill(metaObject, "orderTime", LocalDateTime.class, LocalDateTime.now());
                this.strictInsertFill(metaObject, "checkoutTime", LocalDateTime.class, LocalDateTime.now());
                this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
                this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
                //注意：此时无法在元对象处理器获取session对象，故而这里暂且先赋值一个假数据，等一会我们再来用其他技术来完善
                this.strictInsertFill(metaObject, "createUser", Long.class, (Long)BaseContext.getThreadLocal());
                this.strictInsertFill(metaObject, "updateUser", Long.class, (Long)BaseContext.getThreadLocal());
                //设置自动填充的userId
                this.strictInsertFill(metaObject,"userId",Long.class,(Long)BaseContext.getThreadLocal());
            }

            /**
             * 1.什么时机触发填充：在update  employee set  xx=yy
             * 2.给哪个属性填充什么内容：updateTime/updateUser
             * @param metaObject
             */
            @Override
            public void updateFill(MetaObject metaObject) {
                this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
                //注意：此时无法在元对象处理器获取session对象，故而这里暂且先赋值一个假数据，等一会我们再来用其他技术来完善
                //从ThreadLocal中取出值
                this.strictUpdateFill(metaObject, "updateUser", Long.class,(Long)BaseContext.getThreadLocal());
            }
        };
    }

}
