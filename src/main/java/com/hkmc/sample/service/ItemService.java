package com.hkmc.sample.service;

import com.hkmc.sample.entity.item.Item;
import com.hkmc.sample.model.dto.ResItem;
import com.hkmc.sample.repo.jpa.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
     * 상품 변경
     */
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQueantity) {
        Item item = itemRepository.findById(itemId).orElse(null);
        item.changeItem(name, price, stockQueantity);
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
        return itemRepository.findById(itemId).orElse(null);
    }

    public List<ResItem> findItemsMappingResItem() {
        List<Item> items = findItems();
        return items.stream().map(ResItem::of).collect(Collectors.toList());
    }
}
