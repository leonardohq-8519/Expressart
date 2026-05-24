package org.project.expressart.ImagenComision.domain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Comision.domain.Comision;
import org.project.expressart.Comision.infrastructure.ComisionRepository;
import org.project.expressart.ImagenComision.dto.ImagenComisionCreateDTO;
import org.project.expressart.ImagenComision.dto.ImagenComisionResponseDTO;
import org.project.expressart.ImagenComision.dto.ImagenComisionUpdateDTO;
import org.project.expressart.ImagenComision.infrastructure.ImagenComisionRepository;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommissionPictureService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private final ImagenComisionRepository commissionPictureRepository;
    @Autowired
    private final ComisionRepository commissionRepository;
    public List<ImagenComisionResponseDTO> getByCommission(Long commissionId) throws ResourceNotFoundException {
        List<ImagenComision> commissionPicture = commissionPictureRepository.findByCommissionId(commissionId);
        if (commissionPicture.isEmpty()) {
            throw new ResourceNotFoundException("No commission pictures found for commission id: " + commissionId);
        }
        return commissionPicture.stream()
                .map(ticket -> modelMapper.map(commissionPicture, ImagenComisionResponseDTO.class))
                .collect(Collectors.toList());
    }

    public ImagenComisionResponseDTO getById(Long id) throws ResourceNotFoundException {
        ImagenComision commissionPicture = commissionPictureRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Commission picture not found"));
        return modelMapper.map(commissionPicture, ImagenComisionResponseDTO.class);
    }

    public ImagenComisionResponseDTO create(ImagenComisionCreateDTO dto){
        ImagenComision commissionPicture = new ImagenComision();
        Comision commission = commissionRepository.findById(dto.getComisionId()).orElseThrow(()-> new EntityNotFoundException("Commission not found"));
        commissionPicture.setComision(commission);
        commissionPicture.setUrl(dto.getUrl());
        commissionPicture.setOrden(dto.getOrden());
        commissionPictureRepository.save(commissionPicture);
        return modelMapper.map(commissionPicture, ImagenComisionResponseDTO.class);
    }
    public ImagenComisionResponseDTO update(Long id, ImagenComisionUpdateDTO dto)throws ResourceNotFoundException{
        ImagenComision postArchive = commissionPictureRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Commission picture not found"));;
        postArchive.setUrl(dto.getUrl());
        postArchive.setOrden(dto.getOrden());
        commissionPictureRepository.save(postArchive);
        return modelMapper.map(postArchive, ImagenComisionResponseDTO.class);
    }
    public void delete(Long id){
        if (commissionPictureRepository.existsById(id))
            commissionPictureRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Post archive with ID " + id + " doesn't exist");
    }
    public void deleteByCommission(Long commId){
        if (commissionPictureRepository.existsByCommissionId(commId))
            commissionPictureRepository.deleteByCommissionId(commId);
        else
            throw new EntityNotFoundException("Commission picture from commission ID " + commId + " doesn't exist");
    }
}
