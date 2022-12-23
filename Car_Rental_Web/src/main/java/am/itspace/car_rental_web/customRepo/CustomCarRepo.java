package am.itspace.car_rental_web.customRepo;

import am.itspace.car_rental_common.entity.QCar;
import am.itspace.car_rental_common.repository.CarRepository;
import am.itspace.car_rental_common.entity.Car;
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
    /**
     * This method get all cars.
     * The method allows to search car
     * by name,model...
     */
    public List<Car> cars(String name, String model, Integer productionYear) {
        QCar qCar = QCar.car;
        var query = new JPAQuery(entityManager);
        JPAQueryBase from = query.from(qCar);
        if (name != null && !name.equals("")) {
            from.where(qCar.name.contains(name));
        }
        if (model != null && !model.equals("")) {
            from.where(qCar.model.contains(model));
        }
        if (productionYear != null && productionYear !=0) {
            from.where(qCar.productionYear.eq(productionYear));
        }
        from.limit(5);
        return from.fetch();
    }
}
