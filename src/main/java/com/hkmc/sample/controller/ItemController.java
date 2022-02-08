package com.hkmc.sample.controller;

import com.hkmc.sample.entity.item.Book;
import com.hkmc.sample.entity.item.Item;
import com.hkmc.sample.model.dto.ReqBook;
import com.hkmc.sample.model.dto.ResBook;
import com.hkmc.sample.model.dto.ResItem;
import com.hkmc.sample.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new ReqBook());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(ReqBook reqBook) {

        Book book = Book.of(reqBook);
        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<ResItem> resItems = itemService.findItemsMappingResItem();
        model.addAttribute("items", resItems);
        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId);

        ResBook form = ResBook.of(item);
        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") ReqBook form) {

        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());
        return "redirect:/items";
    }
}





