package pl.edu.pg.eti.aui.delivery.event.dto;

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

    public static Function<DeliveryMethod, CreateDeliveryMethodRequest> entityToDtoMapper() {
        return entity -> CreateDeliveryMethodRequest.builder()
                .carrier(entity.getCarrier())
                .build();
    }
}
