package pl.edu.pg.eti.aui.delivery.dto;

import lombok.*;
import pl.edu.pg.eti.aui.delivery.entity.DeliveryMethod;

import java.util.function.Function;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateDeliveryMethodRequest {

    private String carrier;

    private int time;

    private double rating;

    public static Function<CreateDeliveryMethodRequest, DeliveryMethod> dtoToEntityMapper() {
        return request -> DeliveryMethod.builder()
                .carrier(request.getCarrier())
                .time(request.getTime())
                .rating(request.getRating())
                .build();
    }
}
