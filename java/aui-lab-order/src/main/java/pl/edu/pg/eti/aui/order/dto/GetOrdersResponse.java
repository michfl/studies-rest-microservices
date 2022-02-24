package pl.edu.pg.eti.aui.order.dto;

import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetOrdersResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Order {

        private Long id;

        private double payment;
    }

    @Singular
    private List<Order> orders;

    public static Function<Collection<pl.edu.pg.eti.aui.order.entity.Order>, GetOrdersResponse> entityToDtoMapper() {
        return orders -> {
            GetOrdersResponseBuilder response = GetOrdersResponse.builder();
            orders.stream()
                    .map(order -> Order.builder()
                            .id(order.getId())
                            .payment(order.getPayment())
                            .build())
                    .forEach(response::order);
            return response.build();
        };
    }
}
