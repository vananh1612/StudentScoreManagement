package views;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

interface FunctionHandel {
    void function();
}

class ButtonHandel {
    JButtonCustom jButton;
    FunctionHandel functionHandel;

    Color color;

    public ButtonHandel(JButtonCustom jButton, FunctionHandel functionHandel, Color color) {
        this.jButton = jButton;
        this.functionHandel = functionHandel;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public JButtonCustom getjButton() {
        return jButton;
    }

    public void setjButton(JButtonCustom jButton) {
        this.jButton = jButton;
    }

    public FunctionHandel getFunctionHandel() {
        return functionHandel;
    }

    public void setFunctionHandel(FunctionHandel functionHandel) {
        this.functionHandel = functionHandel;
    }
}

class JButtonCustom extends JButton {

    public JButtonCustom(String text) {
        super(text);
//        this.setPreferredSize(new Dimension(100, 3));
        this.setFont(new Font("Roboto", Font.PLAIN, 15));
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setBackground(new Color(65, 194, 203));
        this.setForeground(Color.WHITE);
        this.setBorder(null);
    }

}

class JTextFieldCustom extends JTextField {
    public JTextFieldCustom() {
        super();
        this.setFont(new Font("Roboto", Font.PLAIN, 15));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 5));
    }

}

class MenuModel {
    private JPanel jPanel;
    private JButtonCustom button;

    public MenuModel(JPanel jPanel, JButtonCustom button) {
        this.jPanel = jPanel;
        this.button = button;
    }

    public JPanel getjPanel() {
        return jPanel;
    }

    public void setjPanel(JPanel jPanel) {
        this.jPanel = jPanel;
    }

    public JButtonCustom getButton() {
        return button;
    }

    public void setButton(JButtonCustom button) {
        this.button = button;
    }
}

public class MenuDashBoard {
    public MenuDashBoard() {
        init();

    }

    public JPanel panelAccount() {
        JPanel jPanel = new JPanel();
        jPanel.add(new JLabel("Xin chào nguyễn Văn Nam"));
        return jPanel;
    }

    public void init() {
        JPanel quanLySinhVienView = viewQuanLySinhVien();
        JButtonCustom buttonSinhVien = new JButtonCustom("Quản lý sinh viên");
        JButtonCustom buttonLop = new JButtonCustom("Quản lý Lớp");
        JButtonCustom buttonKhoa = new JButtonCustom("Quản lý Khoa");
        JButtonCustom buttonNghanh = new JButtonCustom("Quản lý ngành");
        JButtonCustom buttonTruong = new JButtonCustom("Quản lý trường");
        MenuModel[] menuModels = new MenuModel[]{
                new MenuModel(quanLySinhVienView, buttonSinhVien),
                new MenuModel(viewQuanLyLop(), buttonLop),
                new MenuModel(viewQuanLyKhoa(), buttonKhoa),
                new MenuModel(viewQuanLyNganh(), buttonNghanh),
                new MenuModel(viewQuanLyTruong(), buttonTruong),
        };
        JFrame jFrame = new JFrame();
        jFrame.setSize(1200, 600);
        jFrame.setResizable(false);
        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new GridLayout(1, 1));
        // Left Panel is Menu Panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        leftPanel.setLayout(new GridLayout(0, 1, 20, 20));
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, quanLySinhVienView);
        for (MenuModel menuModel : menuModels) {
            menuModel.getButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    changeColor(menuModels, menuModel.getButton());
                    splitPane.setDividerLocation(200);
                    splitPane.setRightComponent(menuModel.getjPanel());
                }
            });
            leftPanel.add(menuModel.getButton());
        }
        splitPane.setDividerLocation(200);
        mainPanel.add(splitPane);
        jFrame.add(mainPanel);
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);

    }

    public void changeColor(MenuModel[] menuModels, JButtonCustom jButtonCustom) {
        jButtonCustom.setBackground(new Color(65, 194, 103));
        for (MenuModel menuModel : menuModels) {
            if (!(menuModel.getButton() == jButtonCustom)) {
                menuModel.getButton().setBackground(new Color(65, 194, 203));
            }

        }

    }

    public JPanel viewQuanLySinhVien() {
        Object oCreate = new Object();
        int id = 0;

        String lableName[] = new String[]{"Tên", "Tuổi", "Địa chỉ", "Lớp", "Điểm"};
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout());
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        JPanel leftPanel = new JPanel();
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        leftPanel.setLayout(new GridLayout(0, 1));
        for (int i = 0; i < lableName.length; i++) {
            JLabel jLabel = new JLabel(lableName[i]);
            JTextField jTextField = new JTextFieldCustom();
            leftPanel.add(jLabel);
            leftPanel.add(jTextField);
        }
        JLabel jLabel = new JLabel("Test JCombobox");
        JTextFieldCustom jTextFieldCustom = new JTextFieldCustom();
        leftPanel.add(jLabel);
        leftPanel.add(jTextFieldCustom);


        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout());
        JScrollPane scrollPane = new JScrollPane();
        JTable table = new JTable();
        String[] column = {"Id", "Id_sinhvien", "Id_monhoc", "Chuyên cần", "Bài tập", "Giữa kỳ",};
        DefaultTableModel model = new DefaultTableModel(column, 0);
        for (int i = 0; i < 3; i++) {
            model.addRow(column);
        }
        table.setModel(model);
        table.setRowHeight(30);
        scrollPane.setViewportView(table);
        JPanel panelAction = new JPanel();
        panelAction.setLayout(new GridLayout(1, 1, 10, 10));
        panelAction.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        ButtonHandel[] buttonHandels = new ButtonHandel[]{
                new ButtonHandel(new JButtonCustom("Thêm"), new FunctionHandel() {
                    @Override
                    public void function() {
                        JOptionPane.showMessageDialog(null, jTextFieldCustom.getText());
                        model.addRow(column);
                    }
                },new Color(25, 135, 84)),

                new ButtonHandel(new JButtonCustom("Sửa"), new FunctionHandel() {
                    @Override
                    public void function() {

                    }

                },new Color(0, 128, 128)),
                new ButtonHandel(new JButtonCustom("Xoá"), new FunctionHandel() {
                    @Override
                    public void function() {
                        model.removeRow(table.getSelectedRow());
                    }
                },new Color(255, 0, 0)),
                new ButtonHandel(new JButtonCustom("Làm mới"), new FunctionHandel() {
                    @Override
                    public void function() {
                    }
                },new Color(65, 194, 103)),
                new ButtonHandel(new JButtonCustom("Tìm Kiếm"), new FunctionHandel() {
                    @Override
                    public void function() {
                        JOptionPane.showMessageDialog(null, "Tìm Kiếm");
                    }
                },new Color(65, 194, 19))
        };

        for (ButtonHandel buttonHandel : buttonHandels) {
            buttonHandel.getjButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    buttonHandel.getFunctionHandel().function();
                }
            });
            buttonHandel.getjButton().setBackground(buttonHandel.getColor());
            panelAction.add(buttonHandel.getjButton());
        }
        JSplitPane splitPaneRight = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, panelAction);
        rightPanel.add(splitPaneRight);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(200);
        mainPanel.add(splitPane);
        return mainPanel;
    }

    public JPanel viewQuanLyKhoa() {
        String buttonName[] = new String[]{"Thêm", "Sửa 2", "Xoá", "Xem 2"};
        String lableName[] = new String[]{"Tên", "Tuổi 2", "Địa chỉ", "Lớp", "Điểm", "Điểm 2"};
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout());
        JPanel leftPanel = new JPanel();
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        leftPanel.setLayout(new GridLayout(0, 1));
        for (int i = 0; i < lableName.length; i++) {
            JLabel jLabel = new JLabel(lableName[i]);
            JTextField jTextField = new JTextFieldCustom();
            leftPanel.add(jLabel);
            leftPanel.add(jTextField);
        }
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout());
        JScrollPane scrollPane = new JScrollPane();
        JTable table = new JTable();
        String[] column = {"Id", "Id_sinhvien", "Id_monhoc", "Chuyên cần", "Bài tập", "Giữa kỳ",};
        DefaultTableModel model = new DefaultTableModel(column, 0);
        for (int i = 0; i < 100; i++) {
            model.addRow(column);
        }
        table.setModel(model);
        table.setRowHeight(30);
        scrollPane.setViewportView(table);
        JPanel panelAction = new JPanel();
        panelAction.setLayout(new GridLayout(1, 1, 10, 10));
        panelAction.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        for (int i = 0; i < buttonName.length; i++) {
            panelAction.add(new JButtonCustom(buttonName[i]));
        }
        JSplitPane splitPaneRight = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, panelAction);
        rightPanel.add(splitPaneRight);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(200);
        mainPanel.add(splitPane);
        return mainPanel;
    }

    public JPanel viewQuanLyNganh() {
        String buttonName[] = new String[]{"Thêm", "Sửa 2", "Xoá", "Xem 2"};
        String lableName[] = new String[]{"Tên", "Tuổi 2", "Địa chỉ", "Lớp", "Điểm", "Điểm 2"};
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout());
        JPanel leftPanel = new JPanel();
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        leftPanel.setLayout(new GridLayout(0, 1));
        for (int i = 0; i < lableName.length; i++) {
            JLabel jLabel = new JLabel(lableName[i]);
            JTextField jTextField = new JTextFieldCustom();
            leftPanel.add(jLabel);
            leftPanel.add(jTextField);
        }
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout());
        JScrollPane scrollPane = new JScrollPane();
        JTable table = new JTable();
        String[] column = {"Id", "Id_sinhvien", "Id_monhoc", "Chuyên cần", "Bài tập", "Giữa kỳ",};
        DefaultTableModel model = new DefaultTableModel(column, 0);
        for (int i = 0; i < 100; i++) {
            model.addRow(column);
        }
        table.setModel(model);
        table.setRowHeight(30);
        scrollPane.setViewportView(table);
        JPanel panelAction = new JPanel();
        panelAction.setLayout(new GridLayout(1, 1, 10, 10));
        panelAction.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        for (int i = 0; i < buttonName.length; i++) {
            panelAction.add(new JButtonCustom(buttonName[i]));
        }
        JSplitPane splitPaneRight = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, panelAction);
        rightPanel.add(splitPaneRight);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(200);
        mainPanel.add(splitPane);
        return mainPanel;
    }

    public JPanel viewQuanLyTruong() {
        String buttonName[] = new String[]{"Thêm", "Sửa 2", "Xoá", "Xem 2"};
        String lableName[] = new String[]{"Tên", "Tuổi 2", "Địa chỉ", "Lớp", "Điểm", "Điểm 2"};
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout());
        JPanel leftPanel = new JPanel();
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        leftPanel.setLayout(new GridLayout(0, 1));
        for (int i = 0; i < lableName.length; i++) {
            JLabel jLabel = new JLabel(lableName[i]);
            JTextField jTextField = new JTextFieldCustom();
            leftPanel.add(jLabel);
            leftPanel.add(jTextField);
        }
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout());
        JScrollPane scrollPane = new JScrollPane();
        JTable table = new JTable();
        String[] column = {"Id", "Id_sinhvien", "Id_monhoc", "Chuyên cần", "Bài tập", "Giữa kỳ",};
        DefaultTableModel model = new DefaultTableModel(column, 0);
        for (int i = 0; i < 100; i++) {
            model.addRow(column);
        }
        table.setModel(model);
        table.setRowHeight(30);
        scrollPane.setViewportView(table);
        JPanel panelAction = new JPanel();
        panelAction.setLayout(new GridLayout(1, 1, 10, 10));
        panelAction.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        for (int i = 0; i < buttonName.length; i++) {
            panelAction.add(new JButtonCustom(buttonName[i]));
        }
        JSplitPane splitPaneRight = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, panelAction);
        rightPanel.add(splitPaneRight);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(200);
        mainPanel.add(splitPane);
        return mainPanel;
    }

    public JPanel viewQuanLyLop() {
        String buttonName[] = new String[]{"Thêm", "Sửa 2", "Xoá", "Xem 2"};
        String lableName[] = new String[]{"Tên", "Tuổi 2", "Địa chỉ", "Lớp", "Điểm", "Điểm 2"};
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout());
        JPanel leftPanel = new JPanel();
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        leftPanel.setLayout(new GridLayout(0, 1));
        for (int i = 0; i < lableName.length; i++) {
            JLabel jLabel = new JLabel(lableName[i]);
            JTextField jTextField = new JTextFieldCustom();
            leftPanel.add(jLabel);
            leftPanel.add(jTextField);
        }
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout());
        JScrollPane scrollPane = new JScrollPane();
        JTable table = new JTable();
        String[] column = {"Id", "Id_sinhvien", "Id_monhoc", "Chuyên cần", "Bài tập", "Giữa kỳ",};
        DefaultTableModel model = new DefaultTableModel(column, 0);
        for (int i = 0; i < 100; i++) {
            model.addRow(column);
        }
        table.setModel(model);
        table.setRowHeight(30);
        scrollPane.setViewportView(table);
        JPanel panelAction = new JPanel();
        panelAction.setLayout(new GridLayout(1, 1, 10, 10));
        panelAction.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        for (int i = 0; i < buttonName.length; i++) {
            panelAction.add(new JButtonCustom(buttonName[i]));
        }
        JSplitPane splitPaneRight = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, panelAction);
        rightPanel.add(splitPaneRight);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(200);
        mainPanel.add(splitPane);
        return mainPanel;
    }

    public static void main(String[] args) {
        new MenuDashBoard();
    }


}
