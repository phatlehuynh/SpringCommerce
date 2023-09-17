package com.example.demo.Controller;

import com.example.demo.Utilities.Response;
import com.example.demo.Service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/api/files")
public class FileUploadController {
    // inject storage Service here
    @Autowired
    private IStorageService storageService;
    @PostMapping("/insert")
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile file) {
        try{
            // save file to a folder => use service
            String generatedFileName = storageService.storeFile(file);
            return  Response.createResponse(HttpStatus.OK, "upload file successfully", generatedFileName);
        } catch (Exception exception) {
            return Response.createResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), null);
        }
    }

    //get image url
    @GetMapping("/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName){
        try {
            byte[] bytes = storageService.readFileContent(fileName);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        }
        catch (Exception exception) {
            return ResponseEntity.noContent().build();
        }
    }

    //Load all file
    @GetMapping("/getalluploadfile")
    public ResponseEntity<?> getAllUploadsFile() {
        try {
            List<String> urls = storageService.loadAll()
                    .map(path -> {
                        // convert file to url (send request to readDetailFile method)
                        String urlPath = MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "readDetailFile", path.getFileName().toString()).build().toUri().toString();
                        return urlPath;
                    })
                    .collect(Collectors.toList());
            return Response.createResponse(HttpStatus.OK, "List files successfully", urls);
        } catch (Exception exception) {
            return Response.createResponse(HttpStatus.BAD_REQUEST, "List files failed", new String[] {});
        }
    }
}
