package com.file.upload.client.file.upload.serviceImplementation;

import com.file.upload.client.file.upload.entity.Metadata;
import com.file.upload.client.file.upload.repository.MetadataRepository;
import com.file.upload.client.file.upload.service.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetadataServiceImplementation implements MetadataService {

    @Autowired
    private MetadataRepository metadataRepository;

    @Override
    public void saveMetadata(Metadata metadata) {
        metadataRepository.save(metadata);
    }
}
