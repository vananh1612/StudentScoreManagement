package views;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;

import daos.LopHocDao;
import models.HocKy;
import models.Lop;

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
import java.util.Iterator;

public class QuanLyLop extends JFrame {
	String column[] = { "Id", "Tên lớp học" };
	private DefaultTableModel modelLop = new DefaultTableModel(column, 0);
	private LopHocDao lopHocDao = new LopHocDao();

	public void getDataToModel() {

		try {
			ArrayList<Lop> lops = new ArrayList<>();
			lops = lopHocDao.findAll();
			modelLop.setRowCount(0);
			for (Lop lop : lops) {
				Object[] row = { lop.getId(), lop.getTen() };
				modelLop.addRow(row);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void getDataToModelSearch(String search) {

		try {
			ArrayList<Lop> lops = new ArrayList<>();
			lops = lopHocDao.search(search);
			modelLop.setRowCount(0);
			for (Lop lop : lops) {
				Object[] row = { lop.getId(), lop.getTen() };
				modelLop.addRow(row);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public QuanLyLop() {

		this.setTitle("Quản lý lớp học ");
		this.setSize(1200, 630);
		JPanel layoutLeft = new JPanel();
		// background màu trắng
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		layoutLeft.setLayout(new GridLayout(0, 1));
		layoutLeft.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JTextFieldCustom txtId = new JTextFieldCustom();
		txtId.disable();
		JTextFieldCustom txtTen = new JTextFieldCustom();

		layoutLeft.add(new JLabel("Id"));
		layoutLeft.add(txtId);
		layoutLeft.add(new JLabel("Tên lớp học"));
		layoutLeft.add(txtTen);

		JPanel layoutRight = new JPanel();
		layoutRight.setLayout(new GridLayout(1, 1));
		layoutRight.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JScrollPane scrollPane = new JScrollPane();
		JTable table = new JTable();
		table.setRowHeight(40);

		String column[] = { "Id", "Tên lớp học" };
		this.getDataToModel();
		table.setModel(modelLop);
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
				Lop lop = new Lop();
				lop.setTen(txtTen.getText());
				if(kiemTraLoi(lop)) {
					try {
						ThongBao thongBao = lopHocDao.create(lop);
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
				Lop lop = new Lop();
				String id = txtId.getText();
				lop.setTen(txtTen.getText());
				if(kiemTraLoi(lop)) {
					try {
						ThongBao thongBao = lopHocDao.update(Integer.parseInt(id), lop);
						thongBaoKiemTra(thongBao);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
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
				String id = txtId.getText();
				if (id.length() == 0) {
					thongBao("Lỗi");
				} else {
					try {
						ThongBao thongBao = lopHocDao.delete(Integer.parseInt(txtId.getText()));
						thongBaoKiemTra(thongBao);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});
		// color màu đỏ
		btnXoa.setBackground(new Color(255, 0, 0));
		JButtonCustom btnThoat = new JButtonCustom(" Thoát");
		btnThoat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//                System.exit(0);
				dispose();
				new StudentHome().setVisible(true);
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
					Lop lop = lopHocDao.findOne(id);	
					txtTen.setText(String.valueOf(lop.getTen()));
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

		if (thongBao.getKiemTra() == true) {
			getDataToModel();
			thongBao(thongBao.getTinNhan());
		} else {
			thongBao(thongBao.getTinNhan());
		}

	}

	public boolean kiemTraLoi(Lop lop) {
		if (lop.getTen().length() == 0) {
			thongBao("Tên lớp không được để trống");
			return false;
		}
		
		return true;
	}

	public static void main(String[] args) {

		QuanLyLop quanLyLop = new QuanLyLop();

	}
}
