package pl.edu.pg.eti.aui.configuration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.pg.eti.aui.invoice.entity.Invoice;
import pl.edu.pg.eti.aui.invoice.service.InvoiceService;

import javax.annotation.PostConstruct;

@Component
@AllArgsConstructor
public class InitializedData {

    private final InvoiceService invoiceService;

    @PostConstruct
    private synchronized void init() {

//        Invoice invoice1 = Invoice.builder()
//                .client("Charlie")
//                .payment(139.99)
//                .deliveryMethod(dhl)
//                .build();
//
//        invoiceService.create(invoice1);
    }
}
