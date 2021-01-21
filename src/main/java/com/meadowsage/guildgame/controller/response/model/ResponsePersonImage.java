package com.meadowsage.guildgame.controller.response.model;


import com.meadowsage.guildgame.model.person.Person;
import lombok.Getter;

@Getter
public class ResponsePersonImage {
    String body;
    String face;
    String eye;
    String hair;
    String cloth;

    public ResponsePersonImage(Person person) {
        this.body = person.getPersonImage().getBodyFileName();
        this.face = person.getPersonImage().getFaceFileName();
        this.eye = person.getPersonImage().getEyeFileName();
        this.hair = person.getPersonImage().getHairName();
        this.cloth = person.getPersonImage().getClothFileName();
    }
}