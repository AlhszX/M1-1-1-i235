package XIN.i235.report3;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static int stoneNum;
    private static int i, j;

    private static long count;
    private static long count2, count1;

    private static List<ArrayList<GameNode>> deep;
    private static ArrayList<GameNode> nodeList;
    private static StoneList stoneList;

    private static ArrayList<ArrayList<Boolean>> sampleList;

    public static void main(String[] args) {

        //締め切り5.8
        //OXOXOX.....　－＞OOO...XXX...　OR　XXX...OOO...
        //最短手順
        //方法を示す
        //探索回数を示す

        System.out.println("report 3");

        for (int halfStone = 3; halfStone < 9; halfStone++) {

            stoneNum = 2 * halfStone;
            count = 0;
            count1 = 0;
            count2 = 0;

            mainLoop();
        }
    }

    private static void mainLoop() {
        deep = new ArrayList<>();
        sampleList = new ArrayList<>();
        //deep 0
        init();
        stoneList.init(stoneNum);
        sampleList.add(stoneList.getList());
        nodeList.add(new GameNode(0, stoneList.getList()));
        deep.add(nodeList);

        long time1 = System.currentTimeMillis();
        int finalNodNum = figureOut();
        long time2 = System.currentTimeMillis();

        System.out.println("\n");
        System.out.printf("use time : %d ms\n", time2 - time1);
        System.out.print("Stone Number = " + stoneNum);
        replay(finalNodNum);
        System.out.println("**************************************");
        System.out.printf("Try to move : %d\n", count2);
        System.out.printf("All success move : %d\n", count1);
        System.out.printf("All search node : %d\n", count);
        System.out.println("**************************************");

    }


    private static int figureOut() {
        //boolean overFlag = false;
        ArrayList<Boolean> temList;
        ArrayList<GameNode> temNodeList;

        int nodeNum, stone, to;
        int temNodeListSize;

        while (true) {

            //System.out.println("\n\nDeep : " + deep.size());
            //System.out.println("NodeSize : " + nodeList.size());
            temNodeList = nodeList;
            init();

            for (nodeNum = 0, temNodeListSize = temNodeList.size(); nodeNum < temNodeListSize; nodeNum++) {

                temList = temNodeList.get(nodeNum).stoneList;

                //System.out.print("\nlist --->   ");
                //stoneList.printList(temList);
                //System.out.print("\n");

                for (stone = 0; stone < stoneNum; stone++) {
                    for (to = 0; to <= stoneNum; to++) {

                        stoneList.setList(temList);
                        count2++;
                        if (stoneList.move(stone, to)) {
                            //System.out.printf("move %d %d to %d %d --->  ", stone, stone + 1, to, to + 1);
                            stoneList.rmSideNull();
                            //stoneList.printList();
                            count1++;

                            if (checkSame(stoneList.getList())) {
                                nodeList.add(new GameNode(nodeNum, stoneList.getList()));
                                count++;
                            }
                            if (stoneList.checkSuccess(stoneNum)) {
                                //overFlag = true;
                                //System.out.println("success ->over");
                                return nodeNum;
                            }
                        }

                    }

                }
            }
            deep.add(nodeList);
        }
    }

    private static void init() {
        nodeList = new ArrayList<>();
        stoneList = new StoneList();
    }

    private static boolean checkSame(ArrayList<Boolean> checkList) {
        //System.out.println("use check");
        ArrayList<Boolean> temSampleList;
        //int i, j;
        int sampleListSize = sampleList.size(), checkListSize = checkList.size();

        for (i = 0; i < sampleListSize; i++) {
            temSampleList = sampleList.get(i);
            if (checkListSize != temSampleList.size()) {
                continue;
            }
            for (j = 0; j < checkListSize; j++) {
                if (checkList.get(j) != temSampleList.get(j)) {
                    break;
                }
            }
            if (j == checkListSize) {
                //System.out.println("  -> have same list");
                return false;
            }
        }
        sampleList.add(checkList);
        //System.out.println("  -> have no same in sample -> add  ");
        return true;

    }

    private static void replay(int nodeNum) {
        ArrayList<Boolean> temList;
        ArrayList<ArrayList<Boolean>> temListList = new ArrayList<>();

        int temNum1 = nodeNum, temNum2;
        //int i;
        int deepSize = deep.size();

        temListList.add(stoneList.getList());

        for (i = 1; i <= deepSize; i++) {
            //System.out.println("node number" + temNum1);
            temList = deep.get(deepSize - i).get(temNum1).stoneList;
            temNum2 = deep.get(deepSize - i).get(temNum1).from;
            temListList.add(temList);
            temNum1 = temNum2;
        }

        System.out.print("\n");
        System.out.println("**************************************");
        System.out.println("Result:");
        System.out.println("**************************************");
        for (i = temListList.size() - 1; i >= 0; i--) {
            System.out.printf("Step: %d ->  ", temListList.size() - 1 - i);
            stoneList.printList(temListList.get(i));
            System.out.println();
        }
    }

}

class GameNode {
    int from;
    ArrayList<Boolean> stoneList;

    GameNode(int f, ArrayList<Boolean> list) {
        from = f;
        stoneList = list;
    }
}