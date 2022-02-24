package pl.edu.pg.eti.aui.delivery.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.edu.pg.eti.aui.delivery.entity.DeliveryMethod;
import pl.edu.pg.eti.aui.delivery.event.dto.CreateDeliveryMethodRequest;

@Service
public class DeliveryMethodEventService {

    private final RestTemplate restTemplate;

    @Autowired
    public DeliveryMethodEventService(@Value("${api.orders.url}") String baseUrl) {
        restTemplate = new RestTemplateBuilder().rootUri(baseUrl).build();
    }

    public void delete(String deliveryMethod) {
        restTemplate.delete("/delivery/methods/{carrier}", deliveryMethod);
    }

    public void create(DeliveryMethod deliveryMethod) {
        restTemplate.postForLocation("/delivery/methods",
                CreateDeliveryMethodRequest.entityToDtoMapper().apply(deliveryMethod));
    }
}
