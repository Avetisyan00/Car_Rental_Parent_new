package am.itspace.car_rental_common.service;

import am.itspace.car_rental_common.entity.UserImage;

import java.util.List;

public interface UserImageService {
    List<UserImage> findAllImagesByUserId(int id);
}
