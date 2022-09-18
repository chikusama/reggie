package com.example.controller;


import com.example.common.R;
import com.example.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

//图片上传
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    //从yml文件中取文件名称
    @Value("${reggie.path}")
    private String fileDir;

    @Autowired
    DishService dishService;

    //上传照片
    @PostMapping("/upload")
    public R<String> uploadPic(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String prefix = UUID.randomUUID().toString();
        String imgName = prefix+suffix;

        file.transferTo(new File(fileDir + imgName));

        return R.success(imgName);
    }

    //下载图片
    @GetMapping("/download")
    public R<String> downloadPic(@RequestParam String name, HttpServletResponse response) throws IOException {
        //获取文件读取流
        FileInputStream fileInputStream = new FileInputStream(new File(fileDir+name));
        //获取网页输出流
        ServletOutputStream outputStream = response.getOutputStream();
        //定义用来接收数据的字节数组
        byte[] bys = new byte[1024];
        //一边读一边写文件
        int read = 0;
        while ((read = fileInputStream.read(bys)) != -1) {
            outputStream.write(bys,0,read);
            //刷新
            outputStream.flush();
        }
        //关闭流
        outputStream.close();
        fileInputStream.close();

        return R.success("上传成功");

    }

}
