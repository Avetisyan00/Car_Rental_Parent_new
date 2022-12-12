package am.itspace.car_rental_common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "car_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime orderStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime orderEnd;
    @ManyToOne
    private User driver;
    @ManyToOne
    private User dealer;
    @ManyToOne
    private User client;
    @ManyToOne
    private Car car;
    private double amount;
}