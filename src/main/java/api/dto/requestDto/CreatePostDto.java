package api.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CreatePostDto {
    String title;
    String body;
    @JsonProperty("select1")
    String select;
    String uniquePost;
    String token;
}