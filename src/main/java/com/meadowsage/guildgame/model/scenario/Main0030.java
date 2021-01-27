package com.meadowsage.guildgame.model.scenario;


import com.meadowsage.guildgame.model.Place;
import com.meadowsage.guildgame.model.system.GameLogger;
import com.meadowsage.guildgame.model.world.GameWorld;

import java.util.Arrays;
import java.util.List;

public class Main0030 extends Scenario {

    public Main0030(String id) {
        super(id);
    }

    @Override
    public String getTitle() {
        return "茸の森";
    }

    @Override
    public boolean canStart(GameWorld world) {
        // 条件：3日目以降、名声30以上
        return world.getGameDate() >= 3 && world.getGuild().getReputation() >= 15;
    }

    @Override
    public void afterProcess(GameWorld world, GameLogger gameLogger) {
        world.getPlaces().add(Place.MUSHROOM_FOREST);
        gameLogger.important("【茸の森】でクエストが発生するようになりました。");
    }

    @Override
    public List<ScenarioScript> getScripts() {
        Speaker me = new Speaker("", "");
        Speaker eris = new Speaker("『秘書』エリス", "face_1.png");
        Speaker teran = new Speaker("『町の世話役』テラン", "face_2.png");
        Speaker dolk = new Speaker("???", "face_3.png");

        return Arrays.asList(
                me.speak("クエスト帰りのテランさんと雑談をしていると、１人の男性が扉を開けて入ってきた。")
                , dolk.speak("どうも、失礼するよ。")
                , teran.speak("おお、町長。わざわざご足労いただくなんて、すみませんねえ。")
                , me.speak("ああ、そうだった。この町に来たときに一度だけ顔を合わせた気がする。町長の、確か…ドルクさんだったか。")
                , teran.speak("ささ、いま茶を出しますんで、まあ座っててくださいよ。")
                , dolk.changeName("『辺境の町長』ドルク").speak("いや、構わんよ。すぐに住む用じゃからな。")
                , me.speak("そう言うと町長は、こちらに向かって歩いてきた。")
                , dolk.speak("数日ぶりじゃな、支部長さん。改めて見ると、なかなか良い面構えしとるじゃないか。")
                , me.speak("「はあ、ありがとうございます。\nところで、どういったご用件でしょうか？」")
                , dolk.speak("なに、ギルドが無事に再開したようじゃから、様子を見るついでに頼みたいことがあってな。")
                , dolk.speak("この街の近くに【茸の森】という場所があるのは知っておるか？。")
                , me.speak("「いえ、すみません。まだこの辺りの地理には疎いもので…」")
                , teran.speak("まあ、まだ来たばかりだからしょうがねえよな。")
                , me.speak("お茶を持ったテランさんが戻ってきた。")
                , me.speak("彼は暇さえあればこのギルド内で過ごすようになっており、早くもスタッフのような存在になりつつある。…本当に世話好きな人だ。")
                , dolk.speak("名前の通り、茸がよく取れる森でな。山菜や木材なども手に入る、貴重な場所だったんじゃがな…")
                , dolk.speak("冒険者ギルドが閉鎖して以降、モンスターが増えてしまってな。うかつに近寄れなくなって困っとるのじゃよ。")
                , teran.speak("なるほど、それで俺らに探索やら護衛をお願いしたいってことですかね？")
                , dolk.speak("そういうことじゃな。\nというわけで、これから『茸の森』での仕事を依頼させてもらうからの、よろしく頼むぞ。")
                , me.speak("そう言い残すと、町長はさっさと帰っていってしまった。……しっかりお茶を飲み干してから。")
                , me.speak("＿＿＿ページ区切り")
                , eris.speak("クエストの発生場所に【茸の森】が追加されました。")
                , eris.speak("難易度が町よりも上がるので、受注する冒険者のスキルには気を配るようにしてください。")
                , eris.speak("＜画像＞それから、クエストには「進行度」というものが存在します。")
                , eris.speak("これは、そのクエストにどのくらい時間がかかるかを表します。クエストごとに最大値が設定されていて、進行度がこれに達するとクエスト完了となります。")
                , eris.speak("最大値が１なら、冒険者１人が１日で終わるくらいの仕事ということになります。２であれば２日、あるいは２人がかりで１日かかる目安です。")
                , eris.speak("とはいえ、これはあくまで目安です。うまく行けば２の仕事量が１日で終わることもありますし、逆に失敗して全くクエストが進まないこともあります。")
                , eris.speak("それと、これは重要なことなのですが。")
                , eris.speak("一度パーティがクエストを開始すると、完了するか、全員の体力が０になるまで中断することができません。人手不足に陥らないように、パーティの配分には十分注意してください。")
                , eris.speak("…")
                , eris.speak("ここまで遊んで頂きありがとうございます。これ以降は鋭意開発中です。")
        );
    }
}
