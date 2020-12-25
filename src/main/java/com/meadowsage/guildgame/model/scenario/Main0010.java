package com.meadowsage.guildgame.model.scenario;


import com.meadowsage.guildgame.model.World;

import java.util.Arrays;
import java.util.List;

public class Main0010 extends ScenarioBase {

    @Override
    public String getTitle() {
        return "チュートリアル";
    }

    @Override
    public boolean canStart(World world) {
        // 条件：なし
        return true;
    }

    @Override
    public List<ScenarioContent> getContents() {
        Speaker me = new Speaker("", "");
        Speaker hasa = new Speaker("???", "face_1.png");
        Speaker teran = new Speaker("???", "face_2.png");

        return Arrays.asList(
                me.speak("「ゲホッ、ゲホッ…」")
                ,me.speak("カウンターに積もったホコリを払い、周囲を見渡す。")
                ,me.speak("今日からここが私の職場だ。\n木造２階建て。古くて、カビ臭い。掃除すれば使えそうだが、なんとも頼りない建物だ。さて………")
                , hasa.speak( "おはようございます。")
                , me.speak("後ろから声がする。\n振り返ると、建物の入口に女性が立っていた。誰だ…？")
                , hasa.changeName("『秘書』ハサ").speak( "ハサと申します。あなたの秘書を仰せつかっております、支部長。")
                , me.speak("秘書…そういえば、そんな話を聞いていた気がする。")
                , hasa.speak( "すでに本部の方から聞いていると思いますが、貴方にはこの町の冒険者ギルドの支部長として、冒険者の管理やギルドの運営を一任いたします")
                , me.speak("経緯はよく覚えていないが、そういうことらしい。")
                , hasa.speak( "…あまり物覚えがよろしくないようですので、まずは簡単な仕事から覚えていただきます。")
                , me.speak("辛辣な人だ。その分仕事ができそうな雰囲気が漂っているが…")
                , hasa.speak( "冒険者ギルドには、腕利きの冒険者の助けを求めて様々な依頼がやってきます。")
                , hasa.speak( "町の見回りといった雑用から、ダンジョンの探索、モンスターの討伐などです。我々はこれをクエストと呼んでいます。")
                , hasa.speak( "冒険者ギルドの役割は、これらのクエストを請け負い、適任と思われる冒険者に紹介することです。")
                , hasa.speak( "冒険者がクエストを達成すれば、依頼人から報酬が支払われます。これを冒険者に渡した分の残りが利益となり、運営資金に回されます")
                , hasa.speak( "冒険者を適切に管理しながらクエストをこなし、皆から信頼されるギルドを目指してください。")
                , me.speak("なるほど。\nちなみに、このギルドに冒険者は何人くらいいるんだ？")
                , hasa.speak( "１人です")
                , me.speak("え？")
                , hasa.speak( "ですから、１人です。元々ここは辺境の小さな町で、近くにめぼしいダンジョンもないので冒険者の集まりが悪かったのですが、支部の閉鎖に伴って大半の冒険者は他の町に移ってしまいました。")
                , hasa.speak( "")
                , hasa.speak( "今残っているのは、移籍の波に乗り遅れ、小さな町でくすぶっている化石のような冒険者だけです。")
                , teran.speak( "おい…それは俺のことか？")
                , hasa.speak( "あら、噂をすれば。")
                , teran.speak( "よう、あんたが新しい支部長だな？")
                , me.speak("そのようですね。")
                , teran.speak("なんで他人事なんだ…まあいい。")
                , teran.changeName("『町の世話役』テラン").speak("俺はテランだ。冒険者だが、この町の世話役みたいなこともしている。なんせ冒険者がいなくなっちまったからな、俺がやるしかねえんだよ。")
                , teran.speak("…まあ、こいつの言うことも間違っちゃいないんだがな、あいにくこの町には愛着があるんだ。ギルドのことでも町のことでも、わからないことがあれば何でも聞いてくれ。")
                , me.speak("ハサの方を指差し、テランが自嘲気味に笑う。とりあえず、悪い人ではなさそうだ。")
                , hasa.speak( "では早速、仕事をしてもらいましょうか。ちょうどギルド再開の話を聞きつけて、新しいクエストが舞い込んで来たところです。")
                , teran.speak( "いま依頼が来ているクエストは、「クエスト」ページの一覧で確認できます。")
                , teran.speak( "どれどれ…って、『町中の草むしり』！？やれやれ、これが冒険者の仕事かねぇ…")
                , hasa.speak( "今のギルドの評判では、この程度が精一杯でしょう。小さな仕事を積み重ねて評判を高めていけば、より報酬の高いクエストが来るようになりますよ。")
                , me.speak( "そういうものか。")
                , hasa.speak( "はい。では、まずは「冒険者」のページでテランの情報を確認してください。")
        );
    }
}
