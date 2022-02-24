package pl.edu.pg.eti.aui.invoice.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pg.eti.aui.invoice.entity.Invoice;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CreateInvoiceRequest {

    private Long orderId;

    private String issuer;

    private String issueDate;

    private MultipartFile file;

    public static Function<CreateInvoiceRequest, Invoice> dtoToEntityMapper() {
        return request -> Invoice.builder()
                .orderId(request.getOrderId())
                .issuer(request.getIssuer())
                .issueDate(LocalDate.parse(request.getIssueDate()))
                .originalFileName(request.getFile().getOriginalFilename())
                .build();
    }
}
