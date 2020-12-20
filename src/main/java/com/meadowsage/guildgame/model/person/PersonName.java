package com.meadowsage.guildgame.model.person;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonName {
    @Getter
    private String firstName;
    @Getter
    private String familyName;

    public String getFullName() {
        return firstName + "・" + familyName;
    }

    public static PersonName generateRandom() {
        PersonName personName = new PersonName();
        Generator generator = new Generator();
        personName.firstName = generator.generate();
        personName.familyName = generator.generate();
        return personName;
    }

    public static PersonName of(String firstName, String familyName) {
        PersonName personName = new PersonName();
        personName.firstName = firstName;
        personName.familyName = familyName;
        return personName;
    }

    private static class Generator {
        private String generate() {
            String prefix = ENGLISH_PREFIX.get((int) (Math.random() * ENGLISH_PREFIX.size()));
            String suffix = ENGLISH_SUFFIX.get((int) (Math.random() * ENGLISH_SUFFIX.size()));
            String name = prefix + suffix;
            // マップの対応表で文字を結合
            for (Map.Entry<String, String> entry : NAME_FIX_MAP.entrySet()) {
                name = name.replace(entry.getKey(), entry.getValue());
            }
            return name;
        }

        private static final Map<String, String> NAME_FIX_MAP = Stream.of(new String[][]{
                        {"-", ""},
                        {"ka", "カ"},
                        {"ki", "キ"},
                        {"ku", "ク"},
                        {"ke", "ケ"},
                        {"ko", "コ"},
                        {"sa", "サ"},
                        {"shi", "シ"},
                        {"su", "ス"},
                        {"se", "セ"},
                        {"so", "ソ"},
                        {"ta", "タ"},
                        {"chi", "チ"},
                        {"tsu", "ツ"},
                        {"te", "テ"},
                        {"to", "ト"},
                        {"na", "ナ"},
                        {"ni", "ニ"},
                        {"nu", "ヌ"},
                        {"ne", "ネ"},
                        {"no", "ノ"},
                        {"ha", "ハ"},
                        {"hi", "ヒ"},
                        {"fu", "フ"},
                        {"he", "ヘ"},
                        {"ho", "ホ"},
                        {"ma", "マ"},
                        {"mi", "ミ"},
                        {"mu", "ム"},
                        {"me", "メ"},
                        {"mo", "モ"},
                        {"ya", "ヤ"},
                        {"yu", "ユ"},
                        {"yo", "ヨ"},
                        {"ra", "ラ"},
                        {"ri", "リ"},
                        {"ru", "ル"},
                        {"re", "レ"},
                        {"ro", "ロ"},
                        {"wa", "ワ"},
//            {"wo", "ヲ"},
//            {"n", "ン"},
                        {"ga", "ガ"},
                        {"gi", "ギ"},
                        {"gu", "グ"},
                        {"ge", "ゲ"},
                        {"go", "ゴ"},
                        {"za", "ザ"},
                        {"zi", "ジ"},
                        {"zu", "ズ"},
                        {"ze", "ゼ"},
                        {"zo", "ゾ"},
                        {"da", "ダ"},
//            {"di", "ヂ"},
//            {"du", "ヅ"},
                        {"de", "デ"},
                        {"do", "ド"},
                        {"ba", "バ"},
                        {"bi", "ビ"},
                        {"bu", "ブ"},
                        {"be", "ベ"},
                        {"bo", "ボ"},
                        {"pa", "パ"},
                        {"pi", "ピ"},
                        {"pu", "プ"},
                        {"pe", "ペ"},
                        {"po", "ポ"},
                        {"kya", "キャ"},
                        {"kyu ", "キュ"},
                        {"kyo", "キョ"},
                        {"sha", "シャ"},
                        {"shu ", "シュ"},
                        {"sho", "ショ"},
                        {"cha", "チャ"},
                        {"chu ", "チュ"},
                        {"cho", "チョ"},
                        {"nya", "ニャ"},
                        {"nyu ", "ニュ"},
                        {"nyo", "ニョ"},
                        {"hya", "ヒャ"},
                        {"hyu ", "ヒュ"},
                        {"hyo", "ヒョ"},
                        {"mya", "ミャ"},
                        {"myu ", "ミュ"},
                        {"myo", "ミョ"},
                        {"rya", "リャ"},
                        {"ryu ", "リュ"},
                        {"ryo", "リョ"},
                        {"gya", "ギャ"},
                        {"gyu ", "ギュ"},
                        {"gyo", "ギョ"},
                        {"ja", "ジャ"},
                        {"ju ", "ジュ"},
                        {"jo", "ジョ"},
                        {"bya", "ビャ"},
                        {"byu ", "ビュ"},
                        {"byo", "ビョ"},
                        {"pya", "ピャ"},
                        {"pyu ", "ピュ"},
                        {"pyo", "ピョ"},
                        {"fa", "ファ"},
                        {"fi", "フィ"},
                        {"fe", "フェ"},
                        {"fo", "フォ"},
                        {"fyu", "フュ"},
                        {"wi", "ウィ"},
                        {"we", "ウェ"},
                        {"wo", "ウォ"},
                        {"va", "ヴァ"},
                        {"vi", "ヴィ"},
                        {"ve", "ヴェ"},
                        {"vo", "ヴォ"},
                        {"tsa", "ツァ"},
                        {"tsi", "ツィ"},
                        {"tse", "ツェ"},
                        {"tso", "ツォ"},
                        {"che", "チェ"},
                        {"she", "シェ"},
                        {"je", "ジェ"},
                        {"ti", "ティ"},
                        {"di", "ディ"},
                        {"du", "デュ"},
                        {"tu", "トゥ"},
                        {"a", "ア"},
                        {"i", "イ"},
                        {"u", "ウ"},
                        {"e", "エ"},
                        {"o", "オ"},
                        {"k", "ク"},
                        {"s", "ス"},
                        {"t", "ツ"},
                        {"n", "ン"},
                        {"h", ""},
                        {"m", "ム"},
                        {"y", "イ"},
                        {"r", "ル"},
                        {"w", "ウ"}}
        ).collect(Collectors.toMap(data -> data[0], data -> data[1]));

        private static final List<String> ENGLISH_PREFIX = Arrays.asList(
                "アー-",
                "アード-",
                "アク-",
                "アクス-",
                "アクス-",
                "アシュ-",
                "アスt-",
                "アニス",
                "アバー-",
                "アポン",
                "インバ-",
                "ウェル-",
                "ウェルド-",
                "エクセ-",
                "オークター-",
                "オーチェン-",
                "カm-",
                "カー-",
                "カー-",
                "カーク-",
                "カル-",
                "キn-",
                "キr-",
                "ギr-",
                "ギr-",
                "キング-",
                "グレn-",
                "コンベ",
                "シェプ-",
                "シプ-",
                "スウィン-",
                "スタn-",
                "スタn-",
                "ストウ-",
                "ストラス-",
                "ストリート-",
                "ストリート",
                "ソープ-",
                "ターn-",
                "ダル-",
                "タン-",
                "ダン-",
                "ダン-",
                "チェプ-",
                "ディn-",
                "ティリ-",
                "ティリ-",
                "ドラム-",
                "トレ-",
                "ナン-",
                "ナンs-",
                "ナント-",
                "ノー-",
                "バr-",
                "バr-",
                "パーヴァ",
                "バラ-",
                "バリ-",
                "パント",
                "ピット-",
                "フィン-",
                "プール",
                "ブラッド-",
                "ブリー-",
                "ブレ-",
                "ブレn-",
                "ブレn-",
                "ペn-",
                "ベクス-",
                "ベック",
                "ポース-",
                "ボーン-",
                "ポル-",
                "ホルム-",
                "ポンt-",
                "マグナ",
                "モス-",
                "ラn-",
                "ラn-",
                "ラn-",
                "ラング-",
                "リン-",
                "リン-",
                "リング-",
                "リング-"
        );

        private static final List<String> ENGLISH_SUFFIX = Arrays.asList(
                "-aム",
                "-アク",
                "アニス",
                "アポン",
                "-iング",
                "-ィ",
                "-ィー",
                "-ウィック",
                "-ウィッチ",
                "-ウィッチ",
                "-ウォルド",
                "-eイ",
                "-エイク",
                "-oック",
                "-カーク",
                "-ガース",
                "-カーディン",
                "-カーデン",
                "-カスター",
                "-ギル",
                "-グレン",
                "-ゲート",
                "-ケス",
                "-ケルド",
                "-コイド",
                "-コット",
                "-コット",
                "コンベ",
                "-ショー",
                "-スウェイト",
                "-スター",
                "-スター",
                "-ステッド",
                "-ストウ",
                "-ストーク",
                "ストリート",
                "-ソープ",
                "-チェス",
                "-チェスター",
                "-ック",
                "-ディーン",
                "-デール",
                "-デン",
                "-トフト",
                "-ドレス",
                "-トン",
                "-ドン",
                "-ネス",
                "パーヴァ",
                "-バーグ",
                "-ハースト",
                "-バーン",
                "-ハイズ",
                "-ハイズ",
                "-バラ",
                "-バラ",
                "-バリー",
                "パント",
                "-ビー",
                "-ファース",
                "-フィールド",
                "プール",
                "-フォース",
                "-フォース",
                "-フォード",
                "-フォス",
                "-フォス",
                "-ブラ",
                "ベック",
                "-ベリー",
                "-ポート",
                "-ホープ",
                "-ボスト",
                "-マウス",
                "マグナ",
                "-ミア",
                "-ミンスター",
                "-レイ",
                "-レイ",
                "-レイ",
                "-ロー",
                "-ロー",
                "-ワージー",
                "-ワース",
                "-ワーディン"
        );
    }
}