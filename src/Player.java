public class Player extends Gameobject {
    public Player(int x, int y){
        super(x, y, '\u2615');
    }
    public void moveUp(){
        move(0, -1);
    }
    public void moveDown(){
        move(0, 1);
    }
    public void moveRight(){
        move(1, 0);
    }
    public void moveLeft(){
        move(-1, 0);

    }

    @Override
    public void onLoop() {
        super.onLoop();
    }
}
