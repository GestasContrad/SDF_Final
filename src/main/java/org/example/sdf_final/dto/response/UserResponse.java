package org.example.sdf_final.dto.response;
import org.example.sdf_final.entity.User;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private User.Role role;
}