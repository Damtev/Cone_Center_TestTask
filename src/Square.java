public class Square {

    public static final int CORNERS = 4;

    private final int id;
    private final int leftTop;
    private final int rightTop;
    private final int leftBottom;
    private final int rightBottom;

    public Square(int id, int leftTop, int rightTop, int leftBottom, int rightBottom) {

        this.id = id;
        this.leftTop = leftTop;
        this.rightTop = rightTop;
        this.leftBottom = leftBottom;
        this.rightBottom = rightBottom;
    }

    public Square(int id, int[] corners) {
        this.id = id;
        leftTop = corners[0];
        rightTop = corners[1];
        leftBottom = corners[2];
        rightBottom = corners[3];
    }

    public int getId() {
        return id;
    }

    public int getLeftTop() {
        return leftTop;
    }

    public int getRightTop() {
        return rightTop;
    }

    public int getLeftBottom() {
        return leftBottom;
    }

    public int getRightBottom() {
        return rightBottom;
    }

    @Override
    public String toString() {
        return leftTop + " " + rightTop + " " + leftBottom + " " + rightBottom;
    }
}
