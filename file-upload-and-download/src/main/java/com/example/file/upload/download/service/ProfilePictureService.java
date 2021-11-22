package com.example.file.upload.download.service;

import com.example.file.upload.download.entity.ProfilePicture;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ProfilePictureService {

    public ProfilePicture saveProfilePicture(String userId, MultipartFile profilePicture);

    public Optional<ProfilePicture> getProfilePicture(String userId);


}
