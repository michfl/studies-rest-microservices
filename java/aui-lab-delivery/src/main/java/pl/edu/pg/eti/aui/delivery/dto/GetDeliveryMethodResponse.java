package pl.edu.pg.eti.aui.delivery.dto;

import lombok.*;
import pl.edu.pg.eti.aui.delivery.entity.DeliveryMethod;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetDeliveryMethodResponse {

    private String carrier;

    private int time;

    private double rating;

    public static Function<DeliveryMethod, GetDeliveryMethodResponse> entityToDtoMapper() {
        return deliveryMethod -> GetDeliveryMethodResponse.builder()
                .carrier(deliveryMethod.getCarrier())
                .time(deliveryMethod.getTime())
                .rating(deliveryMethod.getRating())
                .build();
    }
}
