package am.itspace.car_rental_rest.customRepo;

import am.itspace.car_rental_common.entity.Car;
import am.itspace.car_rental_common.entity.QCar;
import am.itspace.car_rental_common.repository.CarRepository;
import am.itspace.car_rental_rest.dto.CarFilterDto;
import com.querydsl.jpa.JPAQueryBase;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomCarRepo {

    @PersistenceContext
    private EntityManager entityManager;

    public final CarRepository carRepository;

    public List<Car> cars(CarFilterDto carSearchDto) {
        QCar qCar = QCar.car;
        var query = new JPAQuery(entityManager);
        JPAQueryBase from = query.from(qCar);
        if (carSearchDto.getName() != null && !carSearchDto.getName().equals("")) {
            from.where(qCar.name.contains(carSearchDto.getName()));
        }
        if (carSearchDto.getModel() != null && !carSearchDto.getModel().equals("")) {
            from.where(qCar.model.contains(carSearchDto.getModel()));
        }
        if (carSearchDto.getProductionYear() != 0) {
            from.where(qCar.productionYear.eq(carSearchDto.getProductionYear()));
        }
        from.limit(5);
        return from.fetch();
    }
}
