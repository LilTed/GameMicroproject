import com.googlecode.lanterna.*;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;

import java.nio.charset.Charset;
import java.util.Random;

public class Game {

    private Terminal terminal;
    private boolean running;

    private int score;
    private int highScore;

    Gameobject[] gameobject;
    Player player;

    public void run() {
        initWindow();
        initGame();
        while (running) {
            loop();
        }
        exit();
    }

    private void initWindow() {
        terminal = TerminalFacade.createTerminal(System.in,
                System.out, Charset.forName("UTF8"));

        terminal.enterPrivateMode();
        terminal.setCursorVisible(false); //Gör pekaren osynlig.
        running = true;
    }

    private void initGame() {
        score = 0;
        Random rand = new Random();
        gameobject = new Gameobject[rand.nextInt(4) + 2]; //Skapar player och monster
        player = new Player(20, 20);
        for (int i = 0; i < gameobject.length; i++) {
            gameobject[i] = new Monster(rand.nextInt(terminal.getTerminalSize().getColumns()),
                    rand.nextInt(terminal.getTerminalSize().getRows()));
        }
        onScreen();
    }

    private void exit() {
        terminal.exitPrivateMode();
    }

    private void loop() {
        onLoop();
        handleInput();
        checkCollision();
        onScreen();
    }

    private void onLoop() {
        player.onLoop();
        for (int i = 0; i < gameobject.length; i++) {
            gameobject[i].onLoop(player.getX(), player.getY());
        }
    }

    private void handleInput() {
        Key key;
        do {
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {

            }
            key = terminal.readInput();
        }
        while (key == null);

        switch (key.getKind()) {
            case ArrowDown:
                score++;
                player.moveDown();
                break;
            case ArrowUp:
                score++;
                player.moveUp();
                break;
            case ArrowLeft:
                score++;
                player.moveLeft();
                break;
            case ArrowRight:
                score++;
                player.moveRight();
                break;
        }

        while (key != null) {
            key = terminal.readInput();
        }
        //System.out.println(key.getCharacter() + " " + key.getKind());
    }

    private void checkCollision() {
        if (player.getX() < 0 || player.getY() < 0 || player.getX() >= terminal.getTerminalSize().getColumns() || player.getY() >= terminal.getTerminalSize().getRows()) {
            gameOver();
        } else {
            for (int j = 1; j < gameobject.length; j++) {
                if (Math.abs(player.getX() - gameobject[j].getX()) <= 1 &&
                        Math.abs(player.getY() - gameobject[j].getY()) <= 1) {
                    gameOver();
                }
            }
        }
    }

    private void onScreen() {
        terminal.clearScreen();

        terminal.moveCursor(player.getX(), player.getY());
        terminal.putCharacter(player.getGraphics());

        for (Gameobject g : gameobject) {
            terminal.moveCursor(g.getX(), g.getY());
            terminal.putCharacter(g.getGraphics());
        }
    }

    private void gameOver() {
        terminal.clearScreen();
        printText(45, 15, "Game over!");
        printText(45, 16, "Your score is: " + Integer.toString(score));
        printText(45,18,"Press y to play again.");

        if (score>highScore) {
            highScore=score;
            printText(45,17,"Your scored a new high score: " + highScore);
        }
        else {
            printText(45,17,"Your high score this session: " + highScore);
        }

        Key key;
        do {
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {

            }
            key = terminal.readInput();
        }
        while (key == null);

        switch (key.getCharacter()) {
            case 'y':
                initGame();
                break;
            default:
                running = false;
                break;
        }

        while (key != null) {
            key = terminal.readInput();
        }
    }

    private void printText(int x, int y, String message) {
        for (int i = 0; i < message.length(); i++) {
            terminal.moveCursor(x, y);
            terminal.putCharacter(message.charAt(i));
            x = x + 1;
        }
    }
}