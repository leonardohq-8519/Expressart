package org.project.expressart.ImagenComision.domain;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.ArchivoPost.domain.FileService;
import org.project.expressart.Comision.domain.Comision;
import org.project.expressart.Comision.infrastructure.ComisionRepository;
import org.project.expressart.ImagenComision.dto.ImagenComisionCreateDTO;
import org.project.expressart.ImagenComision.dto.ImagenComisionResponseDTO;
import org.project.expressart.ImagenComision.dto.ImagenComisionUpdateDTO;
import org.project.expressart.ImagenComision.infrastructure.ImagenComisionRepository;
import org.project.expressart.ImagenPost.dto.ReorderImagesDTO;
import org.project.expressart.exceptions.ImageLimitException;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommissionPictureService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private final ImagenComisionRepository commissionPictureRepository;

    private final FileService fileService;
    @Autowired
    private final ComisionRepository commissionRepository;

    private static final int IMAGE_LIMIT = 6;

    public List<ImagenComisionResponseDTO> getByCommission(Long commissionId) throws ResourceNotFoundException {
        Comision commission = commissionRepository.findById(commissionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Commission not found: " + commissionId
                ));
        return commission.getImagenes().stream()
                .map(image -> modelMapper.map(image, ImagenComisionResponseDTO.class))
                .sorted(Comparator.comparingInt(ImagenComisionResponseDTO::getOrden))
                .toList();
    }

    public ImagenComisionResponseDTO getById(Long commissionId, Long imageId) throws ResourceNotFoundException {
        Comision commission = commissionRepository.findById(commissionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Commission not found: " + commissionId
                ));

        return commission.getImagenes().stream()
                .filter(image -> image.getId().equals(imageId))
                .findFirst()
                .map(image -> modelMapper.map(image, ImagenComisionResponseDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Image not found: " + imageId
                ));
    }

    public ImagenComisionResponseDTO create(Long commissionId, MultipartFile file) throws ResourceNotFoundException{
        Comision commission = commissionRepository.findById(commissionId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Commission not found: " + commissionId
                ));

        if (commission.getImagenes().size() >= IMAGE_LIMIT) {
            throw new ImageLimitException(
                    "A commission cannot have more than " + IMAGE_LIMIT + " images"
            );
        }

        String url = fileService.uploadImageCommission(file, commissionId);

        ImagenComision commissionPicture = new ImagenComision();

        commissionPicture.setComision(commission);
        commissionPicture.setUrl(url);
        commissionPicture.setOrden(commission.getImagenes().size());
        commission.getImagenes().add(commissionPicture);

        commissionRepository.save(commission);
        return modelMapper.map(commissionPicture, ImagenComisionResponseDTO.class);
    }

    @Transactional
    public List<ImagenComisionResponseDTO> reorder(Long commissionId, ReorderImagesDTO request){
        Comision commission = commissionRepository.findById(commissionId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Commission not found: " + commissionId
                ));

        if (request.orderIds().size() != commission.getImagenes().size()) {
            throw new IllegalArgumentException(
                    "Ids quantity does not equal image quantity"
            );
        }

        commission.getImagenes().forEach(image -> image.setOrden(request.orderIds().indexOf(image.getId())));

        commissionRepository.save(commission);

        return commission.getImagenes().stream()
                .map(image -> modelMapper.map(image, ImagenComisionResponseDTO.class))
                .sorted(Comparator.comparingInt(ImagenComisionResponseDTO::getOrden))
                .toList();
    }


    public ImagenComisionResponseDTO update(Long id, ImagenComisionUpdateDTO dto)throws ResourceNotFoundException{
        ImagenComision postArchive = commissionPictureRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Commission picture not found"));;
        postArchive.setUrl(dto.getUrl());
        postArchive.setOrden(dto.getOrden());
        commissionPictureRepository.save(postArchive);
        return modelMapper.map(postArchive, ImagenComisionResponseDTO.class);
    }
    public void delete(Long commissionId, Long imageId) throws ResourceNotFoundException{
        Comision commission = commissionRepository.findById(commissionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Commission not found: " + commissionId
                ));

        ImagenComision imagenComision =  commission.getImagenes().stream()
                .filter(image -> image.getId().equals(imageId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Image not found: " + imageId
                ));

        fileService.delete(imagenComision.getUrl());

        commission.getImagenes().remove(imagenComision);
        reorder(commission.getImagenes());
        commissionRepository.save(commission);
    }
    public void deleteByCommission(Long commissionId) throws ResourceNotFoundException{
        Comision commission = commissionRepository.findById(commissionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Commission not found: " + commissionId
                ));
        if(commission.getImagenes().isEmpty()){
            throw new ResourceNotFoundException(
                    "Commission does not have images: " + commissionId
            );
        }

        commission.getImagenes().forEach(image -> fileService.delete(image.getUrl()));

        commission.getImagenes().clear();
        commissionRepository.save(commission);
    }

    private void reorder(List<ImagenComision> images){
        for (int i = 0; i < images.size(); i++){
            images.get(i).setOrden(i);
        }
    }
}
