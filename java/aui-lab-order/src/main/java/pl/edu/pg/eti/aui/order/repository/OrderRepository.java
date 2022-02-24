package pl.edu.pg.eti.aui.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pg.eti.aui.delivery.entity.DeliveryMethod;
import pl.edu.pg.eti.aui.order.entity.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByDeliveryMethod(DeliveryMethod deliveryMethod);

    Optional<Order> findByIdAndDeliveryMethod(Long id, DeliveryMethod deliveryMethod);
}
