package pl.edu.pg.eti.aui.invoice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.edu.pg.eti.aui.invoice.dto.CreateInvoiceRequest;
import pl.edu.pg.eti.aui.invoice.entity.Invoice;
import pl.edu.pg.eti.aui.invoice.service.InvoiceService;
import pl.edu.pg.eti.aui.order.entity.Order;
import pl.edu.pg.eti.aui.order.service.OrderService;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("api/invoices")
public class InvoiceController {

    private InvoiceService invoiceService;

    private OrderService orderService;

    @PostMapping("")
    public ResponseEntity<Void> createInvoice(@RequestBody CreateInvoiceRequest request,
                                              UriComponentsBuilder builder) {
        Optional<Order> order = orderService.find(request.getOrderId());
        if (order.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Invoice invoice = CreateInvoiceRequest
                .dtoToEntityMapper()
                .apply(request);
        order.get().setInvoice(invoice);
        invoiceService.create(invoice);
        orderService.update(order.get());
        return ResponseEntity.created(builder.pathSegment("api", "invoices", "{id}")
                .buildAndExpand(invoice.getId()).toUri()).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable("id") Long id) {
        Optional<Invoice> invoice = invoiceService.find(id);
        if (invoice.isPresent()) {
            Order order = invoice.get().getOrder();
            order.setInvoice(null);
            invoiceService.delete(invoice.get().getId());
            orderService.update(order);
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
