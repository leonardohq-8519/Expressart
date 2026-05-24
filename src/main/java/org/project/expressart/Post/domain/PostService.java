package org.project.expressart.Post.domain;
import lombok.RequiredArgsConstructor;
import org.project.expressart.Post.dto.PostRequestDTO;
import org.project.expressart.Post.dto.PostResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService{
    public List<PostResponseDTO> findAll(){
    }
    public PostResponseDTO  findById (Long id){
    }
    public PostResponseDTO findByPortafolioId (Long portafolioId){
    }
    public PostResponseDTO findByPortafolioIdAndEsPublico (Long perfilArtistaId, Boolean publicStatus){
    }
    public PostResponseDTO findByCategoriaId (Long categoriaId){
    }
    public PostResponseDTO findByTagId (Long tagId){
    }

    public PostResponseDTO create(PostRequestDTO request){
    }
    public PostResponseDTO  update (Long id, PostRequestDTO request){
    }
    public void delete (Long id){
    }
}