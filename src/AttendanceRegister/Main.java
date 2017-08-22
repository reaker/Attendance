package AttendanceRegister;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private Main() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(new GUI());
        setMinimumSize(new Dimension(750, 600));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}

