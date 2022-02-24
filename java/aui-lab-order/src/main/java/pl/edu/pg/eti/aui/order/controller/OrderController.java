package pl.edu.pg.eti.aui.order.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.edu.pg.eti.aui.delivery.service.DeliveryMethodService;
import pl.edu.pg.eti.aui.invoice.event.service.InvoiceInfoFileEventService;
import pl.edu.pg.eti.aui.order.dto.CreateOrderRequest;
import pl.edu.pg.eti.aui.order.dto.GetOrderResponse;
import pl.edu.pg.eti.aui.order.dto.GetOrdersResponse;
import pl.edu.pg.eti.aui.order.dto.UpdateOrderRequest;
import pl.edu.pg.eti.aui.order.entity.Order;
import pl.edu.pg.eti.aui.order.service.OrderService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@AllArgsConstructor
@RestController
@RequestMapping("api/orders")
public class OrderController {

    private OrderService orderService;

    private DeliveryMethodService deliveryMethodService;

    private InvoiceInfoFileEventService invoiceInfoFileEventService;

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody CreateOrderRequest request, UriComponentsBuilder builder) {
        Order order = CreateOrderRequest
                .dtoToEntityMapper(name -> deliveryMethodService.find(name).orElseThrow())
                .apply(request);
        order = orderService.create(order);
        return ResponseEntity.created(builder.pathSegment("api", "orders", "{id}")
                .buildAndExpand(order.getId()).toUri()).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Long id) {
        Optional<Order> order = orderService.find(id);
        if (order.isPresent()) {
            invoiceInfoFileEventService.delete(order.get().getInvoice().getId());
            orderService.delete(order.get().getId());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateOrder(@RequestBody UpdateOrderRequest request,
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

    @GetMapping
    public ResponseEntity<GetOrdersResponse> getOrders() {
        List<Order> all = orderService.findAll();
        Function<Collection<Order>, GetOrdersResponse> mapper = GetOrdersResponse.entityToDtoMapper();
        GetOrdersResponse response = mapper.apply(all);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<GetOrderResponse> getOrder(@PathVariable("id") Long id) {
        return orderService.find(id)
                .map(value -> ResponseEntity.ok(GetOrderResponse.entityToDtoMapper().apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
