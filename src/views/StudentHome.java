package views;

import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentHome extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentHome frame = new StudentHome();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public StudentHome() {
		this.setTitle("Student Home");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1500, 900);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setLocation(0, 11);
		lblNewLabel.setSize(158, 591);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel(".");
		lblNewLabel_1.setBounds(168, 11, 578, 591);
		getContentPane().add(lblNewLabel_1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridLayout(2, 1));

		JButton btnSinhVien = new JButton("Sinh Viên");
		btnSinhVien.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new QuanLySinhVien().setVisible(true);
				dispose();
				

			}
		});	
		btnSinhVien.setBackground(Color.PINK);
		//set phông chữ
		
		btnSinhVien.setForeground(Color.BLACK);
		btnSinhVien.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSinhVien.setBounds(10, 25, 135, 43);
		getContentPane().add(btnSinhVien);

		JButton btnLp = new JButton("Lớp");
		btnLp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new QuanLyLop().setVisible(true);
				dispose();
				
			}
		});
		btnLp.setForeground(Color.BLACK);
		btnLp.setBackground(Color.WHITE);
		btnLp.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnLp.setBounds(10, 104, 135, 43);
		getContentPane().add(btnLp);

		JButton btnHcK = new JButton("Học Kỳ");
		btnHcK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new QuanLyHocKy().setVisible(true);
				dispose();
				

			}
		});
		btnHcK.setBackground(Color.BLUE);
		btnHcK.setForeground(Color.WHITE);
		btnHcK.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnHcK.setBounds(10, 182, 135, 43);
		getContentPane().add(btnHcK);

		JButton btnMnHc = new JButton("Môn Học");
		btnMnHc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new QuanLyMonHoc().setVisible(true);
				dispose();
				
			}
		});
		btnMnHc.setBackground(Color.CYAN);
		btnMnHc.setForeground(Color.BLACK);
		btnMnHc.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnMnHc.setBounds(10, 257, 135, 43);
		getContentPane().add(btnMnHc);

		JButton btnNgiDng = new JButton("Người Dùng");
		btnNgiDng.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new QuanLyNguoiDung().setVisible(true);
				dispose();
				
			}	
		});
		btnNgiDng.setBackground(Color.RED);
		btnNgiDng.setForeground(Color.WHITE);
		btnNgiDng.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNgiDng.setBounds(10, 335, 135, 43);
		getContentPane().add(btnNgiDng);

		JButton btnim = new JButton("Điểm");
		btnim.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new QuanLyDiem().setVisible(true);
				dispose();
				
			}
		});
		btnim.setBackground(Color.GRAY);
		btnim.setForeground(Color.WHITE);
		btnim.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnim.setBounds(10, 420, 135, 43);
		getContentPane().add(btnim);
		
	

		JButton btnLogout = new JButton("Thoát!");
		btnLogout.addActionListener(new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					int a = JOptionPane.showConfirmDialog(btnLogout, "Bạn có muốn đăng xuất");
					if (a== JOptionPane.YES_OPTION)
					{
						dispose();
						 new DangNhap();
						
						
					}
					
				}
			});

		btnLogout.setBackground(Color.YELLOW);
		btnLogout.setForeground(Color.BLACK);
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnLogout.setBounds(10, 538, 135, 43);
		getContentPane().add(btnLogout);
   
	}
	public void showWindown() {
		setVisible(true);
		
	}
}
