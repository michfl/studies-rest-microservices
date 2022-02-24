package pl.edu.pg.eti.aui.invoice.dto;

import lombok.*;
import pl.edu.pg.eti.aui.invoice.entity.Invoice;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetInvoiceInfoResponse {

    private Long id;

    private String issuer;

    private LocalDate issueDate;

    private String originalFileName;

    public static Function<Invoice, GetInvoiceInfoResponse> entityToDtoMapper() {
        return invoice -> GetInvoiceInfoResponse.builder()
                .id(invoice.getId())
                .issuer(invoice.getIssuer())
                .issueDate(invoice.getIssueDate())
                .originalFileName(invoice.getOriginalFileName())
                .build();
    }
}
