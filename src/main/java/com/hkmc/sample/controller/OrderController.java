package com.hkmc.sample.controller;

import com.hkmc.sample.model.dto.ResItem;
import com.hkmc.sample.model.dto.ResMember;
import com.hkmc.sample.model.dto.ResOrder;
import com.hkmc.sample.repo.jpa.OrderSearch;
import com.hkmc.sample.service.ItemService;
import com.hkmc.sample.service.MemberService;
import com.hkmc.sample.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model) {

        List<ResMember> resMembers = memberService.findMembersMappingResMember();
        List<ResItem> resItems = itemService.findItemsMappingResItem();

        model.addAttribute("members", resMembers);
        model.addAttribute("items", resItems);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {

        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<ResOrder> resOrder = orderService.findOrdersMappingResOrder(orderSearch);
        model.addAttribute("orders", resOrder);
        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
