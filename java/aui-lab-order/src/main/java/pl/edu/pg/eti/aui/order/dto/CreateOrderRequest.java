package pl.edu.pg.eti.aui.order.dto;

import lombok.*;
import pl.edu.pg.eti.aui.delivery.entity.DeliveryMethod;
import pl.edu.pg.eti.aui.order.entity.Order;

import java.util.function.Function;
import java.util.function.Supplier;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateOrderRequest {

    private String client;

    private double payment;

    private String deliveryMethod;

    public static Function<CreateOrderRequest, Order> dtoToEntityMapper(
            Function<String, DeliveryMethod> deliveryMethodFunction) {
        return request -> Order.builder()
                .client(request.getClient())
                .payment(request.getPayment())
                .deliveryMethod(deliveryMethodFunction.apply(request.getDeliveryMethod()))
                .build();
    }

    public static Function<CreateOrderRequest, Order> dtoToEntityMapper(
            Supplier<DeliveryMethod> deliveryMethodSupplier) {
        return request -> Order.builder()
                .client(request.getClient())
                .payment(request.getPayment())
                .deliveryMethod(deliveryMethodSupplier.get())
                .build();
    }
}
