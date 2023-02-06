package views;

import daos.HocKyDao;
import models.HocKy;
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

public class QuanLyHocKy extends JFrame {
    String[] column = {"Id", "Năm học", "Tên học kì"};
    private final DefaultTableModel modelHocKy = new DefaultTableModel(column, 0);
    private final HocKyDao hocKyDao = new HocKyDao();

    public void getDataToModel() {

        try {
            ArrayList<HocKy> hocKies = new ArrayList<>();
            hocKies = hocKyDao.findAll();
            modelHocKy.setRowCount(0);
            for (HocKy hocKy : hocKies) {
                Object[] row = {hocKy.getId(), hocKy.getNamHoc(), hocKy.getTen()};
                modelHocKy.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getDataToModelSearch(String search) {

        try {
            ArrayList<HocKy> hocKies = new ArrayList<>();
            hocKies = hocKyDao.search(search);
            modelHocKy.setRowCount(0);
            for (HocKy hocKy : hocKies) {
                Object[] row = {hocKy.getId(), hocKy.getNamHoc(), hocKy.getTen()};
                modelHocKy.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public QuanLyHocKy() {
        this.setTitle("Quản lý học kỳ");
        this.setSize(1200, 630);
        JPanel layoutLeft = new JPanel();
        // background màu trắng
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        layoutLeft.setLayout(new GridLayout(0, 1));
        layoutLeft.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextFieldCustom txtId = new JTextFieldCustom();
        txtId.disable();
        JTextFieldCustom txtNamHoc = new JTextFieldCustom();
        JTextFieldCustom txtTenHocKy = new JTextFieldCustom();

        layoutLeft.add(new JLabel("Id"));
        layoutLeft.add(txtId);
        layoutLeft.add(new JLabel("Năm học"));
        layoutLeft.add(txtNamHoc);
        layoutLeft.add(new JLabel("Tên học kỳ"));
        layoutLeft.add(txtTenHocKy);

        JPanel layoutRight = new JPanel();
        layoutRight.setLayout(new GridLayout(1, 1));
        layoutRight.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane();
        JTable table = new JTable();
        table.setRowHeight(40);

        String[] column = {"Id", "Tên", "Tên giảng viên"};
        this.getDataToModel();
        table.setModel(modelHocKy);
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
                HocKy hocKy = new HocKy();
                hocKy.setTen(txtTenHocKy.getText());
                hocKy.setNamHoc(txtNamHoc.getText());
                if (kiemTraLoi(hocKy)) {
                    try {
                        ThongBao thongBao = hocKyDao.create(hocKy);
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
                HocKy hocKy = new HocKy();
                String id = txtId.getText();
                hocKy.setTen(txtTenHocKy.getText());
                hocKy.setNamHoc(txtNamHoc.getText());
                if (kiemTraLoi(hocKy)) {
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn cập nhật?", "Cảnh báo", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        try {
                            ThongBao thongBao = hocKyDao.update(Integer.parseInt(id), hocKy);
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
                        //do something if user clicked "Yes"
                        try {
                            ThongBao thongBao = hocKyDao.delete(Integer.parseInt(txtId.getText()));
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
                    HocKy hocKy = hocKyDao.findOne(id);
                    txtNamHoc.setText(String.valueOf(hocKy.getNamHoc()));
                    txtTenHocKy.setText(String.valueOf(hocKy.getTen()));
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

    public boolean kiemTraLoi(HocKy hocKy) {
        if (hocKy.getTen().length() == 0) {
            thongBao("Tên học kỳ không được để trống");
            return false;
        }
        if (hocKy.getNamHoc().length() == 0) {
            thongBao("Học kỳ không được để trống");
            return false;
        }
        return true;
    }
//	public static void main(String[] args) {
//
//		QuanLyHocKy quanLyHocKy = new QuanLyHocKy();
//
//	}
}
