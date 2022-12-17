package am.itspace.car_rental_common.service;

import am.itspace.car_rental_common.entity.Image;
import am.itspace.car_rental_common.exception.EntityNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CarDetailService {

    void save(int carId, MultipartFile[] files);

    byte[] getCarService(String fileName);

    List<Image> findAllByCar(int id);

    void delete(int id) throws EntityNotFoundException;

    Optional<Image> findById(int id);

    void updateCarImage(MultipartFile file, Image image);
}
