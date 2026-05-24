package org.project.expressart.Notificacion.domain;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Notificacion.infrastructure.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private final NotificacionRepository notificationRepository;
}
