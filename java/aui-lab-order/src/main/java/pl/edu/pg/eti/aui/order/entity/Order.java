package pl.edu.pg.eti.aui.order.entity;

import lombok.*;
import pl.edu.pg.eti.aui.delivery.entity.DeliveryMethod;
import pl.edu.pg.eti.aui.invoice.entity.Invoice;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String client;

    @Column(nullable = false)
    private double payment;

    @ManyToOne
    @JoinColumn(name = "delivery_method")
    private DeliveryMethod deliveryMethod;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    private Invoice invoice;
}
