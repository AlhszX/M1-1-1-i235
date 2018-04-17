package XIN.i235.report2;

public class Main {
    //締め切り4/30
    //20*20のスペース　左半分正例右半分負例
    //ランダムで五個づつ選んで
    //1-NNで判定をする
    //正解率を計算する
    //繰り返す１０回　平均値を計算する
    //正例負例二個づつの場合
    public static void main(String[] args) {
        System.out.println("report2");
        ExampleR2[] examples = new ExampleR2[10];

        for (int number = 0; number < 10; number++) {
            examples[number].x = int(Math.random());
            examples[number].y = 1;
            examples[number].type = number < 5;
        }
    }
}
