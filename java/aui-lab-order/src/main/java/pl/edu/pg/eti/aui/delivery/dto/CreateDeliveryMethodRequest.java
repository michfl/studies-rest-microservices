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
public class CreateDeliveryMethodRequest {

    private String carrier;

    public static Function<CreateDeliveryMethodRequest, DeliveryMethod> dtoToEntityMapper() {
        return request -> DeliveryMethod.builder()
                .carrier(request.getCarrier())
                .build();
    }
}
