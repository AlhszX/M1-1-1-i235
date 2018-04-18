package XIN.i235.report2;

class ExampleR2 {
    int x, y;
    boolean type;

    ExampleR2(int minX, int maxX, int minY, int maxY, boolean type) {
        x = (int) (Math.random() * maxX) + minX;
        y = (int) (Math.random() * maxY) + minY;
        this.type = type;
    }
}
