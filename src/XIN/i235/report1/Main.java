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
        int exampleN, pointX, pointY;
        // 20*20のスペース
        String[][] space = new String[20][20];
        // 正例負例五個ずつ
        Example[] examples = new Example[10];
        // 例を初期化する　ランダムでデータ入り
        for (exampleN = 0; exampleN < 10; exampleN++) {
            examples[exampleN] = new Example();
            examples[exampleN].type = exampleN < 5;
        }
        for (pointY = 0; pointY < 20; pointY++) {
            for (pointX = 0; pointX < 20; pointX++) {
                space[pointX][pointY] = ".";
            }
        }
        // 正例負例をスペースに記入する　同じデータを取り除く
        for (exampleN = 0; exampleN < 10; exampleN++) {
            if (space[examples[exampleN].x][examples[exampleN].y].equals(".")) {
                if (examples[exampleN].type) {
                    space[examples[exampleN].x][examples[exampleN].y] = "@";
                } else {
                    space[examples[exampleN].x][examples[exampleN].y] = "X";
                }
            } else {
                System.out.println("Oops! RESET the same data..");
                examples[exampleN] = new Example();
                exampleN--;
            }
        }
        //just for test
        for (int i = 0; i < 10; i++) {
            System.out.println("point:" + examples[i].x + "," + examples[i].y + " type: " + examples[i].type);
        }
        //判定する前の「スペース と 例」を出力する
        System.out.println("Output space and data :");
        for (pointY = 0; pointY < 20; pointY++) {
            for (pointX = 0; pointX < 20; pointX++) {
                System.out.print(space[pointX][pointY] + "  ");
            }
            System.out.println();
        }
        System.out.println();
        // 1-NN から k-NNで判定する　今k = 5
        String[][][] kNNspace = new String[K][20][20];
        for (int spaceK = 0; spaceK < K; spaceK++) {
            kNNspace[spaceK] = space;
        }
        NearestExample[] nearestExampleRank;
        int temDisX, temDisY, nowK, nN, nP, top;
        int compared, comparing;
        double temDis;
        NearestExample temNE;

        for (pointY = 0; pointY < 20; pointY++) {
            for (pointX = 0; pointX < 20; pointX++) {
                if (space[pointX][pointY].equals(".")) {
                    nearestExampleRank = new NearestExample[10]; // reset nearestExample
                    for (exampleN = 0; exampleN < 10; exampleN++) {
                        temDisX = pointX - examples[exampleN].x;
                        temDisY = pointY - examples[exampleN].y;
                        temDis = Math.sqrt(temDisX * temDisX + temDisY * temDisY);

                        nearestExampleRank[exampleN] = new NearestExample();
                        nearestExampleRank[exampleN].distancing = temDis;
                        nearestExampleRank[exampleN].type = exampleN < 5;
                    }
                    for (compared = 0; compared < 9; compared++) {
                        for (comparing = 0; comparing < 9 - compared; comparing++) {
                            if (nearestExampleRank[comparing].distancing > nearestExampleRank[comparing + 1].distancing) {
                                //swap data
                                temNE = nearestExampleRank[comparing];
                                nearestExampleRank[comparing] = nearestExampleRank[comparing + 1];
                                nearestExampleRank[comparing + 1] = temNE;
                            }
                        }
                    }
                    for (nowK = 0; nowK < K; nowK++) {
                        for (top = 0, nN = 0, nP = 0; top < nowK + 1; top++) {
                            if (nearestExampleRank[top].type) {
                                nP++;
                            } else {
                                nN++;
                            }
                        }
                        if (nN > nP) {
                            kNNspace[nowK][pointX][pointY] = "x";
                        } else {
                            kNNspace[nowK][pointX][pointY] = "o";
                        }
                        //just for test
                        System.out.println("K = " + nowK + " point: " + pointX + "," + pointY + "");
                        System.out.println("nP = " + nP + " ,nN = " + nN);
                        System.out.println("result:" + kNNspace[nowK][pointX][pointY]);
                    }
                    //just for test
                    System.out.println(String.format("point %d, %d:", pointX, pointY));
                    for (int i = 0; i < 10; i++) {
                        System.out.println("dis: " + nearestExampleRank[i].distancing + " type: " + nearestExampleRank[i].type);
                    }
                }
            }
        }
        //　すべての結果を出力する
        for (nowK = 0; nowK < K; nowK++) {
            System.out.println(String.format("Output %d-NN result :", nowK + 1));
            for (pointY = 0; pointY < 20; pointY++) {
                for (pointX = 0; pointX < 20; pointX++) {
                    System.out.print(kNNspace[nowK][pointX][pointY] + "  ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
