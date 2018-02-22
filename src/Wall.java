public class Wall extends Gameobject {

    public Wall(int x, int y) {
        super(x, y, '*);
    }
    /*
    Wall[][] wall = new Wall[30][30];

    for (int u =0; u<30; u++){
        wall[u][0] = new Wall('*');
    }
     for (int u =0; u<30; u++){
        wall[0][u] =new Wall('*');
    }
     for (int u =0; u<30; u++){
        wall[wall.length - 1][u] = new Wall('*');
    }
    for (int u =0; u<30 ; u++){
        wall[u][wall.length-1] = new Wall('*');
    }
    for(int x = 0; x<30; x++){
        for (int y =0; y<30;y++){
            System.out.println(wall[x][y]);
        }
    }
    */

}
