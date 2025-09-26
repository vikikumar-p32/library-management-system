package com.harekrsna.lms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public String healthCheck() {
        return "OK";
    }

    public static void main(String[] args) {
        List<Integer> ls = new ArrayList<>(
                List.of(1, 2, 3, 4, 5)
        );

        ls.forEach(el -> {
            if(el <= 5) {
                System.out.println("Lesser value exists: " + el);
            }
        });

    }
}
