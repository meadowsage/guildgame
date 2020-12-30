package com.meadowsage.guildgame.model.scenario;


import com.meadowsage.guildgame.model.world.GameWorld;

import java.util.Arrays;
import java.util.List;

public class Main0020 extends ScenarioBase {

    @Override
    public String getTitle() {
        return "新しい冒険者";
    }

    @Override
    public boolean canStart(GameWorld world) {
        // 条件：2日目以降
        return world.getGameDate() >= 2;
    }

    @Override
    public List<ScenarioContent> getContents() {
        Speaker me = new Speaker("", "");
        Speaker elif = new Speaker("『秘書』エリフ", "face_1.png");
        Speaker teran = new Speaker("『町の世話役』テラン", "face_2.png");

        return Arrays.asList(
                teran.speak("よう、帰ったぞ！")
                , me.speak("掃除をしていた手を止め、声のする方を向く。\nクエストを終えたテランさんが帰ってきたようだ。")
                , elif.speak("お疲れさまです、テランさん。")
                , teran.speak("まったくコキ使いやがってなぁ。そっちは順調か？")
                , me.speak("「ええ、まだバタバタしてはいますが」")
                , teran.speak("おお、そうかそうか！")
                , me.speak("嬉しそうにうなずいている。どうやらテランさんは、他人の世話を焼くのが好きなようだ。")
                , elif.speak("ところでテランさん、例の件はどうなりましたか？")
                , teran.speak("ああ、勿論だ。何人かは乗り気だったから、今日にでも申請に来るだろうよ。")
                , me.speak("なんのことだろう。エリフさんもテランさんも、昨日へ説明を終えるなりどこかに行ってしまったはずだが…何か相談でもしていたのだろうか？")
                , elif.speak("支部長。")
                , me.speak("……ああ、私のことか。")
                , elif.speak("今日は冒険者の登録業務を覚えてもらいます。")
                , me.speak("登録？")
                , elif.speak("はい。冒険者ギルドは登録制です。誰でもクエストを受けられるわけではなく、事前に審査を受けて通過した者に限られます。")
                , me.speak("なるほど。怪しい奴を入れてしまっては信用に関わるわけか。")
                , elif.speak("とはいっても、厳密な審査や試験などを行うわけではありません。\n簡単なチェックを行い、最低限の素養があるかを確認する程度です。")
                , elif.speak("チェックの結果は共有しますので、よほど問題がない限りは登録を許可して良いと思います。")
                , elif.speak("ただし、たくさん集めればいいというわけではありません。仕事が無い期間が続くと、冒険者はギルドを去ってしまいます。\nしばらくは２〜３人くらいが妥当でしょう。")
                , me.speak("「わかりました、そうします」")
                , teran.speak("俺からも頼むわ。なんせ久々の仕事で疲れちまってな、今日は動けそうにない。")
                , elif.speak("…草むしりで？")
                , teran.speak("…おう、腰が痛えんだわ。")
                , me.speak("エリフが大きなため息をつく。")
                , elif.speak("…支部長。彼には今日は休養をとってもらうことにしましょう。")
                , elif.speak("クエストを受注しなかった冒険者は、各々が自由行動をとります。体力がなければ休養を取り、回復に専念します。")
                , me.speak("「そうですか。わかりました、お大事になさってくださいね」")
                , teran.speak("…おお、あんた……良い奴だな。")
                , me.speak("驚いた顔でこちらを見ている。そんなに意外だっただろうか？")
                , me.speak("＿＿＿ページ送り")
                , elif.speak("＜画像＞では、支部長。メニューに「新規」という項目が増えているはずですので、そちらから申請を承認してください。")
                , elif.speak("承認前の状態では、冒険者の詳細なパラメータはわかりません。「所見」に大まかな評価が書かれているので、そちらを参考にしてください。")
                , elif.speak("緑色のボタンで承認、赤色のボタンで拒否となります。拒否することによるデメリットはありませんが、同じ冒険者は二度と現れないので注意してください。")
                , elif.speak("＜画像＞なお、承認したばかりの冒険者はクエストを受注していません。「クエスト」メニューからクエストの割り当てを行ってくださいね。")
        );
    }
}
