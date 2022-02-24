package pl.edu.pg.eti.aui.invoice.entity;

import lombok.*;
import pl.edu.pg.eti.aui.order.entity.Order;

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
@Table(name = "invoices_order")
public class Invoice implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @OneToOne(mappedBy = "invoice")
    private Order order;
}
