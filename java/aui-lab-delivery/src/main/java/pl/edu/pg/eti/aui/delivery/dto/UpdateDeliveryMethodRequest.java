package pl.edu.pg.eti.aui.delivery.dto;

import lombok.*;
import pl.edu.pg.eti.aui.delivery.entity.DeliveryMethod;

import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateDeliveryMethodRequest {

    private int time;

    private double rating;

    public static BiFunction<DeliveryMethod, UpdateDeliveryMethodRequest, DeliveryMethod> dtoToEntityUpdater() {
        return (deliveryMethod, request) -> {
            deliveryMethod.setTime(request.getTime());
            deliveryMethod.setRating(request.getRating());
            return deliveryMethod;
        };
    }
}
