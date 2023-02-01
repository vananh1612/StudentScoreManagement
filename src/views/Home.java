package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class Home extends JFrame {

	private JPanel jPanel;

	public static void main(String[] args) {
		Home home = new Home();
	}

	public Home() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1013, 625);
		jPanel = new JPanel();
		setContentPane(jPanel);
		jPanel.setLayout(null);
		JLabel lblLabel = new JLabel("");
		lblLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabel.setIcon(new ImageIcon("C:\\Users\\Admin\\Downloads\\vku1.jfif"));
		lblLabel.setBounds(80, 11, 821, 455);
		jPanel.add(lblLabel);

		JButton btnDangNhap = new JButton("Đăng Nhập");
		btnDangNhap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new DangNhap().setVisible(true);
			}
		});

		btnDangNhap.setBackground(Color.CYAN);
		btnDangNhap.setForeground(Color.DARK_GRAY);
		btnDangNhap.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnDangNhap.setBounds(207, 488, 203, 76);
		jPanel.add(btnDangNhap);

		JButton btnDangKy = new JButton("Đăng Ký");
		btnDangKy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new DangKy().setVisible(true);
			}
		});
		btnDangKy.setBackground(Color.RED);
		btnDangKy.setForeground(Color.BLACK);
		btnDangKy.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnDangKy.setBounds(571, 488, 203, 76);
		jPanel.add(btnDangKy);
		setVisible(true);
	}
}
