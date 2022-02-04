package com.hkmc.sample.service;

import com.hkmc.sample.entity.item.Item;
import com.hkmc.sample.repo.jpa.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * 상품 저장
     */
    @Transactional
    public Long saveItem(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    /**
     * 모든 상품 조회
     */
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    /**
     * 상품 1개 조회
     */
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
