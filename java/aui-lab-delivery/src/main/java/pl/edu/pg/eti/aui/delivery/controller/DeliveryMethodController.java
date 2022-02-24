package pl.edu.pg.eti.aui.delivery.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.edu.pg.eti.aui.delivery.dto.CreateDeliveryMethodRequest;
import pl.edu.pg.eti.aui.delivery.dto.GetDeliveryMethodResponse;
import pl.edu.pg.eti.aui.delivery.dto.GetDeliveryMethodsResponse;
import pl.edu.pg.eti.aui.delivery.dto.UpdateDeliveryMethodRequest;
import pl.edu.pg.eti.aui.delivery.entity.DeliveryMethod;
import pl.edu.pg.eti.aui.delivery.service.DeliveryMethodService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@AllArgsConstructor
@RestController
@RequestMapping("api/delivery/methods")
public class DeliveryMethodController {

    private DeliveryMethodService deliveryMethodService;

    @PostMapping
    public ResponseEntity<Void> createDeliveryMethod(@RequestBody CreateDeliveryMethodRequest request,
                                                     UriComponentsBuilder builder) {
        DeliveryMethod deliveryMethod = CreateDeliveryMethodRequest
                .dtoToEntityMapper().apply(request);
        deliveryMethodService.create(deliveryMethod);
        return ResponseEntity.created(builder.pathSegment("api", "delivery", "methods", "{carrier}")
                .buildAndExpand(deliveryMethod.getCarrier()).toUri()).build();
    }

    @DeleteMapping("{carrier}")
    public ResponseEntity<Void> deleteDeliveryMethod(@PathVariable("carrier") String carrier) {
        Optional<DeliveryMethod> deliveryMethod = deliveryMethodService.find(carrier);
        if (deliveryMethod.isPresent()) {
            deliveryMethodService.delete(deliveryMethod.get().getCarrier());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{carrier}")
    public ResponseEntity<Void> updateDeliveryMethod(@RequestBody UpdateDeliveryMethodRequest request,
                                                     @PathVariable("carrier") String carrier) {
        Optional<DeliveryMethod> deliveryMethod = deliveryMethodService.find(carrier);
        if (deliveryMethod.isPresent()) {
            UpdateDeliveryMethodRequest.dtoToEntityUpdater().apply(deliveryMethod.get(), request);
            deliveryMethodService.update(deliveryMethod.get());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<GetDeliveryMethodsResponse> getOrders() {
        List<DeliveryMethod> all = deliveryMethodService.findAll();
        Function<Collection<DeliveryMethod>,
                GetDeliveryMethodsResponse> mapper = GetDeliveryMethodsResponse.entityToDtoMapper();
        GetDeliveryMethodsResponse response = mapper.apply(all);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{carrier}")
    public ResponseEntity<GetDeliveryMethodResponse> getDeliveryMethod(@PathVariable("carrier") String carrier) {
        return deliveryMethodService.find(carrier)
                .map(value -> ResponseEntity.ok(GetDeliveryMethodResponse.entityToDtoMapper().apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
