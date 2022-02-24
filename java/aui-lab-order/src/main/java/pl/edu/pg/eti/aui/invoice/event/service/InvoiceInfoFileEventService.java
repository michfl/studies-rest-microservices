package pl.edu.pg.eti.aui.invoice.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.edu.pg.eti.aui.invoice.event.dto.GetInvoiceInfoResponse;

@Service
public class InvoiceInfoFileEventService {

    private final RestTemplate restTemplate;

    @Autowired
    public InvoiceInfoFileEventService(@Value("${api.invoices.url}") String baseUrl) {
        restTemplate = new RestTemplateBuilder().rootUri(baseUrl).build();
    }

    public GetInvoiceInfoResponse getInfo(Long id) {
        return restTemplate.getForObject("/invoices/" + id, GetInvoiceInfoResponse.class);
    }

    public byte[] getFile(Long id) {
        return restTemplate.getForObject("/invoices/" + id + "/download", byte[].class);
    }

    public void delete(Long id) {
        restTemplate.delete("/invoices/{id}", id);
    }
}
