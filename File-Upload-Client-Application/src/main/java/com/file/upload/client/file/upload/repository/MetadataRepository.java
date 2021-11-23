package com.file.upload.client.file.upload.repository;

import com.file.upload.client.file.upload.entity.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetadataRepository extends JpaRepository<Metadata, String> {
}
