package com.example.file.upload.download.seviceImplementation;

import com.example.file.upload.download.customException.CustomException;
import com.example.file.upload.download.entity.ProfilePicture;
import com.example.file.upload.download.repository.ProfilePictureRepository;
import com.example.file.upload.download.service.ProfilePictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ProfilePictureServiceImplementation implements ProfilePictureService {

    @Autowired
    private ProfilePictureRepository profilePictureRepository;

    public ProfilePicture saveProfilePicture(String userId, MultipartFile file){
        String fileName = userId + "_Profile_Picture";

        try{
            ProfilePicture profilePicture = new ProfilePicture(userId, fileName, file.getBytes());
            return profilePictureRepository.save(profilePicture);
        }catch(IOException e){
            e.printStackTrace();
            throw new CustomException(e.getMessage());
        }catch(Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    public Optional<ProfilePicture> getProfilePicture(String userId){
        try{
            Optional<ProfilePicture> profilePicture = profilePictureRepository.findById(userId);
            return profilePicture;
        } catch(Exception e){
            e.printStackTrace();
            throw new CustomException(e.getMessage());
        }
    }
}
