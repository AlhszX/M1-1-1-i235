package XIN.i235.report2;

class ExampleR2 {
    int x, y;
    boolean type;

    /*ExampleR2(int minX, int maxX, int minY, int maxY, boolean type) {
        x = (int) (Math.random() * maxX) + minX;
        y = (int) (Math.random() * maxY) + minY;
        this.type = type;
    }*/

    void setPoint() {
        if (this.type) {
            x = (int) (Math.random() * 10);
            y = (int) (Math.random() * 20);
        } else {
            x = (int) (Math.random() * 10) + 10;
            y = (int) (Math.random() * 20);
        }
    }
    //同じデータを検査するため//簡単書き方
    @Override
    public int hashCode() {
        //return super.hashCode();
        //データ量が多い場合、効率が良くない、時間があれば補足する
        return 10;
    }
    //同じデータを検査するため//簡単書き方
    @Override
    public boolean equals(Object obj) {
        //return super.equals(obj);
        //パラメータをチェックしてない、強さが足りない、時間があれば補足する
        //type判定不要　生成するとき左右分けで生成したので
        ExampleR2 dataPoint = (ExampleR2) obj;
        return this.x == dataPoint.x && this.y == dataPoint.y;
    }
}

class DataPoint {
    private int x, y;

    DataPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //同じデータを検査するため//簡単書き方
    @Override
    public int hashCode() {
        //return super.hashCode();
        //データ量が多い場合、効率が良くない、時間があれば補足する
        return 10;
    }
    //同じデータを検査するため//簡単書き方
    @Override
    public boolean equals(Object obj) {

        //System.out.println("DataPoint's equals is run !!");
        //パラメータをチェックしてない、強さが足りない、時間があれば補足する
        DataPoint dataPoint = (DataPoint) obj;
        return this.x == dataPoint.x && this.y == dataPoint.y;
        //return super.equals(obj);
    }
}
