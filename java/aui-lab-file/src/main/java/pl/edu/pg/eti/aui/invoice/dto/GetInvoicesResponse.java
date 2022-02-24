package pl.edu.pg.eti.aui.invoice.dto;

import lombok.*;
import pl.edu.pg.eti.aui.invoice.entity.Invoice;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetInvoicesResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Invoice {

        private Long id;

        private Long orderId;

        private String originalFileName;

        private LocalDate issueDate;
    }

    @Singular
    private List<Invoice> invoices;

    public static Function<Collection<pl.edu.pg.eti.aui.invoice.entity.Invoice>,
            GetInvoicesResponse> entityToDtoMapper() {
        return invoices -> {
            GetInvoicesResponseBuilder response = GetInvoicesResponse.builder();
            invoices.stream()
                    .map(invoice -> Invoice.builder()
                            .id(invoice.getId())
                            .orderId(invoice.getOrderId())
                            .originalFileName(invoice.getOriginalFileName())
                            .issueDate(invoice.getIssueDate())
                            .build())
                    .forEach(response::invoice);
            return response.build();
        };
    }
}
