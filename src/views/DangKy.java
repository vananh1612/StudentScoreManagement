package views;

import daos.NguoiDungDao;
import models.NguoiDung;
import models.ThongBao;
import views.components.JPasswordFieldCustom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DangKy extends JFrame {
    private final NguoiDungDao nguoiDungDao = new NguoiDungDao();

    public DangKy() {
        setSize(400, 530);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel titleLabel = new JLabel("Đăng ký", SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);
        // Tạo panel chứa form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel emailLabel = new JLabel("Email:");
        JTextFieldCustom txtEmail = new JTextFieldCustom();
        formPanel.add(emailLabel);
        formPanel.add(txtEmail);

        JLabel passwordLabel = new JLabel("Mật khẩu:");
        JPasswordFieldCustom txtMatKhau = new JPasswordFieldCustom();
        formPanel.add(passwordLabel);
        formPanel.add(txtMatKhau);

        JLabel confirmPasswordLabel = new JLabel("Xác nhận mật khẩu:");
        JPasswordFieldCustom txtMatKhauXacNhan = new JPasswordFieldCustom();
        formPanel.add(confirmPasswordLabel);
        formPanel.add(txtMatKhauXacNhan);

        JLabel nameLabel = new JLabel("Họ tên:");
        JTextFieldCustom txtTen = new JTextFieldCustom();
        formPanel.add(nameLabel);
        formPanel.add(txtTen);

        // Tạo panel chứa button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 1,8,8));
        JButtonCustom registerButton = new JButtonCustom("Đăng ký");
        JButtonCustom backButton = new JButtonCustom("Quay lại");
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);
        formPanel.add(new JLabel(""));
        formPanel.add(buttonPanel);
        // Add vào frame
        add(formPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
        registerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Code xử lý đăng ký
                NguoiDung nguoiDung = new NguoiDung();
                nguoiDung.setTen(txtTen.getText());
                nguoiDung.setEmail(txtEmail.getText());
                nguoiDung.setMatKhau(txtMatKhau.getText());
                nguoiDung.setVaiTro("user");
                if (kiemTraLoi(nguoiDung, txtMatKhauXacNhan.getText())) {
                    try {
                        ThongBao thongBao = nguoiDungDao.create(nguoiDung);
                        thongBaoKiemTra(thongBao);

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

            }
        });
        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new DangNhap();
            }
        });
    }

    public boolean checkEmail(String email) {
        String rex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
        return email.matches(rex);
    }

    public void thongBao(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void thongBaoKiemTra(ThongBao thongBao) {

        if (thongBao.getKiemTra()) {
            thongBao(thongBao.getTinNhan());
            dispose();
            new DangNhap().setVisible(true);
        } else {
            thongBao(thongBao.getTinNhan());
        }

    }

    public boolean kiemTraLoi(NguoiDung nguoiDung, String confirmPass) {

        if (!checkEmail(nguoiDung.getEmail())) {
            thongBao("Lỗi sai định dạng ");
            return false;
        }
        if (nguoiDung.getMatKhau().length() == 0) {
            thongBao("Mật khẩu trống");
            return false;
        }
        if (!nguoiDung.getMatKhau().equals(confirmPass)) {
            thongBao("Vui lòng xác nhận lại mật khẩu");
            return false;
        }
        if (nguoiDung.getTen().length() == 0) {
            thongBao("Tên người dùng trống");
            return false;
        }
        return true;
    }

	public static void main(String[] args) {
		new DangKy();
	}
}
