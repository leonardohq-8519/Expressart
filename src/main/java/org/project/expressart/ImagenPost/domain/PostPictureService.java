package org.project.expressart.ImagenPost.domain;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.ImagenPost.infrastructure.ImagenPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostPictureService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private final ImagenPostRepository postPictureRepository;

}
