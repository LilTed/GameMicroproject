public class Gameobject {
    private int x;
    private int y;
    private char graphics; //Hur objekten ser ut

    protected Gameobject(int x, int y, char graphics) {
        this.x = x;
        this.y = y;
        this.graphics = graphics;
    }

    public void onLoop() {

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getGraphics() {
        return graphics;
    }

    protected void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

}
