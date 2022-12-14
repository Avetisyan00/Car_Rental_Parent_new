package am.itspace.car_rental_common.service.impl;

import am.itspace.car_rental_common.entity.UserImage;
import am.itspace.car_rental_common.repository.ImageRepository;
import am.itspace.car_rental_common.service.UserImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements UserImageService {
    private final ImageRepository imageRepository;
    @Override
    public List<UserImage> findAllImagesByUserId(int id) {
        return imageRepository.findAllByUserId(id);

    }
}
