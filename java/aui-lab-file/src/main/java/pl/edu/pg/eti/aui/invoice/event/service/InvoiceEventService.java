package pl.edu.pg.eti.aui.invoice.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.edu.pg.eti.aui.invoice.entity.Invoice;
import pl.edu.pg.eti.aui.invoice.event.dto.CreateInvoiceRequest;

@Service
public class InvoiceEventService {

    private final RestTemplate restTemplate;

    @Autowired
    public InvoiceEventService(@Value("${api.orders.url}") String baseUrl) {
        restTemplate = new RestTemplateBuilder().rootUri(baseUrl).build();
    }

    public void delete(Long id) {
        restTemplate.delete("/invoices/{id}", id);
    }

    public void create(Invoice invoice) {
        restTemplate.postForLocation("/invoices",
                CreateInvoiceRequest.entityToDtoMapper().apply(invoice));
    }
}
