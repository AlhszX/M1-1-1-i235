package XIN.i235.report1;
import XIN.ToolPackage.MyTool;
public class Main {
    //第一回レポート　3点
    //締め切り4.25
    //20＊20マス　正例と　負例　5つずつ　【達成】
    //空きスペース1-NNで判定する
    //正例(＠)　負例(X)　正例判定(o)　負例判定(x)
    //「+2」　
    // k-NN で判定をする　
    // 正例負例のランダム配置　【達成】
    public static void main(String[] args){
        //System.out.println("report1");
        // 20*20のスペース
        String[][] space = new String[20][20];
        // 正例負例五個ずつ
        Example[] eNegative = new Example[5];
        Example[] ePositive = new Example[5];
        // 例を初期化する
        // ランダムでデータ入り
        for (int exampleN = 0; exampleN < 5; exampleN++){
            eNegative[exampleN] = new Example();
            ePositive[exampleN] = new Example();
        }

        for (int pointY = 0; pointY < 20; pointY++)
            for (int pointX = 0; pointX < 20; pointX ++)
                space[pointX][pointY] = ".";
        // 正例負例をスペースに記入する
        // 同じデータを取り除くこと
        for (int exampleN = 0; exampleN < 5; exampleN++) {
            if (space[eNegative[exampleN].x][eNegative[exampleN].y].equals(".")) {
                space[eNegative[exampleN].x][eNegative[exampleN].y] = "X";
                //space[eNegative[exampleN].x][eNegative[exampleN].y] = "N";
            } else {
                System.out.println("Oops! Get same data in eNegative and RESET..");
                eNegative[exampleN] = new Example();
                exampleN--;
            }
        }
        for (int exampleN = 0; exampleN < 5; exampleN++){
            if (space[ePositive[exampleN].x][ePositive[exampleN].y].equals(".")){
                space[ePositive[exampleN].x][ePositive[exampleN].y] = "@";
                //space[ePositive[exampleN].x][ePositive[exampleN].y] = "P";
            }else {
                System.out.println("Oops! Get same data in ePositive and RESET..");
                ePositive[exampleN] = new Example();
                exampleN--;
            }
        }
        //just for test
        //for (int exampleN = 0; exampleN < 5; exampleN++){
        //        System.out.println("Nx:" + eNegative[exampleN].x + " Ny:" + eNegative[exampleN].y);
        //        System.out.println("Px:" + ePositive[exampleN].x + " Py:" + ePositive[exampleN].y);
        //}
        //「スペース と 例」を出力する
        System.out.println("Output space and data :");
        for (int pointY = 0; pointY < 20; pointY++){
            for (int pointX = 0; pointX < 20; pointX++){
                System.out.print(space[pointX][pointY] + "  ");
            }
            System.out.println();
        }
        System.out.println();
        // 1-NN から k-NNで判定する
        // 今は一から五まで
        int k = 5;
        MyTool myTool = new MyTool();
        String[][][] kNNspace = new String[k][20][20];
        for (int spaceK = 0; spaceK < k; spaceK++)
            kNNspace[spaceK] = space;

        NearestExample[] nearestExampleRank;
        int temNDisX, temPDisX, temNDisY, temPDisY, numberK, rankingNumber;
        double temNDis, temPDis;

        for (numberK = 0; numberK < k; numberK++){

            for (int pointY =0; pointY < 20; pointY++){
                for (int pointX = 0; pointX < 20; pointX++){
                    // set and reset nearestExample
                    nearestExampleRank = new NearestExample[10];
                    rankingNumber = 0;

                    for (int exampleN = 0; exampleN < 5; exampleN++){

                        temNDisX = pointX -eNegative[exampleN].x;
                        temNDisY = pointY -eNegative[exampleN].y;
                        temNDis = myTool.makeDistancing(temNDisX, temNDisY);
                        temPDisX = pointX -ePositive[exampleN].x;
                        temPDisY = pointY -ePositive[exampleN].y;
                        temPDis = myTool.makeDistancing(temPDisX, temPDisY);

                        nearestExampleRank[rankingNumber] = new NearestExample();
                        nearestExampleRank[rankingNumber].distancing = temNDis;
                        nearestExampleRank[rankingNumber].type = false;
                        rankingNumber++;
                        //System.out.println(rankingNumber);
                        nearestExampleRank[rankingNumber] = new NearestExample();
                        nearestExampleRank[rankingNumber].distancing = temPDis;
                        nearestExampleRank[rankingNumber].type = true;
                        rankingNumber++;
                        //System.out.println(rankingNumber);
                    }
                    NearestExample temNE = new NearestExample();
                    int i, j;
                    for (i = 0; i < 9; i++){
                        for (j = 0; j < 9 -i; j++){
                            if (nearestExampleRank[j].distancing > nearestExampleRank[j +1].distancing){
                                temNE = nearestExampleRank[j];
                                nearestExampleRank[j] = nearestExampleRank[j +1];
                                nearestExampleRank[j +1] = temNE;
                            }
                        }
                    }
                    int nN = 0, nP = 0;
                    for (int top = 0; top < numberK +1; top++){
                        if (!nearestExampleRank[top].type)
                            nN++;
                        else
                            nP++;
                    }
                    if (kNNspace[numberK][pointX][pointY].equals(".")){
                        if (nN > nP)
                            kNNspace[numberK][pointX][pointY] = "x";
                        else
                            kNNspace[numberK][pointX][pointY] = "o";
                    }

                }
            }

        }

        for (int i = 0; i < k; i++){
            int temK = i +1;
            System.out.println("Output " + temK + "-NN result :");
            for (int pointY = 0; pointY < 20; pointY++){
                for (int pointX = 0; pointX < 20; pointX++){
                    System.out.print(kNNspace[i][pointX][pointY] + "  ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

}
