package org.project.expressart.ArchivoPost.domain;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.ArchivoPost.infrastructure.ArchivoPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArchivePostService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private final ArchivoPostRepository archivePostRepository;

}
