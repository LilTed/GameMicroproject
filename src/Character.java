import java.util.ArrayList;

public class Character extends Gameobject {
    boolean alive = true;

    protected Character(int x, int y, char g) {
        super(x, y, g);
    }

    public boolean isAlive() {
        return alive;
    }

    protected void kill() {
        alive = false;
    }

    protected boolean onCollide(Object obj) {
        return false;
    }

    protected void move(int x, int y, ArrayList<Gameobject> objs) {
        int newX = this.x + x;
        int newY = this.y + y;

        for (Gameobject obj : objs) {
            if (newX == obj.getX() && newY == obj.getY()) {
                if(onCollide(obj)) {
                    return;
                }
            }
        }

        this.x += x;
        this.y += y;
    }
}
