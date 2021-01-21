package com.liufuhao.crowd.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.liufuhao.crowd.util.ResultEntity;

public class ProjectUtils {

	/*
     * 照片上传供功能
     * */
    public static ResultEntity<String> phoneUpload(MultipartFile multipartFile,String filePath) {
    	String folderName = new SimpleDateFormat("yyyyMMdd").format(new Date());
    	String fileName = multipartFile.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        filePath = filePath+folderName; // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath , fileName);
        //判断文件路径是否存在，不存在新建
        if (!dest.exists()) {
            dest.mkdirs();
        }
        try {
        	multipartFile.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    	
    	return ResultEntity.successWithData(dest.getPath());
    }
	
}
