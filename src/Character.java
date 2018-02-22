import java.util.ArrayList;

public class Character extends Gameobject {

    protected Character(int x, int y, char g) {
        super(x, y, g);
    }

    protected void move(int x, int y, ArrayList<Gameobject> objs) {
        int newX = this.x + x;
        int newY = this.y + y;

        for (Gameobject obj : objs) {
            if (newX == obj.getX() && newY == obj.getY()) {
                // collision
                return;
            }
        }

        this.x += x;
        this.y += y;
    }
}
