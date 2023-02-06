package views;

import daos.MonHocDao;
import models.MonHoc;
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

public class QuanLyMonHoc extends JFrame {
    String[] column = {"Id", "Tên môn học ", "Tên giảng viên"};
    private final DefaultTableModel modelMonHoc = new DefaultTableModel(column, 0);
    private final MonHocDao monHocDao = new MonHocDao();

    public void getDataToModel() {

        try {
            ArrayList<MonHoc> monHocs = new ArrayList<>();
            monHocs = monHocDao.findAll();
            modelMonHoc.setRowCount(0);
            for (MonHoc monHoc : monHocs) {
                Object[] row = {monHoc.getId(), monHoc.getTen(), monHoc.getTenGiangVien()};
                modelMonHoc.addRow(row);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void getDataToModelSearch(String search) {

        try {
            ArrayList<MonHoc> monHocs = new ArrayList<>();
            monHocs = monHocDao.search(search);
            modelMonHoc.setRowCount(0);
            for (MonHoc monHoc : monHocs) {
                Object[] row = {monHoc.getId(), monHoc.getTen(), monHoc.getTenGiangVien()};
                modelMonHoc.addRow(row);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public QuanLyMonHoc() {
        this.getDataToModel();
        this.setTitle("Quản lý môn học");
        this.setSize(1200, 630);
        JPanel layoutLeft = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        layoutLeft.setLayout(new GridLayout(0, 1));
        layoutLeft.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextFieldCustom txtId = new JTextFieldCustom();
        txtId.disable();
        JTextFieldCustom txtTen = new JTextFieldCustom();
        JTextFieldCustom txtTenGiangVien = new JTextFieldCustom();

        layoutLeft.add(new JLabel("Id"));
        layoutLeft.add(txtId);
        layoutLeft.add(new JLabel("Tên môn học"));
        layoutLeft.add(txtTen);
        layoutLeft.add(new JLabel("Tên giảng viên"));
        layoutLeft.add(txtTenGiangVien);

        JPanel layoutRight = new JPanel();
        layoutRight.setLayout(new GridLayout(1, 1));
        layoutRight.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane();
        JTable table = new JTable();
        table.setRowHeight(40);

        String[] column = {"Id", "Tên môn học", "Tên giảng viên"};

        table.setModel(modelMonHoc);
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
                MonHoc monHoc = new MonHoc();
                monHoc.setTen(txtTen.getText());
                monHoc.setTenGiangVien(txtTenGiangVien.getText());
                if (kiemTraLoi(monHoc)) {
                    try {
                        ThongBao thongBao = monHocDao.create(monHoc);
                        thongBaoKiemTra(thongBao);
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });
        JButtonCustom btnSua = new JButtonCustom("Sửa");
        btnSua.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MonHoc monHoc = new MonHoc();
                String id = txtId.getText();
                monHoc.setTen(txtTen.getText());
                monHoc.setTenGiangVien(txtTenGiangVien.getText());
                if (kiemTraLoi(monHoc)) {
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn cập nhật?", "Cảnh báo", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        try {
                            ThongBao thongBao = monHocDao.update(Integer.parseInt(id), monHoc);
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
                            ThongBao thongBao = monHocDao.delete(Integer.parseInt(txtId.getText()));
                            thongBaoKiemTra(thongBao);
                        } catch (Exception e1) {
                            // TODO Auto-generated catch block
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
                    MonHoc monHoc = monHocDao.findOne(id);
                    txtTen.setText(String.valueOf(monHoc.getTen()));
                    txtTenGiangVien.setText(String.valueOf(monHoc.getTenGiangVien()));
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

    public boolean kiemTraLoi(MonHoc monHoc) {
        if (monHoc.getTen().length() == 0) {
            thongBao("Tên môn học không được để trống");
            return false;
        }
        if (monHoc.getTenGiangVien().length() == 0) {
            thongBao("Tên giảng viên không được để trống");
            return false;
        }
        return true;
    }

//    public static void main(String[] args) {
//        QuanLyMonHoc quanLyMonHoc = new QuanLyMonHoc();
//    }
}
