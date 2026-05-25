package org.project.expressart.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.project.expressart.ImagenComision.domain.ImagenComision;
import org.project.expressart.ImagenComision.dto.ImagenComisionResponseDTO;
import org.project.expressart.ImagenPost.domain.ImagenPost;
import org.project.expressart.ImagenPost.dto.ImagenPostResponseDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true)           // ignora campos null del source
                .setFieldMatchingEnabled(true)       // mapea campos privados directamente
                .setFieldAccessLevel(
                        org.modelmapper.config.Configuration.AccessLevel.PRIVATE
                );

        configMapping(mapper);

        return mapper;
    }

    private void configMapping(ModelMapper mapper){
        mapper.createTypeMap(ImagenPost.class, ImagenPostResponseDTO.class)
                .addMappings(m -> {
                    m.map(src -> src.getPost().getId(), ImagenPostResponseDTO::setPostId);
                });

        mapper.createTypeMap(ImagenComision.class, ImagenComisionResponseDTO.class)
                .addMappings(m -> {
                    m.map(src -> src.getComision().getId(),
                            ImagenComisionResponseDTO::setCommissionId);
                });
    }
}
