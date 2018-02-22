import java.util.ArrayList;

public class Monster extends Character {

    public Monster(int x, int y) {
        super(x, y, 'M');
    }

    public void onLoop(int playerX, int playerY, ArrayList<Gameobject> objs) {
        moveTowardsPlayer(playerX, playerY, objs);
    }

    private void moveTowardsPlayer(int targetX, int targetY, ArrayList<Gameobject> objs) {
        if (Math.abs(targetX - getX()) > Math.abs(targetY - getY())) {
            if (targetX < getX()) {
                move(-1, 0, objs);
            } else if (targetX > getX()) {
                move(1, 0, objs);
            }
        } else {
            if (targetY < getY()) {
                move(0, -1, objs);
            } else if (targetY > getY()) {
                move(0, 1, objs);
            }
        }
    }

    @Override
    protected boolean onCollide(Object obj) {
        if (obj instanceof Monster) {
            alive = false;
            return true;
        } else if (obj instanceof Player) {
            ((Player) obj).kill();
            return false;
        }
        return false;
    }
}
