package com.example.file.upload.download.controller;

import com.example.file.upload.download.service.ProfilePictureService;
import com.example.file.upload.download.customException.CustomException;
import com.example.file.upload.download.entity.ProfilePicture;
import com.example.file.upload.download.payload.CustomExceptionHandlerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/profile-picture")
public class ProfilePictureController {

    @Autowired
    private ProfilePictureService profilePictureService;

    @PostMapping(value = "/upload", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadProfilePicture(@RequestPart("userId") String userId, @RequestPart("file")MultipartFile file){
        try{
            profilePictureService.saveProfilePicture(userId, file);
            return new ResponseEntity<>("File Upload successful", HttpStatus.OK);
        } catch (CustomException e){
            e.printStackTrace();
            CustomExceptionHandlerResponse response = new CustomExceptionHandlerResponse(e.getErrorMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e){
            e.printStackTrace();
            CustomExceptionHandlerResponse response = new CustomExceptionHandlerResponse(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{userId}")
    public ResponseEntity<?> downloadProfilePicture (@PathVariable String userId){
        try{
            if(profilePictureService.getProfilePicture(userId).isPresent()){
                ProfilePicture profilePicture = profilePictureService.getProfilePicture(userId).get();
                return ResponseEntity.ok().contentType(MediaType.parseMediaType("image/png"))
                        .header(HttpHeaders.CONTENT_DISPOSITION, profilePicture.getFileName())
                        .body(new ByteArrayResource(profilePicture.getPicture()));
            }
            else throw new CustomException("User Does not exist");
        } catch(CustomException e){
            CustomExceptionHandlerResponse response = new CustomExceptionHandlerResponse(e.getErrorMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e){
            CustomExceptionHandlerResponse response = new CustomExceptionHandlerResponse(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
