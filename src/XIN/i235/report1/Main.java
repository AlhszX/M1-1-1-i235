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

    private static int K = 5;
    private static int xMax = 20, yMax = 20;// 20*20のスペース
    private static int positiveExample = 5, negativeExample = 5;// 正例負例五個ずつ

    public static void main(String[] args) {
        //System.out.println("report1");

        int nowK;
        String[][] space = new String[xMax][yMax];
        ExampleR1[] examples = new ExampleR1[positiveExample + negativeExample];
        String[][][] kNNspace = new String[K / 2 + 1][xMax][yMax];

        setDataAndSpace(space, examples);
        // 1-NN から k-NN中の奇数で判定する
        kNNAndDrawSpace(space, kNNspace, examples);
        //判定する前の「スペース と 例」を出力する
        System.out.println("Output space and data :");
        printSpace(space, yMax, xMax);
        System.out.println();
        //　すべての結果を出力する
        for (nowK = 0; nowK < K / 2 + 1; nowK++) {
            System.out.println(String.format("Output %d-NN result :", nowK * 2 + 1));
            printSpace(kNNspace[nowK], yMax, xMax);
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
    private static void printSpace(String[][] s, int yMax, int xMax) {
        int pointX, pointY;
        for (pointY = 0; pointY < yMax; pointY++) {
            for (pointX = 0; pointX < xMax; pointX++) {
                System.out.print(s[pointX][pointY] + "  ");
            }
            System.out.println();
        }
    }

    //データ入力とスペース描く
    private static void setDataAndSpace(String[][] space, ExampleR1[] examples) {

        int exampleNum, pointX, pointY;
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

    }

    //メイン
    private static void kNNAndDrawSpace(String[][] space, String[][][] kNNspace, ExampleR1[] examples) {

        int nowK, nN, nP, top;
        int pointX, pointY, exampleNum;
        double temDis;

        NearestExample[] nearestExampleRank;

        for (pointY = 0; pointY < yMax; pointY++) {
            for (pointX = 0; pointX < xMax; pointX++) {
                //data pointをジャンプする
                if (space[pointX][pointY].equals(".")) {
                    nearestExampleRank = new NearestExample[examples.length]; // reset nearestExample

                    for (exampleNum = 0; exampleNum < nearestExampleRank.length; exampleNum++) {
                        temDis = Math.pow(pointX - examples[exampleNum].x, 2) + Math.pow(pointY - examples[exampleNum].y, 2);//実距離^2
                        nearestExampleRank[exampleNum] = new NearestExample();
                        createAndSort(exampleNum, temDis, nearestExampleRank);
                    }
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
                        //多数決で判断する　そして　マークする　nN<=nPならpositiveにする
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
    }
    //データを配列に入れながら整理する　距離の数値　小さいー＞大きい
    private static void createAndSort(int exampleNum, double temDis, NearestExample[] nearestExampleRank) {

        int rankNum, sortNum;
        for (rankNum = 0; rankNum < exampleNum; rankNum++) {
            if (temDis < nearestExampleRank[rankNum].distance) {
                break;
            }
        }
        if (rankNum < exampleNum) {//exampleNum == 0 の場合　直接elseになる
            for (sortNum = exampleNum; sortNum > rankNum; sortNum--) {
                nearestExampleRank[sortNum].distance = nearestExampleRank[sortNum - 1].distance;
                nearestExampleRank[sortNum].type = nearestExampleRank[sortNum - 1].type;
            }
            nearestExampleRank[rankNum].distance = temDis;
            nearestExampleRank[rankNum].type = exampleNum < positiveExample;
        } else {
            nearestExampleRank[exampleNum].distance = temDis;
            nearestExampleRank[exampleNum].type = exampleNum < positiveExample;
        }
    }

}
