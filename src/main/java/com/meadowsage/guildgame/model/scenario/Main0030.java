package com.meadowsage.guildgame.model.scenario;


import com.meadowsage.guildgame.model.World;

import java.util.Arrays;
import java.util.List;

public class Main0030 extends ScenarioBase {

    @Override
    public String getTitle() {
        return "支給品";
    }

    @Override
    public boolean canStart(World world) {
        // 条件：3日目以降
        return world.getGameDate() >= 3;
    }

    @Override
    public List<ScenarioContent> getContents() {
        Speaker me = new Speaker("", "");
        Speaker hasa = new Speaker("『秘書』ハサ", "face_1.png");
        Speaker teran = new Speaker("『町の世話役』テラン", "face_2.png");

        return Arrays.asList(
                teran.speak("ここは作成中だ")
                , me.speak("なんだ、そうなのか")
                , hasa.speak("文章考えるのって、時間がかかりますね")
        );
    }
}
