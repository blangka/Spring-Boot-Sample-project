package com.hkmc.sample.repo.jpa;

import com.hkmc.sample.entity.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
