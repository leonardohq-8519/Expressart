package org.project.expressart.ImagenComision.domain;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.ImagenComision.infrastructure.ImagenComisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommissionPictureService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private final ImagenComisionRepository commissionPictureRepository;

}
