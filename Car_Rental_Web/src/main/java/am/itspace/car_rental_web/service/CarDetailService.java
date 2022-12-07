package am.itspace.car_rental_web.service;

import am.itspace.car_rental_common.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CarDetailService {

    void save(int carId, MultipartFile[] files);

    byte[] getCarService(String fileName);

    List<Image> findAllByCar(int id);

    void delete(int id);
}
