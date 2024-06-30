package api.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PostDto {
    @JsonProperty("_id")
    String id;
    String title;
    String body;
    @JsonProperty("select1")
    String select;
    String updated;
    String uniquePost;
    String createdDate;
    AuthorDto author;
    Boolean isVisitorOwner;
}