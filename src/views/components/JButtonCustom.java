package views.components;

import javax.swing.*;
import java.awt.*;

public class JButtonCustom extends JButton {

    public JButtonCustom(String text) {
        super(text);
        this.setPreferredSize(new Dimension(100, 3));
        this.setFont(new Font("Roboto", Font.PLAIN, 15));
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setBackground(new Color(65, 194, 203));
        this.setForeground(Color.WHITE);
        this.setBorder(null);
    }

}
