package pl.edu.pg.eti.aui.delivery.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "delivery_methods")
public class DeliveryMethod implements Serializable {

    @Id
    private String carrier;

    @Column(name = "delivery_time", nullable = false)
    private int time;

    private double rating;
}
