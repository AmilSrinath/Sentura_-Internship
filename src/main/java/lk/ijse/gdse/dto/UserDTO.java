package lk.ijse.gdse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Amil Srinath
 */

@AllArgsConstructor
@Data
public class UserDTO {
    private String name;
    private String email;
    private String password;
}
