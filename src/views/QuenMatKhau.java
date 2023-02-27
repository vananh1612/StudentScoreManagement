package views;

import daos.KPMatKhauDao;
import models.DataMail;
import models.KPMatKhau;
import models.ThongBao;
import models.ThongBaoData;
import utils.SendMail;
import views.components.JButtonCustom;

import javax.swing.*;
import java.awt.*;

public class QuenMatKhau extends JFrame {
    private KPMatKhauDao kpMatKhauDao = new KPMatKhauDao();

    public QuenMatKhau() {
        initComponents();
    }

    private void initComponents() {
        this.setSize(new Dimension(500, 500));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Quên mật khẩu");
        // Thiết lập kích thước và cài đặt khung giao diện
        setSize(500, 450);
        setLocationRelativeTo(null); // Để cửa sổ xuất hiện giữa màn hình
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Tạo và cài đặt thành phần giao diện
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 1, 10, 6));
        JPanel panelContent = new JPanel();
        panelContent.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        panelContent.setLayout(new GridLayout(0, 1));
        JLabel lbEmail = new JLabel("Email:");
        JTextFieldCustom tfEmail = new JTextFieldCustom();
        JButtonCustom btnSend = new JButtonCustom("Gửi");
        JSplitPane splitPaneEmail = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tfEmail, btnSend);
        splitPaneEmail.setBorder(null);
        splitPaneEmail.setResizeWeight(0.90);
        splitPaneEmail.setOneTouchExpandable(false);
        splitPaneEmail.setEnabled(false);
        panelContent.add(lbEmail);
        panelContent.add(splitPaneEmail);
        JLabel lbCode = new JLabel("Mã xác nhận:");
        JTextFieldCustom tfCode = new JTextFieldCustom();
        JLabel lbMatKhau = new JLabel("Mật khẩu:");
        JTextFieldCustom tfMatKhau = new JTextFieldCustom();
        JLabel lbXacNhanMatKhau = new JLabel("Xác nhận mật khẩu:");
        JTextFieldCustom tfXacNhanMatKhau = new JTextFieldCustom();
        panelContent.add(lbCode);
        panelContent.add(tfCode);
        panelContent.add(lbMatKhau);
        panelContent.add(tfMatKhau);
        panelContent.add(lbXacNhanMatKhau);
        panelContent.add(tfXacNhanMatKhau);
        JButtonCustom btnXacNhanMatKhau = new JButtonCustom("Xác nhận");
        JButtonCustom btnDangNhap = new JButtonCustom("Đăng nhập");
        JPanel panelButton = new JPanel();
        panelButton.setLayout(new GridLayout(1, 0, 10, 6));
        panelButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        panelButton.add(btnXacNhanMatKhau);
        panelButton.add(btnDangNhap);
        mainPanel.add(panelContent);
        mainPanel.add(panelButton);
        this.add(mainPanel);
        this.setVisible(true);
        panelButton.add(btnXacNhanMatKhau);
        panelButton.add(btnDangNhap);
        panelContent.add(new JLabel(""));
        panelContent.add(panelButton);
        mainPanel.add(panelContent);
        this.add(mainPanel);
        btnSend.addActionListener(e -> {
            if (validateEmail(tfEmail.getText())) {
                try {
                    ThongBaoData<DataMail> nguoiDungThongBao = kpMatKhauDao.createOrUpdate(new KPMatKhau(0, tfEmail.getText(), "0"));
                    if (nguoiDungThongBao.getKiemTra() == true) {
                        SendMail sendMail = new SendMail(nguoiDungThongBao.getData());
                        sendMail.start();
                        thongBao("Mã xác nhận đã được gửi đến email của bạn");
                    } else {
                        thongBao(nguoiDungThongBao);
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        btnXacNhanMatKhau.addActionListener(e -> {

            if (validateSubmit(tfCode.getText(), tfMatKhau.getText(), tfXacNhanMatKhau.getText())) {
                try {
                    ThongBao thongBao = kpMatKhauDao.updatePassword(tfEmail.getText(), tfMatKhau.getText(), tfCode.getText());
                    thongBao(thongBao);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void thongBao(ThongBao thongBao) {
        JOptionPane.showMessageDialog(this, thongBao.getTinNhan());
    }

    public boolean validateEmail(String email) {
        if (email.isEmpty()) {
            thongBao("Email không được để trống");
            return false;
        }
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if (!email.matches(regex)) {
            thongBao("Email không hợp lệ");
            return false;
        }
        return true;
    }

    public boolean validateSubmit(String code, String password, String confirmPassword) {
        if (code.isEmpty()) {
            thongBao("Mã xác nhận không được để trống");
            return false;
        }
        if (password.isEmpty()) {
            thongBao("Mật khẩu không được để trống");
            return false;
        }
        if (confirmPassword.isEmpty()) {
            thongBao("Xác nhận mật khẩu không được để trống");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            thongBao("Xác nhận mật khẩu không khớp");
            return false;
        }
        return true;
    }

    public void thongBao(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public static void main(String[] args) {
        new QuenMatKhau().setVisible(true);
    }
}
