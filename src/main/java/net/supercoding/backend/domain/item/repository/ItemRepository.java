package net.supercoding.backend.domain.item.repository;

import java.util.List;
import net.supercoding.backend.domain.item.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    @Query("SELECT item "
            + "FROM ItemEntity item "
            + "WHERE (:category = 'all' OR item.itemCategory = :category) "
            + "AND (:itemNameKeyword IS NULL OR :itemNameKeyword = '' OR REPLACE(item.itemName, ' ','') LIKE CONCAT('%', :itemNameKeyword, '%'))")
    List<ItemEntity> findByCategoryAndKeyword(
            @Param("category") String category,
            @Param("itemNameKeyword") String itemNameKeyword);
}
