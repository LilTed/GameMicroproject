public class Monster extends Gameobject {

    public Monster(int x, int y) {
        super(x, y, 'M');
    }

    public void onLoop(int playerX, int playerY) {
        moveTowardsPlayer(playerX, playerY);
    }

    private void moveTowardsPlayer(int targetX, int targetY) {
        if (Math.abs(targetX - getX()) > Math.abs(targetY - getY())) {
            if (targetX < getX()) {
                move(-1, 0);
            } else if (targetX > getX()) {
                move(1, 0);
            }
        } else {
            if (targetY < getY()) {
                move(0, -1);
            } else if (targetY > getY()) {
                move(0, 1);
            }
        }
    }
}
