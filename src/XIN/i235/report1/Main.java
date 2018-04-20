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

        int exampleNum, pointX, pointY;
        String[][] space = new String[xMax][yMax];
        ExampleR1[] examples = new ExampleR1[positiveExample + negativeExample];

        // 例を初期化する　ランダムでデータ入り
        for (exampleNum = 0; exampleNum < 10; exampleNum++) {
            examples[exampleNum] = new ExampleR1();
            examples[exampleNum].type = exampleNum < positiveExample;
        }
        for (pointY = 0; pointY < yMax; pointY++) {
            for (pointX = 0; pointX < xMax; pointX++) {
                space[pointX][pointY] = ".";
            }
        }
        // 正例負例をスペースに記入する　同じデータを取り除く
        for (exampleNum = 0; exampleNum < examples.length; exampleNum++) {
            if (space[examples[exampleNum].x][examples[exampleNum].y].equals(".")) {
                if (examples[exampleNum].type) {
                    space[examples[exampleNum].x][examples[exampleNum].y] = "@";
                } else {
                    space[examples[exampleNum].x][examples[exampleNum].y] = "X";
                }
            } else {
                System.out.println("Oops! RESET the same data..");
                examples[exampleNum] = new ExampleR1();
                exampleNum--;
            }
        }
        //just for test
        /*for (int i = 0; i < 10; i++) {
            System.out.println("point:" + examples[i].x + "," + examples[i].y + " type: " + examples[i].type);
        }*/
        // 1-NN から k-NN中の奇数で判定する　今k = 5
        String[][][] kNNspace = new String[K / 2 + 1][xMax][yMax];
        NearestExample[] nearestExampleRank;
        int nowK, nN, nP, top;
        double temDis;
        //NearestExample temNearE;

        for (pointY = 0; pointY < yMax; pointY++) {
            for (pointX = 0; pointX < xMax; pointX++) {

                if (space[pointX][pointY].equals(".")) {//data point　以外の点を判断する
                    nearestExampleRank = new NearestExample[examples.length]; // reset nearestExample

                    for (exampleNum = 0; exampleNum < nearestExampleRank.length; exampleNum++) {

                        temDis = Math.pow(Math.pow(pointX - examples[exampleNum].x, 2) + Math.pow(pointY - examples[exampleNum].y, 2), 0.5);//実距離
                        nearestExampleRank[exampleNum] = new NearestExample();
                        //多分　O(n^2/2)…？//一応新しいやり方が出る前にボツしない
                        int i, j;
                        for (i = 0; i < exampleNum; i++) {
                            if (temDis < nearestExampleRank[i].distance) {
                                break;
                            }
                        }
                        if (i < exampleNum) {//exampleNum == 0 の場合　直接elseになる
                            for (j = exampleNum; j > i; j--) {
                                nearestExampleRank[j].distance = nearestExampleRank[j - 1].distance;
                                nearestExampleRank[j].type = nearestExampleRank[j - 1].type;
                            }
                            nearestExampleRank[i].distance = temDis;
                            nearestExampleRank[i].type = exampleNum < positiveExample;
                        } else {
                            nearestExampleRank[exampleNum].distance = temDis;
                            nearestExampleRank[exampleNum].type = exampleNum < positiveExample;
                        }
                    }
                    // just for test
                    /*System.out.println("pointX: " + pointX + " pointY: " + pointY);
                    for (NearestExample element : nearestExampleRank) {
                        System.out.println("dis: " + element.distance + " type: " + element.type);
                    }
                    System.out.println();*/
                    //BubbleSort(nearestExampleRank);//距離の大きさによって配列を整理する//O(n^2)ボツ
                    //今k==5　配列頭の1,3,5個ポイントによて判断する
                    //System.out.println("pointX: " + pointX + " pointY: " + pointY);
                    for (nowK = 0; nowK < K / 2 + 1; nowK++) {
                        for (top = 0, nN = 0, nP = 0; top < 2 * nowK + 1; top++) {
                            if (nearestExampleRank[top].type) {
                                nP++;
                            } else {
                                nN++;
                            }
                        }
                        //just for test
                        //System.out.println("nowK: " + nowK + " top: " + top);
                        //System.out.println("nP: " + nP + " nN: " + nN);
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
            //結果をきれいにプリントするため
            if (nowK == 0) {
                for (int p = 0; p < 9; p++) {
                    System.out.println();
                }
            }
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
    /*private static void BubbleSort(NearestExample[] rank) {
        int compared, comparing;
        NearestExample temNE;
        for (compared = 0; compared < rank.length - 1; compared++) {
            for (comparing = 0; comparing < rank.length - 1 - compared; comparing++) {
                if (rank[comparing].distance > rank[comparing + 1].distance) {
                    temNE = rank[comparing];
                    rank[comparing] = rank[comparing + 1];
                    rank[comparing + 1] = temNE;
                }
            }
        }
        for (NearestExample element : rank)
            System.out.println(element.distance);
        System.out.println();
    }*/

}
