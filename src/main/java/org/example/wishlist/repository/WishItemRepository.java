package org.example.wishlist.repository;

import org.example.wishlist.model.WishItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishItemRepository extends JpaRepository<WishItem, Long> {
    int countAllByReserved(boolean reserved);
    List<WishItem> findAllByUserAndReserved(String user, boolean reserved);
    List<WishItem> findAllByReserved(boolean reserved);
    List<WishItem> findAllByUser(String user);
}
