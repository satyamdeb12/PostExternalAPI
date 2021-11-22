package com.example.file.upload.download.repository;

import com.example.file.upload.download.entity.ProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, String> {
}
