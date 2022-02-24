package pl.edu.pg.eti.aui.order.dto;

import lombok.*;
import pl.edu.pg.eti.aui.order.entity.Order;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetOrderResponse {

    private Long id;

    private String client;

    private double payment;

    private String deliveryMethod;

    private String invoice;

    public static Function<Order, GetOrderResponse> entityToDtoMapper() {
        return order -> GetOrderResponse.builder()
                .id(order.getId())
                .client(order.getClient())
                .payment(order.getPayment())
                .deliveryMethod(order.getDeliveryMethod().getCarrier())
                .invoice((order.getInvoice() == null) ? "none" : "available")
                .build();
    }
}
