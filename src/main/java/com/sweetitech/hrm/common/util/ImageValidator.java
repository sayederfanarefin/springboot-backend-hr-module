package com.sweetitech.hrm.common.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageValidator {

    public static boolean isImageValid(MultipartFile multipartFile) {
        try {
            Image image = ImageIO.read(ImageValidator.convertToFile(multipartFile));
            if (image != null)
                return true;
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    private static File convertToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.flush();
        fos.close();
        return file;
    }
}