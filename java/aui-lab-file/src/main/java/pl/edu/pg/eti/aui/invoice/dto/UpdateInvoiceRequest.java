package pl.edu.pg.eti.aui.invoice.dto;

import lombok.*;
import pl.edu.pg.eti.aui.invoice.entity.Invoice;

import java.time.LocalDate;
import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateInvoiceRequest {

    private String issuer;

    private LocalDate issueDate;

    public static BiFunction<Invoice, UpdateInvoiceRequest, Invoice> dtoToEntityUpdater() {
        return (invoice, request) -> {
            invoice.setIssuer(request.getIssuer());
            invoice.setIssueDate(request.getIssueDate());
            return invoice;
        };
    }
}
