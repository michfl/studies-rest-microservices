package pl.edu.pg.eti.aui.invoice.event.dto;

import lombok.*;
import pl.edu.pg.eti.aui.invoice.entity.Invoice;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateInvoiceRequest {

    private Long id;

    private Long orderId;

    public static Function<Invoice, CreateInvoiceRequest> entityToDtoMapper() {
        return entity -> CreateInvoiceRequest.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .build();
    }
}
