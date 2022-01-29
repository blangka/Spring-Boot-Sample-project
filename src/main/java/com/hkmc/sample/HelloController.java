package com.hkmc.sample;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    //Model은 Data를 View에 실어서 넘길때 사용
    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data", "hello!!!");
        return "hello"; //thymeleaf View Page Open . templates 및에 hello.html 호출
    }
}
