package pl.edu.pg.eti.aui.configuration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.pg.eti.aui.delivery.entity.DeliveryMethod;
import pl.edu.pg.eti.aui.delivery.service.DeliveryMethodService;

import javax.annotation.PostConstruct;

@Component
@AllArgsConstructor
public class InitializedData {

    private final DeliveryMethodService deliveryMethodService;

    @PostConstruct
    private synchronized void init() {
//
//        DeliveryMethod dhl = DeliveryMethod.builder()
//                .carrier("DHL")
//                .rating(4.5)
//                .time(2)
//                .build();
//        DeliveryMethod dpd = DeliveryMethod.builder()
//                .carrier("DPD")
//                .rating(4.0)
//                .time(3)
//                .build();
//
//        deliveryMethodService.create(dhl);
//        deliveryMethodService.create(dpd);
    }
}
