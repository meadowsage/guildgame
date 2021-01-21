package com.meadowsage.guildgame.model.person;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonImage {
    private String body;
    private String face;
    private String eye;
    private String hair;
    private String cloth;

    public String getBodyFileName() {
        return "body" + body + ".png";
    }

    public String getFaceFileName() {
        return "face" + face + ".png";
    }

    public String getEyeFileName() {
        return "eye" + eye + ".png";
    }

    public String getHairName() {
        return "hair" + hair + ".png";
    }

    public String getClothFileName() {
        return "cloth" + cloth + ".png";
    }
}
