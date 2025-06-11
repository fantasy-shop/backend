package net.supercoding.backend.domain.item.repository;

import java.util.List;
import net.supercoding.backend.domain.item.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    List<ItemEntity> findByItemCategory(String itemCategory);

}
