package pl.edu.pg.eti.aui.order.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.aui.invoice.event.dto.GetInvoiceInfoResponse;
import pl.edu.pg.eti.aui.invoice.event.service.InvoiceInfoFileEventService;
import pl.edu.pg.eti.aui.order.entity.Order;
import pl.edu.pg.eti.aui.order.service.OrderService;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("api/orders/{id}/invoice")
public class OrderInvoiceController {

    private OrderService orderService;

    private InvoiceInfoFileEventService infoFileService;

    @GetMapping
    public ResponseEntity<GetInvoiceInfoResponse> getOrderInvoiceInfo(@PathVariable("id") Long id) {
        Optional<Order> order = orderService.find(id);
        if (order.isPresent()) {
            if (order.get().getInvoice() != null) {
                return ResponseEntity.ok(infoFileService.getInfo(order.get().getInvoice().getId()));
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> getOrderInvoiceFile(@PathVariable("id") Long id) {
        Optional<Order> order = orderService.find(id);
        if (order.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (order.get().getInvoice() == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="
                + infoFileService.getInfo(order.get().getInvoice().getId()).getOriginalFileName());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        ByteArrayResource resource = new ByteArrayResource(infoFileService.getFile(order.get().getInvoice().getId()));

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
