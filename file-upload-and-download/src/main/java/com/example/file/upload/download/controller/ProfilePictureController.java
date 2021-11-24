package com.example.file.upload.download.controller;

import com.example.file.upload.download.queue.Config;
import com.example.file.upload.download.queue.StatusMessage;
import com.example.file.upload.download.service.ProfilePictureService;
import com.example.file.upload.download.customException.CustomException;
import com.example.file.upload.download.entity.ProfilePicture;
import com.example.file.upload.download.payload.CustomExceptionHandlerResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/profile-picture")
public class ProfilePictureController {

    @Autowired
    private ProfilePictureService profilePictureService;

    @Autowired
    private RabbitTemplate template;


    @PostMapping(value = "/upload", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadProfilePicture(@RequestPart("userId") String userId, @RequestPart("file")MultipartFile file){
        try{
//          "File Upload initiated"
            StatusMessage initialMessage = new StatusMessage();
            initialMessage.setUserId(userId);
            initialMessage.setMessageTime(new Date());
            initialMessage.setStatus("File Upload initiated");
            template.convertAndSend(Config.EXCHANGE,Config.ROUTING_KEY,initialMessage);



            profilePictureService.saveProfilePicture(userId, file);

//          Store "File Upload successful"
            StatusMessage uploadSuccessfulMessage = new StatusMessage();
            uploadSuccessfulMessage.setUserId(userId);
            uploadSuccessfulMessage.setMessageTime(new Date());
            uploadSuccessfulMessage.setStatus("File Upload successful");
            template.convertAndSend(Config.EXCHANGE,Config.ROUTING_KEY,uploadSuccessfulMessage);


//          Store "File is being processed"
            StatusMessage fileProcessingInitiatedMessage = new StatusMessage();
            fileProcessingInitiatedMessage.setUserId(userId);
            fileProcessingInitiatedMessage.setMessageTime(new Date());
            fileProcessingInitiatedMessage.setStatus("File is being processed");
            template.convertAndSend(Config.EXCHANGE,Config.ROUTING_KEY,fileProcessingInitiatedMessage);


//          Store "File Processed successfully"
            StatusMessage fileProcessingCompleteMessage = new StatusMessage();
            fileProcessingCompleteMessage.setUserId(userId);
            fileProcessingCompleteMessage.setMessageTime(new Date());
            fileProcessingCompleteMessage.setStatus("File Processed successfully");
            template.convertAndSend(Config.EXCHANGE,Config.ROUTING_KEY,fileProcessingCompleteMessage);

            return new ResponseEntity<>("File Upload and Processed successfully", HttpStatus.OK);
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
