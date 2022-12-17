package am.itspace.car_rental_rest.mappper;

import am.itspace.car_rental_common.entity.User;
import am.itspace.car_rental_rest.dto.RegistrationDto;
import am.itspace.car_rental_rest.dto.UpdateUserDto;
import am.itspace.car_rental_rest.dto.UserDetailsDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User map(RegistrationDto registrationDto);

    RegistrationDto mapRegistrationDtoToEntity(User user);

    User map(UpdateUserDto updateUserDto);

    @InheritConfiguration
    void update(UpdateUserDto update, @MappingTarget User destination);

    UserDetailsDto map(User user);
}