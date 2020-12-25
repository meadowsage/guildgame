package com.meadowsage.guildgame.model.scenario;


import com.meadowsage.guildgame.model.World;

import java.util.Arrays;
import java.util.List;

public class Main0020 extends ScenarioBase {

    @Override
    public String getTitle() {
        return "新しい冒険者";
    }

    @Override
    public boolean canStart(World world) {
        // 条件：2日目以降
        return world.getGameDate() >= 2;
    }

    @Override
    public List<ScenarioContent> getContents() {
        Speaker me = new Speaker("", "");
        Speaker hasa = new Speaker("『秘書』ハサ", "face_1.png");
        Speaker teran = new Speaker("『町の世話役』テラン", "face_2.png");

        return Arrays.asList(
                teran.speak("よう、帰ったぞ")
                , me.speak("掃除をしていた手を止め、声のする方を向く。\nクエストを終えたテランさんが帰ってきたようだ。")
                , hasa.speak("お疲れさまです、テランさん")
                , teran.speak("まったくコキ使いやがって。そっちは順調か？")
                , me.speak("「ええ、バタバタしていますが」")
                , teran.speak("おお、そうかそうか")
                , me.speak("嬉しそうにうなずいている。どうやらテランさんは、他人の世話を焼くのが好きなようだ")
                , hasa.speak("ところでテランさん、例の件はどうなりましたか？")
                , teran.speak("ああ、勿論だ。何人かは乗り気だったから、今日にでも申請に来るだろうよ")
                , me.speak("なんのことだろう。ハサさんは昨日、説明を終えるなりどこかに行ってしまったのだが、何か相談でもしていたのだろうか")
                , hasa.speak("支部長")
                , me.speak("……ああ、私のことか")
                , hasa.speak("今日は新しい冒険者の登録業務を覚えてもらいます")
                , me.speak("登録？")
                , hasa.speak("はい。冒険者ギルドは登録制です。誰でもクエストを受けられるわけではなく、事前に審査を受けて通過した者に限られます")
                , me.speak("なるほど、怪しい奴を入れてしまっては信用に関わるわけか")
                , hasa.speak("とはいっても、厳密な審査や試験などを行うわけではありません。\n簡単なチェックを行い、大きな問題がないかを確認する程度です")
                , hasa.speak("今はとにかく人手が足りません。余程のことがない限り、今日来る予定の申請は承認してください")
                , me.speak("わかった、そうしよう")
        );
    }
}
