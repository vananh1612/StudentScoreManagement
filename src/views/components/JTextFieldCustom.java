package views.components;

import javax.swing.*;
import java.awt.*;

public class JTextFieldCustom extends JTextField {
    public JTextFieldCustom() {
        super();
        this.setFont(new Font("Roboto", Font.PLAIN, 15));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 5));
    }

}
