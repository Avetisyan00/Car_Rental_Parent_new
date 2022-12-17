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
    private int age;
    private String phoneNumber;
    private String role;
    private String driverLicense;
    private double pricePerDay;
    private int rating;
    private boolean isEnabled;
}
