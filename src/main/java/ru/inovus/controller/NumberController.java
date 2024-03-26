package ru.inovus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.inovus.service.NumberService;


@RestController
@RequestMapping("/number")
@RequiredArgsConstructor
public class NumberController {

    private final NumberService service;

    @GetMapping("/random")
    public String getRandomNumber() {
        return service.getRandomNumber();
    }

    @GetMapping("/next")
    public String getNextNumber() {
        return service.getNextNumber();

    }

}
