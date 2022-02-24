package pl.edu.pg.eti.aui.order.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.edu.pg.eti.aui.delivery.entity.DeliveryMethod;
import pl.edu.pg.eti.aui.delivery.service.DeliveryMethodService;
import pl.edu.pg.eti.aui.order.dto.CreateOrderRequest;
import pl.edu.pg.eti.aui.order.dto.GetOrderResponse;
import pl.edu.pg.eti.aui.order.dto.GetOrdersResponse;
import pl.edu.pg.eti.aui.order.dto.UpdateOrderRequest;
import pl.edu.pg.eti.aui.order.entity.Order;
import pl.edu.pg.eti.aui.order.service.OrderService;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("api/delivery/methods/{carrier}/orders")
public class DeliveryMethodOrderController {

    private OrderService orderService;

    private DeliveryMethodService deliveryMethodService;

    @GetMapping
    public ResponseEntity<GetOrdersResponse> getOrders(@PathVariable("carrier") String carrier) {
        Optional<DeliveryMethod> deliveryMethod = deliveryMethodService.find(carrier);
        return deliveryMethod.map(value -> ResponseEntity.ok(GetOrdersResponse.entityToDtoMapper().apply(orderService.findAll(value))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("{id}")
    public ResponseEntity<GetOrderResponse> getOrder(@PathVariable("carrier") String carrier,
                                                     @PathVariable("id") Long id) {
        Optional<DeliveryMethod> deliveryMethod = deliveryMethodService.find(carrier);
        return deliveryMethod.map(method -> orderService.find(id, method)
                .map(value -> ResponseEntity.ok(GetOrderResponse.entityToDtoMapper().apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build())).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@PathVariable("carrier") String carrier,
                                            @RequestBody CreateOrderRequest request,
                                            UriComponentsBuilder builder) {
        Optional<DeliveryMethod> deliveryMethod = deliveryMethodService.find(carrier);
        if (deliveryMethod.isPresent()) {
            Order order = CreateOrderRequest
                    .dtoToEntityMapper(deliveryMethod::get)
                    .apply(request);
            order = orderService.create(order);
            return ResponseEntity.created(builder.pathSegment("api", "delivery", "methods", "{carrier}", "orders", "{id}")
                    .buildAndExpand(carrier, order.getId()).toUri()).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("carrier") String carrier,
                                            @PathVariable("id") Long id) {
        Optional<DeliveryMethod> deliveryMethod = deliveryMethodService.find(carrier);
        if (deliveryMethod.isPresent()) {
            Optional<Order> order = orderService.find(id, deliveryMethod.get());
            if (order.isPresent()) {
                orderService.delete(order.get().getId());
                return ResponseEntity.accepted().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateOrder(@PathVariable("carrier") String carrier,
                                            @RequestBody UpdateOrderRequest request,
                                            @PathVariable("id") Long id) {
        Optional<Order> order = orderService.find(id);
        if (order.isPresent()) {
            UpdateOrderRequest.dtoToEntityUpdater()
                    .apply(order.get(), request);
            orderService.update(order.get());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
