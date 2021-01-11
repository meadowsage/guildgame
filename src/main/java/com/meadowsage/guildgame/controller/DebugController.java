package com.meadowsage.guildgame.controller;

import com.meadowsage.guildgame.model.person.Applicant;
import com.meadowsage.guildgame.model.system.Dice;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Applicant> generatePerson() {
        return Applicant.generate(10, new Dice());
    }
}