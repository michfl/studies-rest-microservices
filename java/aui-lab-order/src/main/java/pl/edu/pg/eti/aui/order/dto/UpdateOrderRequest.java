package pl.edu.pg.eti.aui.order.dto;

import lombok.*;
import pl.edu.pg.eti.aui.order.entity.Order;

import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateOrderRequest {

    private String client;

    private double payment;

    public static BiFunction<Order, UpdateOrderRequest, Order> dtoToEntityUpdater() {
        return (order, request) -> {
            order.setClient(request.getClient());
            order.setPayment(request.getPayment());
            return order;
        };
    }
}
