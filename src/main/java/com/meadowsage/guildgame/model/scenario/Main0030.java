package com.meadowsage.guildgame.model.scenario;


import com.meadowsage.guildgame.model.World;

import java.util.Arrays;
import java.util.List;

public class Main0030 extends ScenarioBase {

    @Override
    public String getTitle() {
        return "茸の森";
    }

    @Override
    public boolean canStart(World world) {
        // 条件：3日目以降
        return world.getGameDate() >= 3;
    }

    @Override
    public List<ScenarioContent> getContents() {
        Speaker me = new Speaker("", "");
        Speaker elif = new Speaker("『秘書』エリフ", "face_1.png");
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
                , elif.speak("クエストの発生場所に【茸の森】が追加されました。")
                , elif.speak("クエストの難易度は場所によって変化します。受注する冒険者のスキルには気を配るようにしてください。")
                , elif.speak("冒険者の能力に不安がある場合は、複数人でクエストに参加するのも良いでしょう。参加者が多いほどクエストの成功率は上がります。")
                , elif.speak("＜画像＞それから、場所にはそれぞれ『探索度』というパラメータが存在します。")
                , elif.speak("探索度は、その場所をどれくらい調べ終わったかを表す割合です。１００％になったとき、その場所の最深部まで到達したということになります。")
                , elif.speak("探索度は主に『探索』クエストによって上昇します。探索が完了すると、何かしらのボーナスやイベントがあるかもしれませんね。")
        );
    }
}
