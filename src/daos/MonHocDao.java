package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import models.MonHoc;
import models.ThongBao;
import utils.ConnectDatabase;

public class MonHocDao implements DaoInterface<MonHoc> {
	public String tableName = "mon_hoc";
	public String queryCreate = "INSERT INTO " + tableName + " VALUES(?,?,?)";
	public String queryUpdate = "UPDATE " + tableName + " SET `ten` = ?,`giang_vien` = ? WHERE `id` = ?";
	public String queryXoa = "DELETE FROM " + tableName + " WHERE `id` = ?";
	public String queryFindOne = "SELECT * FROM " + tableName + " WHERE `id` = ?";
	public String queryFindAll = "SELECT * FROM " + tableName;
	// search to all
	public String querySearch = "SELECT * FROM " + tableName + " WHERE `ten` LIKE %?% OR `ten` LIKE %?%";
	public Connection connection = ConnectDatabase.connection;

	@Override
	public ThongBao create(MonHoc duLieu) throws Exception {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(queryCreate);
			preparedStatement.setInt(1, duLieu.getId());
			preparedStatement.setString(2, duLieu.getTen());
			preparedStatement.setString(3, duLieu.getTenGiangVien());
			preparedStatement.executeUpdate();
			return new ThongBao("Thêm thành công", true);
		} catch (Exception e) {
			return new ThongBao("Thêm thất bại", false);
		}

	}

	@Override
	public ThongBao update(int id, MonHoc duLieu) throws Exception {
		MonHoc monHoc = this.findOne(id);
		if (monHoc == null) {
			return new ThongBao("Môn học không tồn tại", false);

		}
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(queryUpdate);
			preparedStatement.setString(1, duLieu.getTen());
			preparedStatement.setString(2, duLieu.getTenGiangVien());
			preparedStatement.setInt(3, id);
			preparedStatement.executeUpdate();
			return new ThongBao("Cập nhật thành công", true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ThongBao("Cập nhật thất bại", false);
			
		}

	}

	@Override
	public ThongBao delete(int id) throws Exception {
		MonHoc monHoc = this.findOne(id);
		if (monHoc == null) {
			return new ThongBao("Môn học không tồn tại", false);

		}
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
	public MonHoc findOne(int id) throws Exception {
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
				MonHoc monHoc = new MonHoc();
				monHoc.setId(resultSet.getInt("id"));
				monHoc.setTen(resultSet.getString("ten"));
				monHoc.setTenGiangVien(resultSet.getString("giang_vien"));
				return monHoc;
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	@Override
	public ArrayList<MonHoc> findAll() throws Exception {
		ArrayList<MonHoc> monHocs = new ArrayList<>();
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
				MonHoc monHoc = new MonHoc();
				// gán giá trị cho đối tượng môn học
				monHoc.setId(resultSet.getInt("id"));
				// gán giá trị cho đối tượng môn học
				monHoc.setTen(resultSet.getString("ten"));
				monHoc.setTenGiangVien(resultSet.getString("giang_vien"));
				// thêm đối tượng môn học vào mảng môn học

				monHocs.add(monHoc);
			}
			return monHocs;
		} catch (Exception e) {
			e.printStackTrace();
			return monHocs;
		}
	}

	@Override
	public ArrayList<MonHoc> search(String key) throws Exception {
		try {
			String query = "SELECT * FROM " + tableName + " WHERE `ten` LIKE '%" + key + "%'"+ "OR `giang_vien` LIKE '%" + key + "%'";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<MonHoc> monHocs = new ArrayList<>();
			while (resultSet.next()) {
				MonHoc monHoc = new MonHoc();
				monHoc.setId(resultSet.getInt("id"));
				monHoc.setTen(resultSet.getString("ten"));
				monHoc.setTenGiangVien(resultSet.getString("giang_vien"));
				monHocs.add(monHoc);
			}
			return monHocs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
