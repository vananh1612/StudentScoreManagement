package views;

import models.Manager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuanLy {
    private final JFrame frame;
    private JPanel cardListPanel;

    public QuanLy() {
        frame = new JFrame();
        frame.setTitle("Manager Card List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    }

    public void showUI() {
        List<Manager> managers = new ArrayList<>();
        managers.add(new Manager("Quản lý Lớp", new Color(255, 255, 153), "path/to/image1.png"));
        managers.add(new Manager("Quản lý Sinh Viên", new Color(255, 204, 153), "path/to/image1.png"));
        managers.add(new Manager("Quản lý Học Kỳ", new Color(255, 153, 153), "path/to/image1.png"));
        managers.add(new Manager("Quản lý Môn Học", new Color(204, 153, 255), "path/to/image1.png"));
        managers.add(new Manager("Quản lý Điểm", new Color(153, 153, 255), "path/to/image1.png"));
        managers.add(new Manager("Quản lý Người Dùng", new Color(255, 153, 204), "path/to/image1.png"));
        managers.add(new Manager("Đăng Xuất", new Color(153, 204, 255), "path/to/image1.png"));

        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Quản lý sinh viên");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        frame.add(titlePanel, BorderLayout.NORTH);
        int numRows = (int) Math.ceil(managers.size() / 3.0);
        cardListPanel = new JPanel(new GridLayout(numRows, 3, 20, 20));
        cardListPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        cardListPanel.setBackground(Color.WHITE);
        for (Manager manager : managers) {
            JPanel cardPanel = new JPanel(new BorderLayout());
            cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            cardPanel.setBackground(manager.getColor());
            cardPanel.setPreferredSize(new Dimension(200, 100));

            JLabel imageLabel = new JLabel();
            try {
                BufferedImage image = ImageIO.read(new File(manager.getImagePath()));
                Image resizedImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(resizedImage));
            } catch (IOException e) {
            }

            JLabel nameLabel = new JLabel(manager.getName());
            nameLabel.setHorizontalAlignment(JLabel.CENTER);

            cardPanel.add(imageLabel, BorderLayout.NORTH);
            cardPanel.add(nameLabel, BorderLayout.CENTER);
            cardPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    openView(manager.getName());
                }
            });
            cardListPanel.add(cardPanel);
        }

        frame.add(cardListPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }

    public void openView(String name) {
        switch (name) {
            case "Quản lý Lớp":
                new QuanLyLop();
                break;
            case "Quản lý Sinh Viên":
                new QuanLySinhVien();
                break;
            case "Quản lý Học Kỳ":
                new QuanLyHocKy();
                break;
            case "Quản lý Môn Học":
                new QuanLyMonHoc();
                break;
            case "Quản lý Điểm":
                new QuanLyDiem();
                break;
            case "Quản lý Người Dùng":
                new QuanLyNguoiDung();
                break;
            case "Đăng Xuất":
                frame.dispose();
                new DangNhap();
                break;
            default:
                break;
        }
    }

//    public static void main(String[] args) {
//        QuanLy managerCardList = new QuanLy();
//        managerCardList.showUI();
//    }
}
