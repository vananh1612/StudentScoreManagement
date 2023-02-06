package views;

import daos.NguoiDungDao;
import models.NguoiDung;
import models.ThongBao;
import views.components.JButtonCustom;
import views.components.JTextFieldCustom;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class QuanLyNguoiDung extends JFrame {
    String[] column = {"Id", "Tên người dùng  ", "Email", "Mật khẩu"};
    private final DefaultTableModel modelNguoiDung = new DefaultTableModel(column, 0);
    private final NguoiDungDao nguoiDungDao = new NguoiDungDao();

    public void getDataToModel() {

        try {
            ArrayList<NguoiDung> nguoiDungs = new ArrayList<>();
            nguoiDungs = nguoiDungDao.findAll();
            modelNguoiDung.setRowCount(0);
            for (NguoiDung nguoiDung : nguoiDungs) {
                Object[] row = {nguoiDung.getId(), nguoiDung.getTen(), nguoiDung.getEmail(), nguoiDung.getMatKhau()};
                modelNguoiDung.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getDataToModelSearch(String search) {

        try {
            ArrayList<NguoiDung> nguoiDungs = new ArrayList<>();
            nguoiDungs = nguoiDungDao.search(search);
            modelNguoiDung.setRowCount(0);
            for (NguoiDung nguoiDung : nguoiDungs) {
                Object[] row = {nguoiDung.getId(), nguoiDung.getTen(), nguoiDung.getEmail(), nguoiDung.getMatKhau()};
                modelNguoiDung.addRow(row);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public QuanLyNguoiDung() {

        this.setTitle("Quản lý người dùng");
        this.setSize(1200, 630);
        JPanel layoutLeft = new JPanel();
        // background màu trắng
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        layoutLeft.setLayout(new GridLayout(0, 1));
        layoutLeft.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextFieldCustom txtId = new JTextFieldCustom();
        txtId.disable();
        JTextFieldCustom txtTen = new JTextFieldCustom();
        JTextFieldCustom txtEmail = new JTextFieldCustom();
        JTextFieldCustom txtMatKhau = new JTextFieldCustom();

        layoutLeft.add(new JLabel("Id"));
        layoutLeft.add(txtId);
        layoutLeft.add(new JLabel("Tên người dùng"));
        layoutLeft.add(txtTen);
        layoutLeft.add(new JLabel("Email"));
        layoutLeft.add(txtEmail);
        layoutLeft.add(new JLabel("Mật khẩu"));
        layoutLeft.add(txtMatKhau);

        JPanel layoutRight = new JPanel();
        layoutRight.setLayout(new GridLayout(1, 1));
        layoutRight.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane();
        JTable table = new JTable();
        table.setRowHeight(40);

        String[] column = {"Id", "Tên người dùng", "Email", "Mật khẩu"};
        this.getDataToModel();
        table.setModel(modelNguoiDung);
        scrollPane.setViewportView(table);
        layoutRight.add(scrollPane);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, layoutLeft, layoutRight);
        splitPane.setDividerLocation(400);
        splitPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // 1 vạch chia 2 layout bottom chứa 4 button thêm, sửa, xóa, thoát
        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(1, 2, 10, 10));
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel bottomLeft = new JPanel();
        // bottomLeft chứa 1 textfield và 1 button tìm kiếm
        bottomLeft.setLayout(new GridLayout(1, 3, 10, 10));
        JTextFieldCustom txtSearch = new JTextFieldCustom();
        JButtonCustom btnSearch = new JButtonCustom("Tìm kiếm");
        bottomLeft.add(new JLabel("Tìm kiếm: "));
        bottomLeft.add(txtSearch);
        bottomLeft.add(btnSearch);
        btnSearch.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String message = txtSearch.getText();
                if (message.length() == 0) {
                    getDataToModel();
                } else {
                    getDataToModelSearch(message);
                }

            }
        });
        JPanel bottomRight = new JPanel();
        bottomRight.setLayout(new GridLayout(1, 4, 10, 10));

        JButtonCustom btnThem = new JButtonCustom("Thêm");
        // color màu xanh lá cây
        btnThem.setBackground(new Color(25, 135, 84));
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NguoiDung nguoiDung = new NguoiDung();
                nguoiDung.setTen(txtTen.getText());
                nguoiDung.setEmail(txtEmail.getText());
                nguoiDung.setMatKhau(txtMatKhau.getText());

                if (kiemTraLoi(nguoiDung)) {
                    try {
                        ThongBao thongBao = nguoiDungDao.create(nguoiDung);
                        thongBaoKiemTra(thongBao);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        JButtonCustom btnSua = new JButtonCustom("Sửa");
        btnSua.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                NguoiDung nguoiDung = new NguoiDung();
                String id = txtId.getText();
                nguoiDung.setTen(txtTen.getText());
                nguoiDung.setEmail(txtEmail.getText());
                nguoiDung.setMatKhau(txtMatKhau.getText());

                if (kiemTraLoi(nguoiDung)) {
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn cập nhật?", "Cảnh báo", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        try {
                            ThongBao thongBao = nguoiDungDao.update(Integer.parseInt(id), nguoiDung);
                            thongBaoKiemTra(thongBao);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        // color màu xanh xanh
        btnSua.setBackground(new Color(0, 128, 128));
        JButtonCustom btnXoa = new JButtonCustom("Xóa");
        btnXoa.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String id = txtId.getText();
                if (id.length() == 0) {
                    thongBao("Lỗi");
                } else {
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa?", "Cảnh báo", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        try {
                            ThongBao thongBao = nguoiDungDao.delete(Integer.parseInt(txtId.getText()));
                            thongBaoKiemTra(thongBao);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }

                }

            }
        });
        // color màu đỏ
        btnXoa.setBackground(new Color(255, 0, 0));
        JButtonCustom btnThoat = new JButtonCustom("Thoát");
        btnThoat.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
//				 System.exit(0);
                dispose();
            }
        });
        btnThoat.setBackground(new Color(65, 194, 203));
        bottomRight.add(btnThem);
        bottomRight.add(btnSua);
        bottomRight.add(btnXoa);
        bottomRight.add(btnThoat);
        bottom.add(bottomLeft);
        bottom.add(bottomRight);
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane, bottom);
        splitPane2.setDividerLocation(500);
        splitPane2.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.add(splitPane2);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int row = table.getSelectedRow();
                int id = (int) model.getValueAt(row, 0);
                txtId.setText(String.valueOf(id));
                try {
                    NguoiDung nguoiDung = nguoiDungDao.findOne(id);
                    txtTen.setText(String.valueOf(nguoiDung.getTen()));
                    txtEmail.setText(String.valueOf(nguoiDung.getEmail()));
                    txtMatKhau.setText(String.valueOf(nguoiDung.getMatKhau()));

                } catch (Exception e1) {
                    e1.printStackTrace();
                }


            }
        });
    }

    public void thongBao(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void thongBaoKiemTra(ThongBao thongBao) {

        if (thongBao.getKiemTra()) {
            getDataToModel();
            thongBao(thongBao.getTinNhan());
        } else {
            thongBao(thongBao.getTinNhan());
        }

    }

    public boolean checkEmail(String email) {
        String rex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
        return email.matches(rex);
    }

    public boolean kiemTraLoi(NguoiDung nguoiDung) {

        if (!checkEmail(nguoiDung.getEmail())) {
            thongBao("Lỗi sai định dạng ");
            return false;
        }
        if (nguoiDung.getMatKhau().length() == 0) {
            thongBao("Mật khẩu trống");
            return false;
        }
        if (nguoiDung.getTen().length() == 0) {
            thongBao("Tên người dùng trống");
            return false;
        }
        return true;
    }

//	public static void main(String[] args) {
//
//		QuanLyNguoiDung quanLyNguoiDung = new QuanLyNguoiDung();
//
//	}

}
