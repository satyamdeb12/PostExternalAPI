package com.file.upload.client.file.upload.controller;

import com.file.upload.client.file.upload.entity.Metadata;
import com.file.upload.client.file.upload.service.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/client")
public class FileUploadController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
     private MetadataService metadataService;

    String baseUrl = "http://localhost:8080";

//    GET the file from external API
    @GetMapping("/download/{userId}")
    ResponseEntity<?> download(@PathVariable String userId){
        if(userId == null){
            return new ResponseEntity<>("Please Provide userId as pathVariable",HttpStatus.BAD_REQUEST);
        }
        ByteArrayResource resource = restTemplate.getForObject(baseUrl + "/profile-picture/download/"+userId, ByteArrayResource.class);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("image/png"))
                .header(HttpHeaders.CONTENT_DISPOSITION, userId)
                .body(resource);
    }

//  POST the file into external API and store metadata about the file in client database
    @PostMapping(value = "/upload", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadProfilePicture(@RequestPart("userId") String userId, @RequestPart("file") MultipartFile file){
        if(userId == null){
            return new ResponseEntity<>("Please Provide userId as pathVariable",HttpStatus.BAD_REQUEST);
        }
        if(file.isEmpty()){
            return new ResponseEntity<>("Please choose file to upload",HttpStatus.BAD_REQUEST);
        }
        try{
            LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("userId", userId);
            map.add("file", file.getResource());

            HttpEntity<LinkedMultiValueMap<String, Object>> body = new HttpEntity<>(map);
            String response = restTemplate.postForObject(baseUrl + "/profile-picture/upload/", body, String.class);


//          Storing metadata about the file
            Metadata metadata = new Metadata();
            metadata.setUserId(userId);
            metadata.setFileName(file.getOriginalFilename());
            metadata.setFileType(file.getContentType());
            metadata.setFileSize(file.getSize());

            metadataService.saveMetadata(metadata);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
