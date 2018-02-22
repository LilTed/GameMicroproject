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

    private Monster[] monsters;
    private Player player;

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
        monsters = new Monster[rand.nextInt(4) + 2];
        player = new Player(20, 20);
        for (int i = 0; i < monsters.length; i++) {
            monsters[i] = new Monster(rand.nextInt(terminal.getTerminalSize().getColumns()),
                    rand.nextInt(terminal.getTerminalSize().getRows()));
        }
        onScreen();
    }

    private void exit() {
        terminal.exitPrivateMode();
    }

    private void loop() {
        handleInput();
        onLoop();
        checkCollision();
        onScreen();
    }

    private void onLoop() {
        player.onLoop();
        for (int i = 0; i < monsters.length; i++) {
            monsters[i].onLoop(player.getX(), player.getY());
        }
    }

    private void handleInput() {
        Key key;
        boolean badInput = true;
        while (badInput) {
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
                    badInput = false;
                    player.moveDown();
                    break;
                case ArrowUp:
                    score++;
                    badInput = false;
                    player.moveUp();
                    break;
                case ArrowLeft:
                    score++;
                    badInput = false;
                    player.moveLeft();
                    break;
                case ArrowRight:
                    score++;
                    badInput = false;
                    player.moveRight();
                    break;
            }

            while (key != null) {
                key = terminal.readInput();
            }
        }
    }

    private void checkCollision() {
        if (player.getX() < 0 || player.getY() < 0 || player.getX() >= terminal.getTerminalSize().getColumns() || player.getY() >= terminal.getTerminalSize().getRows()) {
            gameOver();
        } else {
            for (int j = 1; j < monsters.length; j++) {
                if (Math.abs(player.getX() - monsters[j].getX()) <= 1 &&
                        Math.abs(player.getY() - monsters[j].getY()) <= 1) {
                    gameOver();
                }
            }
        }
    }

    private void onScreen() {
        terminal.clearScreen();

        terminal.moveCursor(player.getX(), player.getY());
        terminal.putCharacter(player.getGraphics());

        for (Monster m : monsters) {
            terminal.moveCursor(m.getX(), m.getY());
            terminal.putCharacter(m.getGraphics());
        }
    }

    private void gameOver() {
        terminal.clearScreen();
        printText(45, 15, "Game over!");
        printText(45, 16, "Your score is: " + Integer.toString(score));
        printText(45, 18, "Press 'y' to play again and press 'n' to quit");

        if (score > highScore) {
            highScore = score;
            printText(45, 17, "Your scored a new high score: " + highScore);
        } else {
            printText(45, 17, "Your high score this session: " + highScore);
        }

        Key key;
        boolean badInput = true;
        while (badInput) {
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
                    badInput = false;
                    break;
                case 'n':
                    running = false;
                    badInput = false;
                    break;
            }

            while (key != null) {
                key = terminal.readInput();
            }
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
