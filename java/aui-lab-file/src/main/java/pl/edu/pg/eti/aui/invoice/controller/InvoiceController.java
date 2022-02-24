package pl.edu.pg.eti.aui.invoice.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.edu.pg.eti.aui.invoice.dto.CreateInvoiceRequest;
import pl.edu.pg.eti.aui.invoice.dto.GetInvoiceInfoResponse;
import pl.edu.pg.eti.aui.invoice.dto.GetInvoicesResponse;
import pl.edu.pg.eti.aui.invoice.dto.UpdateInvoiceRequest;
import pl.edu.pg.eti.aui.invoice.entity.Invoice;
import pl.edu.pg.eti.aui.invoice.event.service.InvoiceEventService;
import pl.edu.pg.eti.aui.invoice.service.InvoiceService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@AllArgsConstructor
@RestController
@RequestMapping("api/invoices")
public class InvoiceController {

    private InvoiceService invoiceService;

    private InvoiceEventService eventService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createInvoice(@ModelAttribute CreateInvoiceRequest request, UriComponentsBuilder builder,
                                              @Value("${api.file-upload-dir}") String dir) {
        Invoice invoice = CreateInvoiceRequest
                .dtoToEntityMapper()
                .apply(request);

        invoice = invoiceService.create(invoice);

        invoice.setFilePath(dir + "/" + invoice.getId() + "." + invoice.getOriginalFileName().split("\\.")[1]);
        File file = new File(invoice.getFilePath());

        invoiceService.update(invoice);
        eventService.create(invoice);

        try {
            request.getFile().transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            invoiceService.delete(invoice.getId());
            eventService.delete(invoice.getId());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.created(builder.pathSegment("api", "invoices", "{id}")
                .buildAndExpand(invoice.getId()).toUri()).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable("id") Long id) {
        Optional<Invoice> invoice = invoiceService.find(id);
        if (invoice.isPresent()) {

            File file = new File(invoice.get().getFilePath());
            if (!file.delete()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            invoiceService.delete(invoice.get().getId());
            eventService.delete(invoice.get().getId());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateInvoice(@RequestBody UpdateInvoiceRequest request,
                                              @PathVariable("id") Long id) {
        Optional<Invoice> invoice = invoiceService.find(id);
        if (invoice.isPresent()) {
            UpdateInvoiceRequest.dtoToEntityUpdater()
                    .apply(invoice.get(), request);
            invoiceService.update(invoice.get());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<GetInvoicesResponse> getInvoices() {
        List<Invoice> all = invoiceService.findAll();
        Function<Collection<Invoice>, GetInvoicesResponse> mapper = GetInvoicesResponse.entityToDtoMapper();
        GetInvoicesResponse response = mapper.apply(all);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<GetInvoiceInfoResponse> getInvoiceInfo(@PathVariable("id") Long id) {
        return invoiceService.find(id)
                .map(value -> ResponseEntity.ok(GetInvoiceInfoResponse.entityToDtoMapper().apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "{id}/download")
    public ResponseEntity<Resource> getInvoice(@PathVariable("id") Long id) {
        Optional<Invoice> invoice = invoiceService.find(id);
        if (invoice.isPresent()) {
            File file = new File(invoice.get().getFilePath());

            HttpHeaders header = new HttpHeaders();
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="
                    + invoice.get().getOriginalFileName());
            header.add("Cache-Control", "no-cache, no-store, must-revalidate");
            header.add("Pragma", "no-cache");
            header.add("Expires", "0");

            Path path = Paths.get(file.getAbsolutePath());
            ByteArrayResource resource;
            try {
                resource = new ByteArrayResource(Files.readAllBytes(path));
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            return ResponseEntity.ok()
                    .headers(header)
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
