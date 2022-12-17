package am.itspace.car_rental_rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsDto {
    private String name;
    private String surname;
    private String email;
    private String age;
    private String phoneNumber;
    private String picUrl;
    private String role;
}