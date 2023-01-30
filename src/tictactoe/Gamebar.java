package tictactoe;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class used to represent menu on the top.
 * It lets us choose game type faster.
 */
class GameBar extends JMenuBar {
    @FunctionalInterface
    interface GameResetter {
        void reset(String a, String b);
    }

    GameResetter resetter;

    /**
     * Initialization stage, by using resetter method, we create 4 different game types.
     * @param resetter method reference to Game instance, to change its state
     */
    GameBar(GameResetter resetter) {
        super();
        this.resetter = resetter;
        JMenu game = new JMenu("Game");
        game.setName("MenuGame");
        game.add(new ResetMenuItem("Human vs Human", "Human", "Human"));
        game.add(new ResetMenuItem("Human vs Robot", "Human", "Robot"));
        game.add(new ResetMenuItem("Robot vs Human", "Robot", "Human"));
        game.add(new ResetMenuItem("Robot vs Robot", "Robot", "Robot"));
        game.addSeparator();
        JMenuItem exit = new JMenuItem("Exit");
        exit.setName("MenuExit");
        exit.addActionListener(e -> System.exit(0));
        game.add(exit);
        add(game);
    }

    /**
     * Class used to represent menu items, that are shown after clicking on menu
     */
    class ResetMenuItem extends JMenuItem implements ActionListener {
        String resetX;
        String resetO;

        ResetMenuItem(String name, String resetX, String resetO) {
            super(name);
            this.resetX = resetX;
            this.resetO = resetO;
            setName("Menu" + resetX + resetO);
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            GameBar.this.resetter.reset(resetX, resetO);
        }
    }
}