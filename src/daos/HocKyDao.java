package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import models.HocKy;
import models.ThongBao;
import utils.ConnectDatabase;

public class HocKyDao implements DaoInterface<HocKy> {
	public String tableName = "hocky";
	public String queryCreate = "INSERT INTO " + tableName + " VALUES(?,?,?)";
	public String queryUpdate = "UPDATE " + tableName + " SET `namhoc` = ?,`tenhocky`= ? WHERE `id` = ?";
	public String queryXoa = "DELETE FROM " + tableName + " WHERE `id` = ?";
	public String queryFindOne = "SELECT * FROM " + tableName + " WHERE `id` = ?";
	public String queryFindAll = "SELECT * FROM " + tableName;
	// search to all
	public String querySearch = "SELECT * FROM " + tableName + " WHERE `tenhocky` LIKE %?%";
	public Connection connection = ConnectDatabase.connection;

	@Override
	public ThongBao create(HocKy duLieu) throws Exception {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(queryCreate);
			preparedStatement.setInt(1, duLieu.getId());
			preparedStatement.setString(2, duLieu.getNamHoc());
			preparedStatement.setString(3, duLieu.getTen());
			preparedStatement.executeUpdate();
			return new ThongBao("Thêm thành công", true);
		} catch (Exception e) {
			return new ThongBao("Thêm thất bại", false);
		}

	}

	@Override
	public ThongBao update(int id, HocKy duLieu) throws Exception {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(queryUpdate);
			preparedStatement.setString(1, duLieu.getNamHoc());
			preparedStatement.setString(2, duLieu.getTen());
			preparedStatement.setInt(3, id);
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
	public HocKy findOne(int id) throws Exception {
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
				HocKy hocKy = new HocKy();
				hocKy.setId(resultSet.getInt("id"));
				hocKy.setNamHoc(resultSet.getString("namhoc"));
				hocKy.setTen(resultSet.getString("tenhocky"));
				return hocKy;
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	@Override
	public ArrayList<HocKy> findAll() throws Exception {
		ArrayList<HocKy> hockys = new ArrayList<>();
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
				HocKy hocKy = new HocKy();
				// gán giá trị cho đối tượng môn học
				hocKy.setId(resultSet.getInt("id"));
				// gán giá trị cho đối tượng môn học
				hocKy.setNamHoc(resultSet.getString("namhoc"));
				hocKy.setTen(resultSet.getString("tenhocky"));
				// thêm đối tượng môn học vào mảng môn học

				hockys.add(hocKy);
			}
			return hockys;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ArrayList<HocKy> search(String key) throws Exception {
		try {
			String query = "SELECT * FROM " + tableName + " WHERE `namhoc` LIKE '%" + key + "%'OR" +"`tenhocky` LIKE '%" + key + "%'";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<HocKy> hocKys = new ArrayList<>();
			while (resultSet.next()) {
				HocKy hocKy = new HocKy();
				hocKy.setId(resultSet.getInt("id"));
				hocKy.setNamHoc(resultSet.getString("namhoc"));
				hocKy.setTen(resultSet.getString("tenhocky"));
				hocKys.add(hocKy);
			}
			return hocKys;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
