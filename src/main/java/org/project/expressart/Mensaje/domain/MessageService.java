package org.project.expressart.Mensaje.domain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Chat.domain.Chat;
import org.project.expressart.Chat.infrastructure.ChatRepository;
import org.project.expressart.Mensaje.dto.MessageRequestDTO;
import org.project.expressart.Mensaje.dto.MessageResponseDTO;
import org.project.expressart.Mensaje.infrastructure.MensajeRepository;
import org.project.expressart.ResenaArtista.dto.ArtistReviewResponseDTO;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private final MensajeRepository messageRepository;
    @Autowired
    private final ChatRepository chatRepository;
    @Autowired
    private final UsuarioRepository userRepository;
    public List<MessageResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return messageRepository.findAllBy(pageable);
    }
    public MessageResponseDTO  findById (Long id)throws ResourceNotFoundException {
        Mensaje message = messageRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Message not found"));
        return modelMapper.map(message, MessageResponseDTO.class);
    }
    public List<MessageResponseDTO> findByChatId (Long chatId)throws ResourceNotFoundException{
        List<Mensaje> message = messageRepository.findByChatId(chatId);
        if (message.isEmpty()) {
            throw new ResourceNotFoundException("No messages found for chat id: " + chatId);
        }
        return message.stream()
                .map(ticket -> modelMapper.map(message, MessageResponseDTO.class))
                .collect(Collectors.toList());
    }
    public MessageResponseDTO create(MessageRequestDTO request){
        Mensaje message = new Mensaje();
        Chat chat = chatRepository.findById(request.getChatId()).orElseThrow(()-> new EntityNotFoundException("Chat not found"));
        message.setChat(chat);
        Usuario remitent = userRepository.findById(request.getRemitenteId()).orElseThrow(()-> new EntityNotFoundException("Remitent not found"));
        message.setRemitente(remitent);
        message.setTexto(request.getTexto());
        messageRepository.save(message);
        return modelMapper.map(message, MessageResponseDTO.class);
    }
    public MessageResponseDTO  markAsRead (Long id) throws ResourceNotFoundException {
        Mensaje updatedMessage = messageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Message not found"));
        updatedMessage.setLeido(true);
        messageRepository.save(updatedMessage);
        return modelMapper.map(updatedMessage, MessageResponseDTO.class);
    }
    public void delete (Long id){
        if (messageRepository.existsById(id))
            messageRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Message with ID " + id + " doesn't exist");
    }

}