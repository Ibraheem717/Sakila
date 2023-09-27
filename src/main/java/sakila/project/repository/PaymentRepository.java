package sakila.project.repository;
import org.springframework.data.repository.CrudRepository;

import sakila.project.entities.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Long> {

}
