package AttendanceRegister;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame{
    public Main() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(new GUI());
        setDefaultCloseOperation(3);
        setMinimumSize(new Dimension(920,700));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}

