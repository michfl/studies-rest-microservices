package pl.edu.pg.eti.aui.invoice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.aui.invoice.entity.Invoice;
import pl.edu.pg.eti.aui.invoice.repository.InvoiceRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class InvoiceService {

    private final InvoiceRepository repository;

    public Optional<Invoice> find(Long id) {
        return repository.findById(id);
    }

    public List<Invoice> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Invoice create(Invoice invoice) {
        return repository.save(invoice);
    }

    @Transactional
    public void update(Invoice invoice) {
        repository.save(invoice);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
