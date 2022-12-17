package am.itspace.car_rental_common;

import am.itspace.car_rental_common.service.impl.CarServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {CarServiceImpl.class})
public class CarRentalCommonApplicationTests {
    @Test
    void contextLoads() {
    }
}
