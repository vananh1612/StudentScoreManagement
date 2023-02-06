package views.components;

import javax.swing.*;
import java.awt.*;

public class JComboBoxCustom<T> extends JComboBox<T> {

    public JComboBoxCustom() {
        super();
        this.setFont(new Font("Roboto", Font.PLAIN, 15));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 5));
        this.setBorder(null);
    }

}
