package com.file.upload.client.file.upload.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.cglib.core.GeneratorStrategy;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Metadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String uniqueId;

    @Column(updatable = false)
    private String userId;

    private String fileName;

    private String fileType;

    private Long fileSize;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MMM-yyyy HH:mm:ss", timezone = "Asia/Kolkata")
    @Column(updatable = false)
    private Date fileUploadedOn;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MMM-yyyy HH:mm:ss", timezone = "Asia/Kolkata")
    private Date lastUpdatedOn;

}
