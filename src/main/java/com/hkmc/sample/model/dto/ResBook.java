package com.hkmc.sample.model.dto;

import com.hkmc.sample.entity.item.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResBook extends ResItem{

    private String author;
    private String isbn;

    public static ResBook of(Book book) {
        return new ModelMapper().map(book,ResBook.class);
    }
}
