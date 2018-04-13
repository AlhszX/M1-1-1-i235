package XIN.ToolPackage;

public class MyTool {

    public int toUnsigned (int number){
        return number & 0X0FFFF;
    }

    public double makeDistancing(int pointX, int pointY){
        return Math.sqrt(pointX *pointX +pointY *pointY);
    }
}
