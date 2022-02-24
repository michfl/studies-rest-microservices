package pl.edu.pg.eti.aui.configuration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.pg.eti.aui.delivery.entity.DeliveryMethod;
import pl.edu.pg.eti.aui.delivery.service.DeliveryMethodService;
import pl.edu.pg.eti.aui.order.entity.Order;
import pl.edu.pg.eti.aui.order.service.OrderService;

import javax.annotation.PostConstruct;

@Component
@AllArgsConstructor
public class InitializedData {

    private final OrderService orderService;

    private final DeliveryMethodService deliveryMethodService;

    @PostConstruct
    private synchronized void init() {

//        DeliveryMethod dhl = DeliveryMethod.builder()
//                .carrier("DHL")
//                .build();
//        DeliveryMethod dpd = DeliveryMethod.builder()
//                .carrier("DPD")
//                .build();
//
//        deliveryMethodService.create(dhl);
//        deliveryMethodService.create(dpd);
//
//        Order order1 = Order.builder()
//                .client("Charlie")
//                .payment(139.99)
//                .deliveryMethod(dhl)
//                .build();
//        Order order2 = Order.builder()
//                .client("John1994")
//                .payment(56.59)
//                .deliveryMethod(dhl)
//                .build();
//        Order order3 = Order.builder()
//                .client("Max")
//                .payment(1089.89)
//                .deliveryMethod(dpd)
//                .build();
//
//        orderService.create(order1);
//        orderService.create(order2);
//        orderService.create(order3);
    }
}
