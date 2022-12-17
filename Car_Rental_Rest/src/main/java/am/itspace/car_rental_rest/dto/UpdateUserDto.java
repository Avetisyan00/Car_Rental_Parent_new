package am.itspace.car_rental_rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserDto {

    private String name;
    private String surname;
    private String email;
    private String password;
    private Integer age;
    private String phoneNumber;
    private String role;
    private String driverLicense;
    private Double pricePerDay;
    private Integer rating;
    private Boolean isEnabled;
}
