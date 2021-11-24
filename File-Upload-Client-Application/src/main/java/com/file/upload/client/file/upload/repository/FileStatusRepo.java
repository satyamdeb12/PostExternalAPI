package com.file.upload.client.file.upload.repository;

import com.file.upload.client.file.upload.entity.FileStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileStatusRepo extends JpaRepository<FileStatus, String> {
}
