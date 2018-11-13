package com.sweetitech.hrm.controller.all;

import com.sweetitech.hrm.domain.Image;
import com.sweetitech.hrm.service.ImageService;
import com.sweetitech.hrm.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/all/upload")
public class FileUploadController {

    @Value("${file.download.base}")
    private String DOWNLOAD_FOLDER;

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @Value("${file.upload.url}")
    private String UPLOADED_FOLDER;

    @Autowired
    ImageService imageService;

    // Single file upload
    @PostMapping("/file")
    public ResponseEntity<Image> uploadFile(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }

        try {
            Image i = saveUploadedFiles(Arrays.asList(file)).get(0);
            return new ResponseEntity(i, new HttpHeaders(), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (org.springframework.web.multipart.MultipartException eo) {
            System.out.println("Maximum file size exceeds");
            return new ResponseEntity("Maximum file size exceeds", HttpStatus.FORBIDDEN);
        }catch (Exception e) {
            System.out.println("just an exception");
            System.out.println(e.getStackTrace().toString());
            System.out.println(e.getLocalizedMessage());
            return new ResponseEntity("Some thing went wrong please try again later", HttpStatus.BAD_GATEWAY);
        }
    }

    // save file
    private List<Image> saveUploadedFiles(List<MultipartFile> files) throws IOException {

        List<Image> imagesToBeReturned = new ArrayList<Image>();
        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue; // next pls
            }

            String fileName = storageService.store(file);

            Resource fileo = storageService.loadAsResource(fileName);

            System.out.println(fileo.getURL().toString());

            String url = DOWNLOAD_FOLDER+"/"+fileo.getFilename().toString();
            System.out.println("demo download url when u add the base url: " + url);

            System.out.println(fileo.getFilename().toString());

            imageService.addImage(new Image(fileo.getFilename().toString()));
            imagesToBeReturned.add(imageService.findByUrl(fileo.getFilename().toString()));

        }
        return imagesToBeReturned;
    }

}
