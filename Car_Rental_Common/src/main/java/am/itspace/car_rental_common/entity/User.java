package am.itspace.car_rental_common.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private int age;
    private String phoneNumber;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Enumerated(value = EnumType.STRING)
    private DriverLicense driverLicense;
    private double pricePerDay;
    private int rating;
    private String picUrl;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    private boolean isEnabled;
    private String verifyToken;
    private LocalDate tokenGivenDate;
}
