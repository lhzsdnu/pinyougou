package com.pinyougou.config;

import com.pinyougou.fastdfs.FastDFSClient;
import com.pinyougou.pojo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 文件上传Controller
 */

@RestController
public class UploadController {
    private static Logger logger = LoggerFactory.getLogger(UploadController.class);


    @RequestMapping("/upload") //new annotation since 4.3
    public Result singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            new Result(false, "Please select a file to upload");
        }
        try {
            // Get the file and save it somewhere
            String path = FastDFSClient.saveFile(file);
            redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" + file.getOriginalFilename() + "'");
            redirectAttributes.addFlashAttribute("path", "file path url '" + path + "'");

            System.out.println("图片存储地址"+path);

            //拼接返回的 url 和 ip 地址，拼装成完整的 url
            return new Result(true, path);

        } catch (Exception e) {
            logger.error("upload file failed", e);
            return new Result(false, "upload file failed exception");
        }
    }

}