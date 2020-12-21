package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.model.person.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * デバッグ用
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/debug")
public class DebugController {

    @GetMapping("/persons")
    @ResponseBody
    public List<Person> generatePerson() {
        return Person.generateAdventurer(10);
    }
}