package victor.training.java.java17;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class DisplayShape {

    public static void display(Consumer<Graphics> drawFunction) {
        class MyPanel extends JPanel {
            public Dimension getPreferredSize() {
                return new Dimension(250,200);
            }
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                // TODO draw here
               drawFunction.accept(g);
            }
        }
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Demo");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.add(new MyPanel());
            f.pack();
            f.setLocationByPlatform(true);
            f.setVisible(true);
        });
    }
}

