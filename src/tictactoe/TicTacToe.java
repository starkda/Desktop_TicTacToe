package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * GameStates represents all possible states the game can be
 */

enum GameStates {
    NOT_STARTED("Game is not started"), X_WIN("X wins"), O_WIN("O wins"), DRAW("Draw"),
    X_IN_PROGRESS("X"), O_IN_PROGRESS("O"), IN_PROGRESS("Game is in progress");
    String status = "";

    GameStates(String status) {
        this.status = status;
    }
}

/**
 * Turn represents whom turn is now
 */
enum Turn {
    FIRST, SECOND;
}

/**
 * One of the main classes of the game, It stores the game itself
 */
class Game extends JPanel {

    static Turn turn = Turn.FIRST;
    StateField currentState;
    Board[] buttons = new Board[9];
    StartResetButton startResetButton = new StartResetButton();

    JButton player1Button = new PlayerButton("ButtonPlayer1");
    JButton player2Button = new PlayerButton("ButtonPlayer2");

    Game() {
        super();
        currentState = new StateField();
        setLayout(new GridLayout(5, 3));
        add(player1Button);
        add(startResetButton);
        add(player2Button);
        initElements();

    }

    /**
     * When the game starts, the function is called to make all buttons clickable
     */
    private void initElements() {
        int current = 0;
        for (String i : new String[]{"3", "2", "1"}) {
            for (String j : new String[]{"A", "B", "C"}) {
                buttons[current] = new Board(j + i);
                add(buttons[current]);
                current += 1;
            }
        }
        currentState = new StateField();
        add(currentState);
        add(new JPanel());
    }

    /**
     * Function used to simulate Robot player, it is called automatically, if current turn is Robot's
     */
    void performRobotAction() {
        for (int i = 0; i < 9; i++) { // Robot chooses first clear tile and touches it.
            if (buttons[i].getText().equals(" ")) {
                buttons[i].actionPerformed(new ActionEvent(1, 1, "Robot taught"));
                break;
            }
        }
    }

    /**
     * Check whether current game state is winning for target playes
     *
     * @param type type of the player(X or O)
     */
    private boolean win(char type) {
        String cmp = Character.toString(type);
        boolean ok = false;
        if (buttons[0].getText().equals(cmp) && buttons[3].getText().equals(cmp) && buttons[6].getText().equals(cmp)) {
            ok = true;
        }
        if (buttons[1].getText().equals(cmp) && buttons[4].getText().equals(cmp) && buttons[7].getText().equals(cmp)) {
            ok = true;
        }
        if (buttons[2].getText().equals(cmp) && buttons[5].getText().equals(cmp) && buttons[8].getText().equals(cmp)) {
            ok = true;
        }
        if (buttons[0].getText().equals(cmp) && buttons[1].getText().equals(cmp) && buttons[2].getText().equals(cmp)) {
            ok = true;
        }
        if (buttons[3].getText().equals(cmp) && buttons[4].getText().equals(cmp) && buttons[5].getText().equals(cmp)) {
            ok = true;
        }
        if (buttons[6].getText().equals(cmp) && buttons[7].getText().equals(cmp) && buttons[8].getText().equals(cmp)) {
            ok = true;
        }
        if (buttons[0].getText().equals(cmp) && buttons[4].getText().equals(cmp) && buttons[8].getText().equals(cmp)) {
            ok = true;
        }
        if (buttons[2].getText().equals(cmp) && buttons[4].getText().equals(cmp) && buttons[6].getText().equals(cmp)) {
            ok = true;
        }
        return ok;
    }

    /**
     * Check whether current game state is drawn
     */
    private boolean draw() {
        boolean ok = true;
        for (int i = 0; i < 9; i++) {
            if (buttons[i].getText().equals(" ")) ok = false;
        }
        return ok;
    }


    /**
     * Class defines start/reset button
     */
    class StartResetButton extends JButton implements ActionListener {
        StartResetButton() {
            super();
            setText("Start");
            setName("ButtonStartReset");
            addActionListener(this);
        }

        /**
         * When the button is taught, following function is called
         * Depending on the current game state(started or not started) button changes Game's state
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentState.currentState != GameStates.NOT_STARTED) {
                turn = Turn.FIRST;
                for (int i = 0; i < 9; i++) {
                    buttons[i].setEnabled(false);
                }
                player1Button.setEnabled(true);
                player2Button.setEnabled(true);
                startResetButton.setText("Start");
                currentState.setCurrentState(GameStates.NOT_STARTED);
                for (Board currentButton : buttons) {
                    currentButton.setText(" ");
                }
            } else {
                turn = Turn.FIRST;
                for (int i = 0; i < 9; i++) {
                    buttons[i].setEnabled(true);
                }
                player1Button.setEnabled(false);
                player2Button.setEnabled(false);
                startResetButton.setText("Reset");
                currentState.updateState();
                System.out.println(player1Button.getText());
                if (player1Button.getText().equals("Robot")) {
                    performRobotAction();
                }
            }
        }
    }

    /**
     * Method that is passed as a reference into GameBar class.
     * Purpose of that method is to reset the entire game and automatically start it based on which
     * type of game user prefers.
     *
     * @param nameX whether first player is robot or human
     * @param nameO whether second player is robot or human
     */
    void ResetGame(String nameX, String nameO) {
        turn = Turn.FIRST;
        if ((nameX.equals("Human") || nameX.equals("Robot")) && (nameO.equals("Human") || nameO.equals("Robot"))) {
            for (Board currentButton : buttons) {
                currentButton.setText(" ");
            }
            for (int i = 0; i < 9; i++) {
                buttons[i].setEnabled(true);
            }
            player1Button.setEnabled(false);
            player2Button.setEnabled(false);
            startResetButton.setText("Reset");
            currentState.setCurrentState(GameStates.X_IN_PROGRESS);
            player1Button.setText(nameX);
            player2Button.setText(nameO);
            currentState.updateState();
            if (player1Button.getText().equals("Robot")) {
                performRobotAction();
            }
        }
    }

    /**
     * Class that represents Button to chose whom vs whom will play. Besides, it lets undertand current game state
     */
    class PlayerButton extends JButton implements ActionListener {
        PlayerButton(String name) {
            super();
            setName(name);
            setText("Human");
            addActionListener(this);
        }


        /**
         * when the player button was taught, it should change its state(Robot to player, or Player to Robot)
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentState.currentState == GameStates.NOT_STARTED) {
                if (getText().equals("Human")) setText("Robot");
                else setText("Human");
            }
        }
    }

    /**
     * Class that shows to the player what is a current game state
     */
    private class StateField extends JLabel {
        GameStates currentState;
        String LabelStatus;

        StateField() {
            super();
            currentState = GameStates.NOT_STARTED;
            setText(GameStates.NOT_STARTED.status);
            setName("LabelStatus");
        }

        /**
         * When we need to update the current state, following method is called. If the state is not terminal, than it
         * just changes turns.
         */
        void updateState() {
            if (win('X')) {
                currentState = GameStates.X_WIN;
                currentState.status = "The " + player1Button.getText() + " Player (X) wins";
            } else if (win('O')) {
                currentState = GameStates.O_WIN;
                currentState.status = "The " + player2Button.getText() + " Player (O) wins";
            } else if (draw()) {
                currentState = GameStates.DRAW;
            } else {
                currentState = GameStates.IN_PROGRESS;
                System.out.println(turn);
                if (turn == Turn.FIRST) {
                    currentState.status = "The turn of " + player1Button.getText() + " Player" + " (X)";
                } else {
                    currentState.status = "The turn of " + player2Button.getText() + " Player" + " (O)";
                }
            }
            setText(currentState.status);
        }

        /**
         * That method lets us set the state we want.
         */
        void setCurrentState(GameStates newState) {
            currentState = newState;
            setText(currentState.status);
            Game.turn = Turn.FIRST;
        }
    }

    /**
     * Class that represents clickable tile(Basically heart of the game)
     */
    class Board extends JButton implements ActionListener {
        Board(String name) {
            super();
            setFocusPainted(false);
            setName("Button" + name);
            setText(" ");
            addActionListener(this);
            setEnabled(false);
        }

        /**
         * When the tile was taught, we need to ensure that the game has started and the tile is clear.
         * Also, if the next move performed by robot, we make robot action.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentState.currentState == GameStates.NOT_STARTED) {
                return;
            }
            if (this.getText().equals(" ") && (Game.this.currentState.currentState == GameStates.NOT_STARTED || Game.this.currentState.currentState == GameStates.IN_PROGRESS)) {
                if (turn == Turn.FIRST) {
                    setText("X");
                } else {
                    setText("O");
                }
                if (turn == Turn.FIRST) turn = Turn.SECOND;
                else turn = Turn.FIRST;
                Game.this.currentState.updateState();
                if (turn == Turn.SECOND && player2Button.getText().equals("Robot")) {
                    Game.this.performRobotAction();
                }
                if (turn == Turn.FIRST && player1Button.getText().equals("Robot")) {
                    Game.this.performRobotAction();
                }
            }
        }
    }

}


public class TicTacToe extends JFrame {
    public TicTacToe() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setResizable(false);
        setSize(450, 450);
        Game game = new Game();
        add(game);
        setJMenuBar(new GameBar(game::ResetGame));
        setVisible(true);
    }
}