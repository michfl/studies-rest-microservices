package pl.edu.pg.eti.aui.delivery.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.edu.pg.eti.aui.delivery.dto.CreateDeliveryMethodRequest;
import pl.edu.pg.eti.aui.delivery.entity.DeliveryMethod;
import pl.edu.pg.eti.aui.delivery.service.DeliveryMethodService;
import pl.edu.pg.eti.aui.invoice.event.service.InvoiceInfoFileEventService;
import pl.edu.pg.eti.aui.order.service.OrderService;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("api/delivery/methods")
public class DeliveryMethodController {

    private DeliveryMethodService deliveryMethodService;

    private InvoiceInfoFileEventService invoiceInfoFileEventService;

    private OrderService orderService;

    @PostMapping("")
    public ResponseEntity<Void> createDeliveryMethod(@RequestBody CreateDeliveryMethodRequest request,
                                                     UriComponentsBuilder builder) {
        DeliveryMethod deliveryMethod = CreateDeliveryMethodRequest
                .dtoToEntityMapper().apply(request);
        deliveryMethod = deliveryMethodService.create(deliveryMethod);
        return ResponseEntity.created(builder.pathSegment("api", "delivery", "methods", "{carrier}")
                .buildAndExpand(deliveryMethod.getCarrier()).toUri()).build();
    }

    @DeleteMapping("{carrier}")
    public ResponseEntity<Void> deleteDeliveryMethod(@PathVariable("carrier") String carrier) {
        Optional<DeliveryMethod> deliveryMethod = deliveryMethodService.find(carrier);
        if (deliveryMethod.isPresent()) {
            orderService.findAll(deliveryMethod.get()).forEach(order -> {
                if (order.getInvoice() != null) {
                    invoiceInfoFileEventService.delete(order.getInvoice().getId());
                }
            });
            deliveryMethodService.delete(deliveryMethod.get().getCarrier());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
