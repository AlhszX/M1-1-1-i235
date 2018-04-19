package XIN.i235.report1;

public class Main {
    //第一回レポート　3点
    //締め切り4.25
    //20＊20マス　正例と　負例　5つずつ
    //空きスペース1-NNで判定する
    //正例(＠)　負例(X)　正例判定(o)　負例判定(x)
    //「+2」　
    // k-NN で判定をする　
    // 正例負例のランダム配置
    public static void main(String[] args) {
        //System.out.println("report1");
        int K = 5;
        int xMax = 20, yMax = 20;// 20*20のスペース
        int positiveExample = 5, negativeExample = 5;// 正例負例五個ずつ
        int exampleN, pointX, pointY;

        String[][] space = new String[xMax][yMax];
        ExampleR1[] examples = new ExampleR1[positiveExample + negativeExample];

        // 例を初期化する　ランダムでデータ入り
        for (exampleN = 0; exampleN < 10; exampleN++) {
            examples[exampleN] = new ExampleR1();
            examples[exampleN].type = exampleN < positiveExample;
        }
        for (pointY = 0; pointY < yMax; pointY++) {
            for (pointX = 0; pointX < xMax; pointX++) {
                space[pointX][pointY] = ".";
            }
        }
        // 正例負例をスペースに記入する　同じデータを取り除く
        for (exampleN = 0; exampleN < examples.length; exampleN++) {
            if (space[examples[exampleN].x][examples[exampleN].y].equals(".")) {
                if (examples[exampleN].type) {
                    space[examples[exampleN].x][examples[exampleN].y] = "@";
                } else {
                    space[examples[exampleN].x][examples[exampleN].y] = "X";
                }
            } else {
                System.out.println("Oops! RESET the same data..");
                examples[exampleN] = new ExampleR1();
                exampleN--;
            }
        }
        //just for test
        /*for (int i = 0; i < 10; i++) {
            System.out.println("point:" + examples[i].x + "," + examples[i].y + " type: " + examples[i].type);
        }*/

        // 1-NN から k-NN中の奇数で判定する　今k = 5
        String[][][] kNNspace = new String[K / 2 + 1][xMax][yMax];
        NearestExample[] nearestExampleRank;
        int temDisX, temDisY, nowK, nN, nP, top;
        double temDis;

        for (pointY = 0; pointY < yMax; pointY++) {
            for (pointX = 0; pointX < xMax; pointX++) {

                if (space[pointX][pointY].equals(".")) {//data point　以外の点を判断する
                    nearestExampleRank = new NearestExample[examples.length]; // reset nearestExample

                    for (exampleN = 0; exampleN < nearestExampleRank.length; exampleN++) {
                        temDisX = pointX - examples[exampleN].x;//ｘ距離
                        temDisY = pointY - examples[exampleN].y;//ｙ距離
                        temDis = Math.sqrt(temDisX * temDisX + temDisY * temDisY);//実距離

                        nearestExampleRank[exampleN] = new NearestExample();
                        nearestExampleRank[exampleN].distancing = temDis;
                        nearestExampleRank[exampleN].type = exampleN < positiveExample;
                    }

                    BubbleSort(nearestExampleRank);//距離の大きさによって配列を整理する
                    //今k==5　配列頭の1,3,5個ポイントによて判断する
                    for (nowK = 0; nowK < K / 2 + 1; nowK++) {
                        for (top = 0, nN = 0, nP = 0; top < nowK + 1; top = 1 + 2 * top) {
                            if (nearestExampleRank[top].type) {
                                nP++;
                            } else {
                                nN++;
                            }
                        }
                        //多数決で判断する　そして　マークする
                        if (nN > nP) {
                            kNNspace[nowK][pointX][pointY] = "x";
                        } else {
                            kNNspace[nowK][pointX][pointY] = "o";
                        }
                    }
                    //データポイントの場合はそのまま出力スペースに書く
                } else {
                    for (int i = 0; i < K / 2 + 1; i++) {
                        kNNspace[i][pointX][pointY] = space[pointX][pointY];
                    }
                }
            }
        }
        //判定する前の「スペース と 例」を出力する
        System.out.println("Output space and data :");
        PrintSpace(space, yMax, xMax);
        System.out.println();
        //　すべての結果を出力する
        for (nowK = 0; nowK < K / 2 + 1; nowK++) {
            System.out.println(String.format("Output %d-NN result :", nowK * 2 + 1));
            PrintSpace(kNNspace[nowK], yMax, xMax);
            System.out.println();
        }
    }
    //出力
    private static void PrintSpace(String[][] s, int yMax, int xMax) {
        int pointX, pointY;
        for (pointY = 0; pointY < yMax; pointY++) {
            for (pointX = 0; pointX < xMax; pointX++) {
                System.out.print(s[pointX][pointY] + "  ");
            }
            System.out.println();
        }
    }
    //Bubble Sort 方法で配列を小さい順　整理する
    private static void BubbleSort(NearestExample[] rank) {
        int compared, comparing;
        NearestExample temNE;
        for (compared = 0; compared < rank.length - 1; compared++) {
            for (comparing = 0; comparing < rank.length - 1 - compared; comparing++) {
                if (rank[comparing].distancing > rank[comparing + 1].distancing) {
                    temNE = rank[comparing];
                    rank[comparing] = rank[comparing + 1];
                    rank[comparing + 1] = temNE;
                }
            }
        }
    }

}
