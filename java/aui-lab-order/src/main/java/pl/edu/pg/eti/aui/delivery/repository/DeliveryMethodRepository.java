package pl.edu.pg.eti.aui.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pg.eti.aui.delivery.entity.DeliveryMethod;

@Repository
public interface DeliveryMethodRepository extends JpaRepository<DeliveryMethod, String> {
}
