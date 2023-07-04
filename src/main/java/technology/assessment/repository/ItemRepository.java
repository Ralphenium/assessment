package technology.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import technology.assessment.model.entity.Item;

import java.math.BigDecimal;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByNameAndPrice(String name, BigDecimal price);
}
