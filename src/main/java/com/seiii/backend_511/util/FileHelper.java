package com.seiii.backend_511.util;


import com.seiii.backend_511.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
public class FileHelper {
    /**
     * 保存文件
     * @param directoryPath 目录路径（以 / 结尾）
     * @param file 文件
     * @return 保存成功后的文件名
     */
    public static ResultVO save(String directoryPath, MultipartFile file) throws IOException {
        if(!checkDirectoryPath(directoryPath)){
            throw new IOException("服务器端错误，用于存放上传文件的文件夹不存在或创建失败！");
        }
        // 原文件名
        String name = file.getOriginalFilename();
        String dir=directoryPath+"/"+name;
        // 根据目标地址构造文件输出流
        FileOutputStream fileOutputStream = new FileOutputStream(dir);
        // 将文件复制到映射的地址
        FileCopyUtils.copy(file.getInputStream(),fileOutputStream);

        return new ResultVO(CONST.REQUEST_SUCCESS,"文件保存成功",dir);
    }

    /**
     * 加载文件为资源
     * @param directoryPath 目录路径（以 / 结尾）
     * @return 输入流资源
     */
    public static Resource loadFileAsResource(String directoryPath) {
        try {
            Path filePath = Paths.get(directoryPath);
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists())
                return resource;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
         }
        return null;
    }

    public static ResultVO delete(String directoryPath){
        if(StringUtils.hasText(directoryPath)){
            File file = new File(directoryPath);
            if(file.exists()) {
                // 当且仅当文件被成功删除后返回true
                if(file.delete()){
                    return new ResultVO(CONST.REQUEST_SUCCESS,"成功删除");
                }
            }
        }
        return new ResultVO(CONST.REQUEST_FAIL,"删除失败");

    }

    public static void download(String path,String name, HttpServletResponse response){
        InputStream inputStream = null;
        OutputStream outputStream = null;
        response.setContentType("application/x-msdownload");
        try {
            Resource resource = FileHelper.loadFileAsResource(path);
            if(resource == null)
                throw new IOException();
            inputStream = resource.getInputStream();
            //1.设置文件ContentType类型
            response.setContentType("application/octet-stream;charset=UTF-8");
            outputStream = response.getOutputStream();
            //3.设置 header  Content-Disposition
            response.setHeader("Content-Disposition", "attachment; filename=" + name);
            int b = 0;
            byte[] buffer = new byte[2048];
            while (b != -1) {
                b = inputStream.read(buffer);
                if (b != -1) {
                    outputStream.write(buffer, 0, b);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(inputStream != null)
                    inputStream.close();
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 检查目录路径是否有效，若当前路径对应的目录不存在，则尝试创建目录
     * @param directoryPath 目录路径
     * @return 若目录不存在且创建失败则返回false，否则返回true
     */
    private static boolean checkDirectoryPath(String directoryPath) {
        File dir = new File(directoryPath);
        // 如果文件夹不存在则创建
        if(!dir.exists() && !dir.isDirectory()){
            log.debug("用于存放上传文件的文件夹不存在，正在尝试创建..");
            return dir.mkdirs();
        }
        return true;
    }
}
