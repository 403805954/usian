package com.usian.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;

import com.usian.util.Result;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileUpController {



    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @RequestMapping("/upload")
    public Result upload(MultipartFile file){

        String filename = file.getOriginalFilename();

        String  extFileName= filename.substring(filename.lastIndexOf("."));

        try {
            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), extFileName, null);

            String fullPath = "http://image.usian.com/"+storePath.getFullPath();
            return Result.ok(fullPath);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("上传失败");
        }

    }
}
