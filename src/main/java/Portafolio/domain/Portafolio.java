package Portafolio.domain;


import Post.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class Portafolio{
    List<Post> posts;

}