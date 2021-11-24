package com.file.upload.client.file.upload.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileStatus {
    @Id
    private String messageId;

    private String userId;
    private String statusMessage;
    private Date messageTimestamp;
}
