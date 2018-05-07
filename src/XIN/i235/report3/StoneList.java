package XIN.i235.report3;

import java.util.ArrayList;

final class StoneList {

    private ArrayList<Boolean> stoneList = new ArrayList<>();
    private int listSize;

    void init(int stoneNum) {
        for (int i = 0; i < stoneNum; i++) {
            if (i % 2 == 0) {
                this.stoneList.add(true);
            } else {
                this.stoneList.add(false);
            }
        }
        listSize = stoneList.size();
    }

    ArrayList<Boolean> getList() {
        return stoneList;
    }

    void setList(ArrayList<Boolean> list) {
        stoneList = list;
        listSize = stoneList.size();
    }

    boolean move(int stone, int to) {

        if (to + 1 < stoneList.size()) {
            if (stoneList.get(to) != null || stoneList.get(to + 1) != null) {
                return false;
            }
            if (stoneList.get(stone) == null || stoneList.get(stone + 1) == null) {
                return false;
            }
        } else if (to < stoneList.size()) {
            if (stoneList.get(to) != null) {
                return false;
            }
            if (stoneList.get(stone) == null) {
                return false;
            }
        }
        if (to == stone || to == (stone + 1)) {
            return false;
        }

        ArrayList<Boolean> temList = new ArrayList<>(stoneList);

        temList.set(stone, null);
        temList.set(stone + 1, null);

        if (to == stoneList.size()) {
            temList.add(stoneList.get(stone));
            temList.add(stoneList.get(stone + 1));
        } else {
            temList.set(to, stoneList.get(stone));
            temList.set(to + 1, stoneList.get(stone + 1));
        }

        stoneList = temList;
        listSize = stoneList.size();
        return true;
    }

    boolean checkSuccess(int stoneNum) {

        if (listSize != stoneNum) {
            return false;
        }

        int i;
        for (i = 0; i < stoneNum; i++) {
            if (stoneList.get(i) == null) {
                return false;
            }

            int halfStoneNum = stoneNum / 2;
            boolean flag = stoneList.get(0);

            if (i < halfStoneNum && stoneList.get(i) != flag) {
                return false;
            }
            if (i >= halfStoneNum && stoneList.get(i) == flag) {
                return false;
            }
        }

        return true;
    }

    void rmSideNull() {
        while (stoneList.get(0) == null) {
            stoneList.remove(0);
            listSize = stoneList.size();
        }
        while (stoneList.get(listSize - 1) == null) {
            stoneList.remove(listSize - 1);
            listSize = stoneList.size();
        }
    }


    void printList() {

        for (Boolean stone : stoneList) {
            if (stone == null) {
                System.out.print("_ ");
            } else if (stone) {
                System.out.print("O ");
            } else {
                System.out.print("X ");
            }
        }
    }

    void printList(ArrayList<Boolean> list) {

        for (Boolean stone : list) {
            if (stone == null) {
                System.out.print("_ ");
            } else if (stone) {
                System.out.print("O ");
            } else {
                System.out.print("X ");
            }
        }
    }


}
