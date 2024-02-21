package api.dto.requestDto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString

public class CreateUserDto {
    String username;
    String email;
    String password;
    String token;
}