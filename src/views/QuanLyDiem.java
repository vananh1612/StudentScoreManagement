package views;

import daos.DiemDao;
import models.*;
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
import java.util.Vector;

public class QuanLyDiem extends JFrame {
    String[] column = {"Id", "Tên sinh viên", "Tên môn học", "Chuyên cần", "Bài tập", "Giữa kỳ", "Cuối kỳ",
            "Tên học kỳ", "Tính điểm", "Điểm trung bình", "Xếp loại "};
    private final DefaultTableModel modelDiem = new DefaultTableModel(column, 0);
    private final DiemDao diemDao = new DiemDao();
    ArrayList<SinhVien> sinhviensFindAll = new ArrayList<>();
    ArrayList<MonHoc> monhocsFindAll = new ArrayList<>();
    ArrayList<HocKy> hockiesFindAll = new ArrayList<>();
    private DefaultComboBoxModel<SinhVien> sinhvienComboboxModel;
    private DefaultComboBoxModel<MonHoc> monhocComboboxModel;
    private DefaultComboBoxModel<HocKy> hockyComboboxModel;
    private final Vector<TinhDiem> tinhDiems = new Vector<>();

    public void getDataToModel() {
        try {
            ArrayList<Diem> diems = new ArrayList<>();
            sinhviensFindAll = diemDao.getAllSinhVien();
            Vector<SinhVien> sinhViens = new Vector<>();
            for (SinhVien sinhVien : sinhviensFindAll) {
                sinhViens.add(sinhVien);

            }
            monhocsFindAll = diemDao.getAllMonHoc();
            Vector<MonHoc> monHocs = new Vector<>();
            for (MonHoc monHoc : monhocsFindAll) {
                monHocs.add(monHoc);
            }
            hockiesFindAll = diemDao.getAllHocKy();
            Vector<HocKy> hocKies = new Vector<>();
            for (HocKy hocKy : hockiesFindAll) {
                hocKies.add(hocKy);
            }

            diems = diemDao.findAll();
            // xoá hết dữ liệu của table
            modelDiem.setRowCount(0);
            for (Diem diem : diems) {

                Object[] row = {diem.getId(), diem.getSinhVien().getTen(), diem.getMonHoc().getTen(), diem.getChuyenCan(),
                        diem.getBaiTap(), diem.getGiuaKy(), diem.getCuoiKy(), diem.getHocKy().getTen(),
                        diem.getTinhDiem().getLoai() == 1 ? "Có điểm bài tập" : "Không có điểm bài tập", diem.getDiemTrungBinh(), diem.getXepLoai()};
                modelDiem.addRow(row);
            }
            sinhvienComboboxModel = new DefaultComboBoxModel<>(sinhViens);
            monhocComboboxModel = new DefaultComboBoxModel<>(monHocs);
            hockyComboboxModel = new DefaultComboBoxModel<>(hocKies);
        } catch (

                Exception e) {
            e.printStackTrace();
        }

    }

    public void getDataToModelSearch(String search) {

        try {
            ArrayList<Diem> diems = new ArrayList<>();
            diems = diemDao.search(search);
            modelDiem.setRowCount(0);
            for (Diem diem : diems) {
                Object[] row = {diem.getId(), diem.getSinhVien().getTen(), diem.getMonHoc().getTen(), diem.getChuyenCan(),
                        diem.getBaiTap(), diem.getGiuaKy(), diem.getCuoiKy(), diem.getHocKy().getTen(),
                        diem.getTinhDiem().getLoai() == 1 ? "Có điểm bài tập" : "Không có điểm bài tập", diem.getDiemTrungBinh(), diem.getXepLoai()};
                modelDiem.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public QuanLyDiem() {

        this.getDataToModel();
        SinhVien sinhVien = new SinhVien();
        MonHoc monHoc = new MonHoc();
        HocKy hocKy = new HocKy();
        this.setTitle("Quản lý điểm sinh viên");
        this.setSize(1200, 630);
        JPanel layoutLeft = new JPanel(new GridLayout(1, 2));
        JPanel layoutLeftLeft = new JPanel(new GridLayout(0, 1));
        JPanel layoutLeftRight = new JPanel(new GridLayout(0, 1));
        // background màu trắng

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        layoutLeftLeft.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        layoutLeftRight.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JTextFieldCustom txtId = new JTextFieldCustom();
        txtId.disable();
        JTextFieldCustom txtChuyenCan = new JTextFieldCustom();
        JTextFieldCustom txtBaiTap = new JTextFieldCustom();
        JTextFieldCustom txtGiuaKy = new JTextFieldCustom();
        JTextFieldCustom txtCuoiKy = new JTextFieldCustom();

        tinhDiems.add(new TinhDiem(1, "Có điểm bài tập"));
        tinhDiems.add(new TinhDiem(2, "Không điểm bài tập"));
        JComboBox<TinhDiem> comboboxTinhdiem = new JComboBox<>(tinhDiems);
        JTextFieldCustom txtTinhDiem = new JTextFieldCustom();
        JTextFieldCustom txtDiemTrungBinh = new JTextFieldCustom();
        JTextFieldCustom txtXepLoai = new JTextFieldCustom();
        txtDiemTrungBinh.disable();
        txtXepLoai.disable();

        layoutLeftLeft.add(new JLabel("Id"));
        layoutLeftLeft.add(txtId);
        layoutLeftLeft.add(new JLabel("Sinh viên"));
        JComboBox<SinhVien> comboBoxSinhVien = new JComboBox<SinhVien>();
        layoutLeftLeft.add(comboBoxSinhVien);
        comboBoxSinhVien.setModel(sinhvienComboboxModel);
        layoutLeftLeft.add(new JLabel("Môn học"));
        JComboBox<MonHoc> comboBoxMonHoc = new JComboBox<MonHoc>();
        comboBoxMonHoc.setModel(monhocComboboxModel);
        layoutLeftLeft.add(comboBoxMonHoc);
        layoutLeftLeft.add(new JLabel("Điểm chuyên cần"));
        layoutLeftLeft.add(txtChuyenCan);
        layoutLeftLeft.add(new JLabel("Điểm bài tập"));
        layoutLeftLeft.add(txtBaiTap);
        layoutLeftRight.add(new JLabel("Điểm giữa kỳ"));
        layoutLeftRight.add(txtGiuaKy);
        layoutLeftRight.add(new JLabel("Điểm cuối kỳ"));
        layoutLeftRight.add(txtCuoiKy);
        layoutLeftRight.add(new JLabel("Học kỳ"));
        JComboBox<HocKy> comboBoxHocKy = new JComboBox<HocKy>();
        comboBoxHocKy.setModel(hockyComboboxModel);
        layoutLeftRight.add(comboBoxHocKy);
        layoutLeftRight.add(new JLabel("Cách tính điểm"));

        layoutLeftRight.add(comboboxTinhdiem);
        layoutLeftRight.add(new JLabel("Điểm trung bình"));
        layoutLeftRight.add(txtDiemTrungBinh);
        layoutLeftRight.add(new JLabel("Xếp loại"));
        layoutLeftRight.add(txtXepLoai);
        layoutLeft.add(layoutLeftLeft);
        layoutLeft.add(layoutLeftRight);

        JPanel layoutRight = new JPanel();
        layoutRight.setLayout(new GridLayout(1, 1));
        layoutRight.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane();
        JTable table = new JTable();
        table.setRowHeight(40);

        String[] column = {"Id", "Id_sinhvien", "Id_monhoc", "Chuyên cần", "Bài tập", "Giữa kỳ", "Cuối kỳ", "Id_hocky",
                "Tính điểm", "Điểm trung bình", "Xếp loại"};
        table.setModel(modelDiem);
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
        bottomLeft.setLayout(new GridLayout(1, 2, 10, 10));
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
                Diem diem = new Diem();
                SinhVien sinhVien = (SinhVien) comboBoxSinhVien.getSelectedItem();
                MonHoc monHoc = (MonHoc) comboBoxMonHoc.getSelectedItem();
                HocKy hocKy = (HocKy) comboBoxHocKy.getSelectedItem();
                TinhDiem tinhDiem = (TinhDiem) comboboxTinhdiem.getSelectedItem();
                if (validate(sinhVien, monHoc, txtChuyenCan.getText(), txtBaiTap.getText(), txtGiuaKy.getText(), txtCuoiKy.getText(),
                        hocKy, tinhDiem)) {
                    float chuyenCan = Float.parseFloat(txtChuyenCan.getText());
                    float giuaKy = Float.parseFloat(txtGiuaKy.getText());
                    float cuoiKy = Float.parseFloat(txtCuoiKy.getText());
                    diem.setSinhVien(sinhVien);
                    diem.setMonHoc(monHoc);
                    diem.setChuyenCan(chuyenCan);
                    if (tinhDiem.getLoai() == 1) {
                        diem.setBaiTap(Float.parseFloat(txtBaiTap.getText()));
                    }
                    diem.setGiuaKy(giuaKy);
                    diem.setCuoiKy(cuoiKy);
                    diem.setHocKy(hocKy);
                    diem.setTinhDiem(tinhDiem);
                    diem.tinhDiemTrungBinh();
                    diem.tinhXepLoai();
                    try {
                        ThongBao thongBao = diemDao.create(diem);
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
                Diem diem = new Diem();
                String id = txtId.getText();
                SinhVien sinhVien = (SinhVien) comboBoxSinhVien.getSelectedItem();
                MonHoc monHoc = (MonHoc) comboBoxMonHoc.getSelectedItem();
                HocKy hocKy = (HocKy) comboBoxHocKy.getSelectedItem();
                TinhDiem tinhDiem = (TinhDiem) comboboxTinhdiem.getSelectedItem();
                if (validate(sinhVien, monHoc, txtChuyenCan.getText(), txtBaiTap.getText(), txtGiuaKy.getText(), txtCuoiKy.getText(),
                        hocKy, tinhDiem)) {
                    float chuyenCan = Float.parseFloat(txtChuyenCan.getText());
                    float giuaKy = Float.parseFloat(txtGiuaKy.getText());
                    float cuoiKy = Float.parseFloat(txtCuoiKy.getText());
                    diem.setSinhVien(sinhVien);
                    diem.setMonHoc(monHoc);
                    diem.setChuyenCan(chuyenCan);
                    if (tinhDiem.getLoai() == 1) {
                        diem.setBaiTap(Float.parseFloat(txtBaiTap.getText()));
                    }
                    diem.setGiuaKy(giuaKy);
                    diem.setCuoiKy(cuoiKy);
                    diem.setHocKy(hocKy);
                    diem.setTinhDiem(tinhDiem);
                    diem.tinhDiemTrungBinh();
                    diem.tinhXepLoai();
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn cập nhật?", "Cảnh báo", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        try {
                            ThongBao thongBao = diemDao.update(Integer.parseInt(id), diem);
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
                            ThongBao thongBao = diemDao.delete(Integer.parseInt(txtId.getText()));
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
                    Diem diem = diemDao.findOne(id);
                    sinhvienComboboxModel.setSelectedItem(diem.getSinhVien());
                    comboBoxSinhVien.setModel(sinhvienComboboxModel);
                    monhocComboboxModel.setSelectedItem(diem.getMonHoc());
                    comboBoxMonHoc.setModel(monhocComboboxModel);
                    txtChuyenCan.setText(String.valueOf(diem.getCuoiKy()));
                    txtBaiTap.setText(String.valueOf(diem.getBaiTap()));
                    txtGiuaKy.setText(String.valueOf(diem.getGiuaKy()));
                    txtCuoiKy.setText(String.valueOf(diem.getChuyenCan()));
                    txtDiemTrungBinh.setText(String.valueOf(diem.getDiemTrungBinh()));
                    txtXepLoai.setText(String.valueOf(diem.getXepLoai()));
                    hockyComboboxModel.setSelectedItem(diem.getHocKy());
                    comboBoxHocKy.setModel(hockyComboboxModel);
                    TinhDiem tinhDiem = getTinhDiemByLoai(diem.getTinhDiem().getLoai());
                    ComboBoxModel model2 = comboboxTinhdiem.getModel();
                    model2.setSelectedItem(tinhDiem);
                    comboboxTinhdiem.setModel(model2);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }


            }
        });
        this.getDataToModel();

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

    public SinhVien getSinhVienById(int id) {
        for (SinhVien sinhVien : this.sinhviensFindAll) {
            if (sinhVien.getId() == id) {
                return sinhVien;
            }
        }
        return null;
    }

    public MonHoc getMonHocById(int id) {

        for (MonHoc monHoc : this.monhocsFindAll) {
            if (monHoc.getId() == id) {
                return monHoc;
            }
        }
        return null;

    }

    public HocKy getHocKyById(int id) {

        for (HocKy hocKy : this.hockiesFindAll) {
            if (hocKy.getId() == id) {
                return hocKy;
            }
        }
        return null;

    }

    public TinhDiem getTinhDiemByLoai(int id) {

        for (TinhDiem tinhDiem : this.tinhDiems) {
            if (tinhDiem.getLoai() == id) {
                return tinhDiem;
            }
        }
        return null;

    }

    public boolean validate(SinhVien sinhVien, MonHoc monHoc, String chuyenCan, String baiTap, String giuaKy, String cuoiKy,
                            HocKy hocKy, TinhDiem tinhDiem) {

        if (sinhVien == null) {
            thongBao("Chưa chọn sinh viên");
            return false;
        }
        if (monHoc == null) {
            thongBao("Chưa chọn môn học");
            return false;
        }
        if (hocKy == null) {
            thongBao("Chưa chọn học kỳ");
            return false;
        }
        if (chuyenCan.isEmpty()) {
            thongBao("Chưa nhập đểm chuyên cần");
            return false;
        }
        if (tinhDiem.getLoai() == 1 && baiTap.isEmpty()) {
            thongBao("Chưa nhập đểm bài tập");
            return false;
        }
        if (giuaKy.isEmpty()) {
            thongBao("Chưa nhập đểm giữa kỳ");
            return false;
        }
        if (cuoiKy.isEmpty()) {
            thongBao("Chưa nhập đểm giữa kỳ");
            return false;
        }
        return true;
    }

//    public static void main(String[] args) {
//
//        QuanLyDiem quanLyDiem = new QuanLyDiem();
//
//    }

}
