package org.project.expressart.NotificacionCorreo.domain;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.NotificacionCorreo.infrastructure.NotificacionCorreoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailNotificationService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private final NotificacionCorreoRepository emailNotificationRepository;

}
