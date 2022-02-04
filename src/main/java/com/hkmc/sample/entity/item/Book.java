package com.hkmc.sample.entity.item;

import com.hkmc.sample.model.dto.ReqBook;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@DiscriminatorValue("B")
public class Book extends Item{

    private String author;
    private String isbn;

    public static Book of(ReqBook reqBook) {
        return Book.builder()
                .name(reqBook.getName())
                .price(reqBook.getPrice())
                .stockQuantity(reqBook.getStockQuantity())
                .author(reqBook.getAuthor())
                .isbn(reqBook.getIsbn())
                .build();
    }
}
