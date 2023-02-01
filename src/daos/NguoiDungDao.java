package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import models.MonHoc;
import models.NguoiDung;
import models.ThongBao;
import utils.ConnectDatabase;

public class NguoiDungDao implements DaoInterface<NguoiDung> {
	public String tableName = "user";
	public String queryCreate = "INSERT INTO " + tableName + " VALUES(?,?,?,?,?)";
	public String queryUpdate = "UPDATE " + tableName + " SET `ten` = ?,`email`= ?,`matkhau`= ?, `vaitro` = ?  WHERE `id` = ?";
	public String queryXoa = "DELETE FROM " + tableName + " WHERE `id` = ?";
	public String queryFindOne = "SELECT * FROM " + tableName + " WHERE `id` = ?";
	public String queryFindOneByEmail = "SELECT * FROM " + tableName + " WHERE `email` = ?";
	public String queryFindAll = "SELECT * FROM " + tableName;
	// search to all
	public String querySearch = "SELECT * FROM " + tableName + " WHERE `ten` LIKE %?%";
	public Connection connection = ConnectDatabase.connection;
	@Override
	public ThongBao create(NguoiDung duLieu) throws Exception {
		NguoiDung nguoiDungCheck = this.findByEmail(duLieu.getEmail());
		if(nguoiDungCheck != null) {
			return new ThongBao("Tài khoản đã tồn tại", false);
		}
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(queryCreate);
			preparedStatement.setInt(1, duLieu.getId());
			preparedStatement.setString(2, duLieu.getTen());
			preparedStatement.setString(3, duLieu.getEmail());
			preparedStatement.setString(4, duLieu.getMatKhau());
			preparedStatement.setString(5, duLieu.getVaiTro());
			preparedStatement.executeUpdate();
			return new ThongBao("Thêm thành công", true);
		} catch (Exception e) {
			return new ThongBao("Thêm thất bại", false);
		}

	}

	@Override
	public ThongBao update(int id, NguoiDung duLieu) throws Exception {
		NguoiDung nguoiDung = this.findOne(id);
		if (nguoiDung == null) {
			return new ThongBao("Người dùng không tồn tại", false);
		}
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(queryUpdate);
			preparedStatement.setString(1, duLieu.getTen());
			preparedStatement.setString(2, duLieu.getEmail());
			preparedStatement.setString(3, duLieu.getMatKhau());
			preparedStatement.setString(4, duLieu.getVaiTro());
			preparedStatement.setInt(4, id);
			preparedStatement.executeUpdate();
			return new ThongBao("Cập nhật thành công", true);
		} catch (Exception e) {
			return new ThongBao("Cập nhật thất bại", false);
		}

	}

	@Override
	public ThongBao delete(int id) throws Exception {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(queryXoa);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			return new ThongBao("Xóa thành công", true);
		} catch (Exception e) {
			return new ThongBao("Xóa thất bại", false);
		}

	}

	@Override
	public NguoiDung findOne(int id) throws Exception {
		try {
			// tạo câu lệnh truy vấn sql và thực thi bằng preparedStatement
			// (PreparedStatement)
			// PreparedStatement là một lớp trong java.sql, nó được sử dụng để thực thi các
			// câu lệnh SQL có tham số.
			PreparedStatement preparedStatement = connection.prepareStatement(queryFindOne);
			// truyền tham số vào câu lệnh sql bằng phương thức set của preparedStatement
			// thay dấu ? bằng id
			preparedStatement.setInt(1, id);
			// thực thi câu lệnh sql và trả về kết quả
			ResultSet resultSet = preparedStatement.executeQuery();
			// nếu kết quả trả về có dòng thì tạo đối tượng lớp và trả về
			if (resultSet.next()) {
				NguoiDung nguoiDung = new NguoiDung();
				nguoiDung.setId(resultSet.getInt("id"));
				nguoiDung.setTen(resultSet.getString("ten"));
				nguoiDung.setEmail(resultSet.getString("email"));
				nguoiDung.setMatKhau(resultSet.getString("matkhau"));
				nguoiDung.setVaiTro(resultSet.getString("vaitro"));
				return nguoiDung;
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	@Override
	public ArrayList<NguoiDung> findAll() throws Exception {
		ArrayList<NguoiDung> nguoiDungs = new ArrayList<>();
		try {
			// tạo câu lệnh truy vấn sql và thực thi bằng preparedStatement
			// (PreparedStatement)
			PreparedStatement preparedStatement = connection.prepareStatement(queryFindAll);
			// thực thi câu lệnh sql và trả về kết quả
			ResultSet resultSet = preparedStatement.executeQuery();
			// tạo mảng monhoc để chứa kết quả

			// duyệt kết quả trả về và thêm vào mảng monhoc next() trả về true nếu còn dòng
			// tiếp theo và false nếu không còn dòng nào
			while (resultSet.next()) {
				// tạo đối tượng môn học
				NguoiDung nguoiDung = new NguoiDung();
				// gán giá trị cho đối tượng môn học
				nguoiDung.setId(resultSet.getInt("id"));
				// gán giá trị cho đối tượng môn học
				nguoiDung.setTen(resultSet.getString("ten"));
				nguoiDung.setEmail(resultSet.getString("email"));
				nguoiDung.setMatKhau(resultSet.getString("matkhau"));
				nguoiDung.setVaiTro(resultSet.getString("vaitro"));
				// thêm đối tượng môn học vào mảng môn học

				nguoiDungs.add(nguoiDung);
			}
			return nguoiDungs;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public NguoiDung findByEmail (String email ) throws Exception{
		try {
			// tạo câu lệnh truy vấn sql và thực thi bằng preparedStatement
			// (PreparedStatement)
			// PreparedStatement là một lớp trong java.sql, nó được sử dụng để thực thi các
			// câu lệnh SQL có tham số.
			PreparedStatement preparedStatement = connection.prepareStatement(queryFindOneByEmail);
			// truyền tham số vào câu lệnh sql bằng phương thức set của preparedStatement
			// thay dấu ? bằng id
			preparedStatement.setString(1, email);
			// thực thi câu lệnh sql và trả về kết quả
			ResultSet resultSet = preparedStatement.executeQuery();
			// nếu kết quả trả về có dòng thì tạo đối tượng lớp và trả về
			if (resultSet.next()) {
				NguoiDung nguoiDung = new NguoiDung();
				nguoiDung.setId(resultSet.getInt("id"));
				nguoiDung.setTen(resultSet.getString("ten"));
				nguoiDung.setEmail(resultSet.getString("email"));
				nguoiDung.setMatKhau(resultSet.getString("matkhau"));
				nguoiDung.setVaiTro(resultSet.getString("vaitro"));
				return nguoiDung;
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	@Override
	public ArrayList<NguoiDung> search(String key) throws Exception {
		try {
			String query = "SELECT * FROM " + tableName + " WHERE `ten` LIKE '%" + key + "%'OR" + "`email` LIKE '%" + key
					+ "%'OR" +"`matkhau` LIKE '%" + key + "%'" ;
			// em thêm cái vaitro vào
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<NguoiDung> nguoiDungs = new ArrayList<>();
			while (resultSet.next()) {
				NguoiDung nguoiDung = new NguoiDung();
				nguoiDung.setId(resultSet.getInt("id"));
				nguoiDung.setTen(resultSet.getString("ten"));
				nguoiDung.setEmail(resultSet.getString("email"));
				nguoiDung.setMatKhau(resultSet.getString("matkhau"));

				nguoiDungs.add(nguoiDung);
			}
			return nguoiDungs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public NguoiDung findBy(String column,  String data) throws Exception {
		try {
			 String queryFindBy = "SELECT * FROM " + tableName + " WHERE " + "`" +column +"`" + " = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(queryFindBy);
			
			// truyền tham số vào câu lệnh sql bằng phương thức set của preparedStatement
			// thay dấu ? bằng id
			preparedStatement.setString(1, data);
			System.out.println(preparedStatement);
			// thực thi câu lệnh sql và trả về kết quả
			ResultSet resultSet = preparedStatement.executeQuery();
			// nếu kết quả trả về có dòng thì tạo đối tượng lớp và trả về
			if (resultSet.next()) {
				NguoiDung nguoiDung = new NguoiDung();
				nguoiDung.setId(resultSet.getInt("id"));
				nguoiDung.setTen(resultSet.getString("ten"));
				nguoiDung.setEmail(resultSet.getString("email"));
				nguoiDung.setMatKhau(resultSet.getString("matkhau"));
				return nguoiDung;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
