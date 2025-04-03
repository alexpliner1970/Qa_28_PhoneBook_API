package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString

public class AuthRequestDto {
    //    "username"*	"string"
//    "password"*	"string"

    private String username;
    private String password;
}
