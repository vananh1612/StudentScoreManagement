package views;

import javax.swing.*;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;

import daos.SinhVienDao;
import models.Lop;
import models.SinhVien;
import models.ThongBao;
import views.components.JButtonCustom;
import views.components.JComboBoxCustom;
import views.components.JTextFieldCustom;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class QuanLySinhVien extends JFrame {
	String column[] = { "Mã sinh viên", "Họ tên sinh viên ", "Giới tính ", "Ngày sinh", "Địa chỉ", "Sđt", "Id_lớp" };
	private DefaultTableModel modelSinhVien = new DefaultTableModel(column, 0);
	private SinhVienDao sinhVienDao = new SinhVienDao();
	ArrayList<Lop> lopsFindAll = new ArrayList<>();
	private DefaultComboBoxModel<Lop> lopComboboxModel;
	int idLop;
	public void getDataToModel() {
		try {
			ArrayList<SinhVien> sinhViens = new ArrayList<>();
			lopsFindAll = sinhVienDao.getAllLop();
			Vector<Lop> lops = new Vector<>();
			for (Lop lop : lopsFindAll) {
				lops.add(lop);
			}
			sinhViens = sinhVienDao.findAll();
			modelSinhVien.setRowCount(0);
			for (SinhVien sinhVien : sinhViens) {
				Object[] row = { sinhVien.getId(), sinhVien.getTen(), getGoiTinh(sinhVien.getGioiTinh()),
						sinhVien.getNgaySinh(), sinhVien.getDiaChi(), sinhVien.getSdt(), sinhVien.getLop().getId() };
				modelSinhVien.addRow(row);
			}
			lopComboboxModel = new DefaultComboBoxModel<>(lops);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void getDataToModelSearch(String search) {

		try {
			ArrayList<SinhVien> sinhViens = new ArrayList<>();
			sinhViens = sinhVienDao.search(search);
			modelSinhVien.setRowCount(0);
			for (SinhVien sinhVien : sinhViens) {
				Object[] row = { sinhVien.getId(), sinhVien.getTen(), sinhVien.getGioiTinh(), sinhVien.getNgaySinh(),
						sinhVien.getDiaChi(), sinhVien.getSdt(), sinhVien.getLop().getId() };
				modelSinhVien.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public QuanLySinhVien() {
		this.getDataToModel();
		Lop lop = new Lop();
		this.setTitle("Quản lý sinh viên");
		this.setSize(1200, 630);
		JPanel layoutLeft = new JPanel();
		// background màu trắng
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		layoutLeft.setLayout(new GridLayout(0, 1));
		layoutLeft.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JTextFieldCustom txtMaSV = new JTextFieldCustom();
		txtMaSV.disable();
		JTextFieldCustom txtHoTen = new JTextFieldCustom();
		JTextFieldCustom txtNgaySinh = new JTextFieldCustom();
		JComboBoxCustom cbGioiTinh = new JComboBoxCustom();
		JTextFieldCustom txtDiaChi = new JTextFieldCustom();
		JTextFieldCustom txtSDT = new JTextFieldCustom();
		// JTextFieldCustom txtId_lop = new JTextFieldCustom();

		layoutLeft.add(new JLabel("Mã sinh viên"));
		layoutLeft.add(txtMaSV);
		layoutLeft.add(new JLabel("Họ tên"));
		layoutLeft.add(txtHoTen);
		layoutLeft.add(new JLabel("Ngày sinh"));
		layoutLeft.add(txtNgaySinh);
		layoutLeft.add(new JLabel("Giới tính"));
		layoutLeft.add(cbGioiTinh);
		cbGioiTinh.addItem("Nam");
		cbGioiTinh.addItem("Nữ");
		layoutLeft.add(new JLabel("Địa Chỉ"));
		layoutLeft.add(txtDiaChi);
		layoutLeft.add(new JLabel("SĐT"));
		layoutLeft.add(txtSDT);
		layoutLeft.add(new JLabel("Lớp"));
		JComboBox<Lop> comboBoxLop = new JComboBox<Lop>();
		comboBoxLop.setModel(lopComboboxModel);
		layoutLeft.add(comboBoxLop);

		JPanel layoutRight = new JPanel();
		layoutRight.setLayout(new GridLayout(1, 1));
		layoutRight.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JScrollPane scrollPane = new JScrollPane();
		JTable table = new JTable();
		table.setRowHeight(40);

		String column[] = { "Mã sinh viên", "Họ tên", "Ngày sinh", "Địa chỉ", "SDT", "Id_Lớp" };
		table.setModel(modelSinhVien);
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
				SinhVien sinhVien = new SinhVien();
				sinhVien.setTen(txtHoTen.getText());
				sinhVien.setNgaySinh(txtNgaySinh.getText());
				sinhVien.setNgaySinh(txtNgaySinh.getText());
				sinhVien.setDiaChi(txtDiaChi.getText());
				sinhVien.setGioiTinh(cbGioiTinh.getSelectedIndex());
				sinhVien.setSdt(txtSDT.getText());
				Lop lop = (Lop) comboBoxLop.getSelectedItem();
				sinhVien.setLop(lop);
				
				if (kiemTraLoi(sinhVien)) {

					try {
						ThongBao thongBao = sinhVienDao.create(sinhVien);
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
				SinhVien sinhVien = new SinhVien();
				String id = txtMaSV.getText();
				sinhVien.setTen(txtHoTen.getText());
				sinhVien.setNgaySinh(txtNgaySinh.getText());
				sinhVien.setGioiTinh(cbGioiTinh.getSelectedIndex());
				sinhVien.setDiaChi(txtDiaChi.getText());
				sinhVien.setSdt(txtSDT.getText());
				Lop lop = (Lop) comboBoxLop.getSelectedItem();
				sinhVien.setLop(lop);

				if (kiemTraLoi(sinhVien)) {
	try {
						ThongBao thongBao = sinhVienDao.update(Integer.parseInt(id), sinhVien);
						thongBaoKiemTra(thongBao);
					} catch (Exception e1) {
						e1.printStackTrace();
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
				String id = txtMaSV.getText();
				if (id.length() == 0) {
					thongBao("Lỗi");
				} else {
					try {
						ThongBao thongBao = sinhVienDao.delete(Integer.parseInt(txtMaSV.getText()));
						thongBaoKiemTra(thongBao);
					} catch (Exception e1) {
						e1.printStackTrace();
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
				StudentHome studentHome = new StudentHome();
				studentHome.showWindown();
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
				txtMaSV.setText(String.valueOf(model.getValueAt(row, 0)));
				txtHoTen.setText((String) model.getValueAt(row, 1));
				cbGioiTinh.setSelectedItem(model.getValueAt(row, 2));
				txtNgaySinh.setText((String) model.getValueAt(row, 3));
				txtDiaChi.setText((String) model.getValueAt(row, 4));
				txtSDT.setText((String) model.getValueAt(row, 5));
				int idLop = (int) model.getValueAt(row, 6);
				Lop lop = getLopById(idLop);
				lopComboboxModel.setSelectedItem(lop);
				comboBoxLop.setModel(lopComboboxModel);

			}
		});
		this.getDataToModel();

	}

	public void thongBao(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	public void thongBaoKiemTra(ThongBao thongBao) {

		if (thongBao.getKiemTra() == true) {
			getDataToModel();
			thongBao(thongBao.getTinNhan());
		} else {
			thongBao(thongBao.getTinNhan());
		}

	}

	public String getGoiTinh(int i) {
		if (i == 1)
			return "Nữ";
		else {
			return "Nam";
		}
	}

	public Lop getLopById(int id) {

		for (Lop lop : this.lopsFindAll) {
			if (lop.getId() == id) {
				return lop;
			}
		}
		return null;

	}

	public boolean kiemTraLoi(SinhVien sinhVien) {
		if (sinhVien.getTen().length() == 0) {
			thongBao("Tên của sinh viên không được trống ");
			return false;
		}
		if (sinhVien.getNgaySinh().length() == 0) {
			thongBao("Ngày sinh của sinh viên không được trống ");
			return false;
		}
		if (sinhVien.getDiaChi().length() == 0) {
			thongBao("Địa chỉ của sinh viên không được trống ");
			return false;
		}
		if (sinhVien.getSdt().length() == 0) {
			thongBao("Sđt của sinh viên không được trống ");
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		QuanLySinhVien quanLySinhVien = new QuanLySinhVien();
	}
}
