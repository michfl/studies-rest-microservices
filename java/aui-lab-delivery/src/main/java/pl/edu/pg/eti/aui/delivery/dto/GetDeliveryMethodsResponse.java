package pl.edu.pg.eti.aui.delivery.dto;

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
public class GetDeliveryMethodsResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class DeliveryMethod {

        private String carrier;
    }

    @Singular
    private List<DeliveryMethod> deliveryMethods;

    public static Function<Collection<pl.edu.pg.eti.aui.delivery.entity.DeliveryMethod>,
            GetDeliveryMethodsResponse> entityToDtoMapper() {
        return deliveryMethods -> {
            GetDeliveryMethodsResponseBuilder response = GetDeliveryMethodsResponse.builder();
            deliveryMethods.stream()
                    .map(deliveryMethod -> DeliveryMethod.builder()
                            .carrier(deliveryMethod.getCarrier())
                            .build())
                    .forEach(response::deliveryMethod);
            return response.build();
        };
    }
}
