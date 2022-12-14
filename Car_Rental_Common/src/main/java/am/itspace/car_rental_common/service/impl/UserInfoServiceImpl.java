package am.itspace.car_rental_common.service.impl;

import am.itspace.car_rental_common.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    @Value("${car.rental.user.images.folder}")
    private String folderPath;

    @Override
    public byte[] getUserImage(String file) {
        try {
            InputStream inputStream = new FileInputStream(folderPath + File.separator + file);
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
