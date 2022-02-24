package pl.edu.pg.eti.aui.delivery.entity;

import lombok.*;
import pl.edu.pg.eti.aui.order.entity.Order;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "delivery_methods_order")
public class DeliveryMethod implements Serializable {

    @Id
    private String carrier;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "deliveryMethod", cascade = CascadeType.REMOVE)
    private List<Order> orders;
}
