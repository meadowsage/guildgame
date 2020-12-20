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
                me.speak("……古くて、カビ臭い。掃除すればまだ使えそうだが、なんとも頼りない建物だ")
                , hasa.speak( "おはようございます")
                , me.speak("後ろから声がする。\n振り返ると、見知らぬ女性が立っていた。誰だ……？")
                , hasa.changeName("『補佐係』ハサ").speak( "ハサと申します。あなたの補佐係としてここにいます")
                , me.speak("口に出てしまっていたか")
                , hasa.speak( "今日からあなたには、この街の冒険者ギルドの支部長として、ギルドの運営や冒険者の管理をしていただきます")
                , me.speak("経緯はよく覚えていないが、そういうことらしい")
                , hasa.speak( "あまり物覚えがよろしくないようですので、簡単な仕事から始めていきましょう")
                , me.speak("考えを読まれているな")
                , hasa.speak( "冒険者ギルドには、さまざまな依頼がやってきます")
                , hasa.speak( "街の清掃や警備などの雑用から、ダンジョンの探索、モンスターの討伐などです。私達はこれをクエストと呼んでいます")
                , hasa.speak( "我々の仕事は、ギルドに登録された冒険者にこれらのクエストを紹介することです")
                , hasa.speak( "冒険者がクエストを達成すれば、依頼人から報酬が支払われます。これを冒険者に渡した分の残りが利益となり、運営資金に回されます")
                , hasa.speak( "冒険者をしっかり管理しながらクエストをこなし、皆から信頼されるギルドを目指してください")
                , me.speak("なるほど\n「ちなみに、このギルドに冒険者は何人くらいいるんだ？」")
                , hasa.speak( "１人です")
                , me.speak("え？")
                , hasa.speak( "ですから１人です。つい先日までここの支部は閉鎖していたので、ほとんどの冒険者は他の街に移ってしまいました")
                , hasa.speak( "今残っているのは、残念ながら別のギルドに受け入れてもらえなかった化石のような冒険者だけです…")
                , teran.speak( "おい…それは俺のことか？")
                , hasa.speak( "あら、噂をすれば")
                , teran.speak( "よう、あんたが新しい支部長だな？")
                , me.speak("「そのようです」")
                , teran.speak("なんで他人事なんだ…まあいい")
                , teran.changeName("『街の世話役』テラン").speak("俺はテランだ。冒険者だが、この街の世話役みたいなこともしていてな。ギルドのことでも街のことでも、わからないことがあったら何でも聞いてくれ")
                , me.speak("ハサの言い草は気になるが、頼れそうな人だ")
                , hasa.speak( "では早速、テランに仕事をしてもらいましょう")
        );
    }
}
