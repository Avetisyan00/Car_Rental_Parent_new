package am.itspace.car_rental_rest.endpoint;

import am.itspace.car_rental_common.entity.Image;
import am.itspace.car_rental_common.exception.EntityNotFoundException;
import am.itspace.car_rental_common.service.CarDetailService;
import am.itspace.car_rental_rest.dto.AddCarImagesDto;
import am.itspace.car_rental_rest.dto.CarImageResponse;
import am.itspace.car_rental_rest.dto.UpdateCarImageDto;
import am.itspace.car_rental_rest.mapper.CarDetailMapper;
import am.itspace.car_rental_rest.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars-detail")
@Slf4j
public class CarDetailEndpoint {

    private final CarDetailService carDetailService;
    private final CarDetailMapper carDetailMapper;
    /**
     * With this method we send List of images.
     * First of all we find a car from database. After
     * we send corresponding images from db/
     */
    @GetMapping("/{id}")
    public List<CarImageResponse> gatCarImages(@PathVariable("id") int id) {
        log.info("/cars-detail has been called");
        return carDetailMapper.carImages(carDetailService.findAllByCar(id));
    }

    @GetMapping(value = "/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getImage(@RequestParam("fileName") String fileName) {
        byte[] carImage = carDetailService.getCarService(fileName);
        return ResponseEntity.ok(carImage);
    }
    /**
     * Upload Car images
     */
    @PostMapping("/add")
    public ResponseEntity<?> addCarImages(@RequestPart("file") MultipartFile[] files,
                                          @RequestPart("request") @Valid AddCarImagesDto addImage,
                                          @AuthenticationPrincipal CurrentUser currentUser) {
        log.info("/cars-detail/add has been called");
        if (currentUser.getUser().getId() == addImage.getCar().getDealer().getId()) {
            Image image = carDetailMapper.carImage(addImage);
            carDetailService.save(image.getCar().getId(), files);
            return ResponseEntity.status(HttpStatus.CREATED).body(carDetailMapper.car(image));
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Delete car image by id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCarImage(@PathVariable("id") int id,
                                            @AuthenticationPrincipal CurrentUser currentUser) throws EntityNotFoundException {
        log.info("/cars-detail/remove has been called");
        Optional<Image> byId = carDetailService.findById(id);
        if (currentUser.getUser().getId() == byId.get().getCar().getDealer().getId()) {
            carDetailService.delete(id);
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Update car image by id
     */
    @PutMapping("/{id}")
    public ResponseEntity<UpdateCarImageDto> UpdateCarImage(@PathVariable("id") int id,
                                                            @RequestPart("file") MultipartFile file,
                                                            @RequestPart("request") @Valid UpdateCarImageDto updateCarImageDto,
                                                            @AuthenticationPrincipal CurrentUser currentUser) {
        log.info("/cars-detail/update has been called");
        if (currentUser.getUser().getId() == updateCarImageDto.getCar().getDealer().getId()) {
            Optional<Image> byId = carDetailService.findById(id);
            if (byId.isPresent()) {
                Image image = carDetailMapper.updateImage(updateCarImageDto);
                carDetailService.updateCarImage(file, image);
                return ResponseEntity.ok(updateCarImageDto);
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
