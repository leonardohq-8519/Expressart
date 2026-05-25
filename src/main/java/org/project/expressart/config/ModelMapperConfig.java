package org.project.expressart.config;

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
        mapper.createTypeMap(ImagenPost.class, ImagenPostResponse.class)
                .addMappings(m -> {
                    m.map(src -> src.getPost().getId(), ImagenPostResponse::setPostId);
                });

        mapper.createTypeMap(ImagenComision.class, ImagenComisionResponse.class)
                .addMappings(m -> {
                    m.map(src -> src.getComision().getId(),
                            ImagenComisionResponse::setComisionId);
                });
    }
}
