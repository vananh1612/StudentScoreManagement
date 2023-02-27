package views.components;

import javax.swing.*;
import java.awt.*;

public class JPasswordFieldCustom extends JPasswordField {
    public JPasswordFieldCustom() {
        super();
        this.setFont(new Font("Roboto", Font.PLAIN, 15));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 5));
    }
}
