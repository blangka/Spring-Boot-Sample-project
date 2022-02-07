package com.hkmc.sample.model.dto;

import com.hkmc.sample.entity.item.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResItem {

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    public static ResItem of(Item item) {
        return new ModelMapper().map(item,ResItem.class);
    }
}
