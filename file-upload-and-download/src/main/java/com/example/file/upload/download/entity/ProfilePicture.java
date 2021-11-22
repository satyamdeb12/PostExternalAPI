package com.example.file.upload.download.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProfilePicture {

    @Id
    private String userId;

    private String fileName;

    @Lob
    private byte[] picture;
}
