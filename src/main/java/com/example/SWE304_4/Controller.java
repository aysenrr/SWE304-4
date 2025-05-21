package com.example.SWE304_4;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/hello")
    public String sayHello(){
        return "Selamun Aleyküm Dünya";
    }
}
