package org.project.expressart.ArchivoPost.domain;

import org.modelmapper.ModelMapper;
import org.project.expressart.ArchivoPost.infrastructure.ArchivoPostRepository;
import org.springframework.stereotype.Service;

@Service
public class ArchivePostService {

    private final ModelMapper modelMapper;
    private final ArchivoPostRepository archivePostRepository;

    public ArchivePostService(ModelMapper modelMapper, ArchivoPostRepository archivePostRepository) {
        this.modelMapper = modelMapper;
        this.archivePostRepository = archivePostRepository;
    }
}