package pl.edu.pg.eti.aui.invoice.event.dto;

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
}
