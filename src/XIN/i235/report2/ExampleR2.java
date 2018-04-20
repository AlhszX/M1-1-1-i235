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
}

class DataPoint {
    int x, y;

    DataPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
