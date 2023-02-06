package views;

import daos.NguoiDungDao;
import models.NguoiDung;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class DangNhap extends JFrame {
    private final NguoiDungDao nguoiDungDao = new NguoiDungDao();
    public JTextField emailField, passwordField;

    public DangNhap() {
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel titleLabel = new JLabel("Đăng nhập", SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);
        // Tạo panel chứa form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(2, 2));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        formPanel.add(emailLabel);
        formPanel.add(emailField);

        JLabel passwordLabel = new JLabel("Mật khẩu:");
        passwordField = new JPasswordField();
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        // Tạo panel chứa button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton loginButton = new JButton("Đăng nhập");
        JButton registerButton = new JButton("Đăng ký");
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        // Add vào frame
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
        setVisible(true);
        loginButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                kiemTraLogin();
            }
        });
        registerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new DangKy();
            }
        });
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    kiemTraLogin();
                    return true;
                }
                return false;
            }
        });
    }

    public void kiemTraLogin() {
        String email = emailField.getText();
        try {
            NguoiDung nguoiDung = nguoiDungDao.findByEmail(email);
            if (nguoiDung == null) {
                thongBao("Tài khoản không tồn tại");
            } else {
                if (nguoiDung.getMatKhau().equals(passwordField.getText())) {
                    dispose();
                    new QuanLy().showUI();
                } else {
                    thongBao("Sai mật khẩu");
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void thongBao(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

//    public static void main(String[] args) {
//		new DangNhap();
//	}
}
