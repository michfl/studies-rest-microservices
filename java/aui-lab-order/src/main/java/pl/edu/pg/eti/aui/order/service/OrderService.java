package pl.edu.pg.eti.aui.order.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.aui.delivery.entity.DeliveryMethod;
import pl.edu.pg.eti.aui.order.entity.Order;
import pl.edu.pg.eti.aui.order.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository repository;

    public Optional<Order> find(Long id) {
        return repository.findById(id);
    }

    public Optional<Order> find(Long id, DeliveryMethod deliveryMethod) {
        return repository.findByIdAndDeliveryMethod(id, deliveryMethod);
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    public List<Order> findAll(DeliveryMethod deliveryMethod) {
        return repository.findAllByDeliveryMethod(deliveryMethod);
    }

    @Transactional
    public Order create(Order order) {
        return repository.save(order);
    }

    @Transactional
    public void update(Order order) {
        repository.save(order);
    }

    @Transactional
    public void delete(Long order) {
        repository.deleteById(order);
    }
}
