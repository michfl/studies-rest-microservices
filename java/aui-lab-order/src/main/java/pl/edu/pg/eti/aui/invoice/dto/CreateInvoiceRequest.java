package pl.edu.pg.eti.aui.invoice.dto;

import lombok.*;
import pl.edu.pg.eti.aui.invoice.entity.Invoice;
import pl.edu.pg.eti.aui.order.entity.Order;

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

    public static Function<CreateInvoiceRequest, Invoice> dtoToEntityMapper() {
        return request -> Invoice.builder()
                .id(request.getId())
                .build();
    }
}
