package XIN.i235.report2;

import java.util.HashSet;
import java.util.Set;

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
        //パラメータを設置する
        int cycleTimes = 10;
        int positiveDataOfSmallData = 5, negativeDataOfSmallData = 5;
        int positiveDataOfSmallerData = 2, negativeDataOfSmallerData = 2;
        //毎回計算の結果
        double[] resultSmall;
        double[] resultSmaller;
        //平均値を計算するためもの
        double averageSmallN = 0, averageSmallP = 0;
        double averageSmallerN = 0, averageSmallerP = 0;
        //二つのデータ配列　　初期化
        ExampleR2[] exampleSmall = new ExampleR2[positiveDataOfSmallData + negativeDataOfSmallData];
        ExampleR2[] exampleSmaller = new ExampleR2[positiveDataOfSmallerData + negativeDataOfSmallerData];
        //１０回の循環
        for (int time = 0; time < cycleTimes; time++) {

            System.out.println(String.format("This is the %dst/nd/rd/th times loop .....", time + 1));
            System.out.println();

            setData(positiveDataOfSmallData, negativeDataOfSmallData, exampleSmall);
            hashRmDup(exampleSmall);
            setData(positiveDataOfSmallerData, negativeDataOfSmallerData, exampleSmaller);
            hashRmDup(exampleSmaller);

            System.out.println("Small Data:");
            printOutData(exampleSmall);
            resultSmall = nearestNeighbor(exampleSmall);

            averageSmallP += resultSmall[0];
            averageSmallN += resultSmall[1];

            System.out.println();

            System.out.println("Smaller Data:");
            printOutData(exampleSmaller);
            resultSmaller = nearestNeighbor(exampleSmaller);

            averageSmallerP += resultSmaller[0];
            averageSmallerN += resultSmaller[1];

            System.out.println();
        }

        System.out.println("---------------------------------The REAL Result---------------------------------");
        System.out.println();
        System.out.println(String.format("Small Data(%d/%d) %d times average result Positive is %f, Negative is %f", positiveDataOfSmallData, negativeDataOfSmallData, cycleTimes, averageSmallP / 10, averageSmallN / 10));
        System.out.println();
        System.out.println(String.format("Smaller Data(%d/%d) %d times average result Positive is %f, Negative is %f", positiveDataOfSmallerData, negativeDataOfSmallerData, cycleTimes, averageSmallerP / 10, averageSmallerN / 10));
    }

    //データ入力
    private static void setData(int pNum, int nNum, ExampleR2[] data) {
        for (int number = 0; number < pNum + nNum; number++) {
            data[number] = new ExampleR2();
            if (number < pNum) {
                data[number].type = true;
                data[number].setPoint();
            } else {
                data[number].type = false;
                data[number].setPoint();
            }
        }
    }
    //同じデータがあれば　reset
    private static void hashRmDup(ExampleR2[] examples) {
        Set<ExampleR2> exampleR2Set = new HashSet<>();
        for (int i = 0; i < examples.length; i++) {
            if (!exampleR2Set.add(examples[i])) {
                examples[i].setPoint();
                System.out.println("Re Get Point Success!!");
                i--;
            }
        }
    }
    //出力
    private static void printOutData(ExampleR2[] exampleR2) {
        for (int i = 0; i < exampleR2.length; i++) {
            System.out.println("Data number: " + i + " point: " + exampleR2[i].x + "," + exampleR2[i].y + " type: " + exampleR2[i].type);
        }
    }
    //ポイント判断
    private static double[] nearestNeighbor(ExampleR2[] exampleR2s) {
        //データポイントを計算しないため　まずhashに入れる
        Set<DataPoint> dataPointSet = new HashSet<>();
        for (ExampleR2 elements : exampleR2s) {
            dataPointSet.add(new DataPoint(elements.x, elements.y));
        }
        int nExample, x, y;
        int nP = 0, nN = 0;
        int trueNegativePoint = 0, truePositivePoint = 0;
        double temDisMin, temDisNow;
        boolean npFlag;
        //(0,0)から(19,19)まで
        for (y = 0; y < 20; y++) {
            for (x = 0; x < 20; x++) {
                //hashに入れたら計算、他は単に出力して無視する
                if (dataPointSet.add(new DataPoint(x, y))) {
                    temDisMin = Math.pow(x - exampleR2s[0].x, 2) + Math.pow(y - exampleR2s[0].y, 2);
                    npFlag = exampleR2s[0].type;
                    //距離を計算する
                    for (nExample = 0; nExample < exampleR2s.length; nExample++) {
                        temDisNow = Math.pow(x - exampleR2s[nExample].x, 2) + Math.pow(y - exampleR2s[nExample].y, 2);
                        if (temDisNow < temDisMin) {
                            temDisMin = temDisNow;
                            npFlag = exampleR2s[nExample].type;
                        }
                    }
                    //1-NNのため　一個だけ見ればいい
                    if (npFlag) {
                        nP++;
                        if (x < 10) {
                            truePositivePoint++;
                        }
                    } else {
                        nN++;
                        if (x >= 10) {
                            trueNegativePoint++;
                        }
                    }
                } else {
                    System.out.println("Jump the Data Point !! Point Is : " + x + " , " + y);
                }
            }
        }

        //一回の結果を出力する
        double accuracyP = (double) truePositivePoint / (double) nP;
        System.out.println(String.format("all Positive result Point: %d, true Positive Point: %d, accuracy of Positive: %f", nP, truePositivePoint, accuracyP));

        double accuracyN = (double) trueNegativePoint / (double) nN;
        System.out.println(String.format("all Negative result Point: %d, true Negative Point: %d, accuracy of Negative: %f", nN, trueNegativePoint, accuracyN));
        //10回平均値を計算するためのreturn
        return new double[]{accuracyP, accuracyN};
    }
}
