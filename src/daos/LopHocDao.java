package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import models.Lop;
import models.ThongBao;
import utils.ConnectDatabase;

public class LopHocDao implements DaoInterface<Lop> {
	public String tableName = "Lop";
	public String queryCreate = "INSERT INTO " + tableName + " VALUES(?,?)";
	public String queryUpdate = "UPDATE " + tableName + " SET `ten` = ? WHERE `id` = ?";
	public String queryXoa = "DELETE FROM " + tableName + " WHERE `id` = ?";
	public String queryFindOne = "SELECT * FROM " + tableName + " WHERE `id` = ?";
	public String queryFindAll = "SELECT * FROM " + tableName;
	// search to all
	public String querySearch = "SELECT * FROM " + tableName + " WHERE `ten` LIKE %?%";
	public Connection connection = ConnectDatabase.connection;

	@Override
	public ThongBao create(Lop duLieu) throws Exception {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(queryCreate);
			preparedStatement.setInt(1, duLieu.getId());
			preparedStatement.setString(2, duLieu.getTen());
			preparedStatement.executeUpdate();
			return new ThongBao("Thêm thành công", true);
		} catch (Exception e) {
			return new ThongBao("Thêm thất bại", false);
		}
	}

	@Override
	public ThongBao update(int id, Lop duLieu) throws Exception {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(queryUpdate);
			preparedStatement.setString(1, duLieu.getTen());
			preparedStatement.setInt(2, id);
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
	public Lop findOne(int id) throws Exception {
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
				Lop lop = new Lop();
				lop.setId(resultSet.getInt("id"));
				lop.setTen(resultSet.getString("ten"));
				return lop;
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	@Override
	public ArrayList<Lop> findAll() throws Exception {
		ArrayList<Lop> lops = new ArrayList<>();
		try {
			// tạo câu lệnh truy vấn sql và thực thi bằng preparedStatement
			// (PreparedStatement)
			PreparedStatement preparedStatement = connection.prepareStatement(queryFindAll);
			// thực thi câu lệnh sql và trả về kết quả
			ResultSet resultSet = preparedStatement.executeQuery();
			// tạo mảng lớp để chứa kết quả
			
			// duyệt kết quả trả về và thêm vào mảng lớp next() trả về true nếu còn dòng
			// tiếp theo và false nếu không còn dòng nào
			while (resultSet.next()) {
				// tạo đối tượng lớp
				Lop lop = new Lop();
				// gán giá trị cho đối tượng lớp
				lop.setId(resultSet.getInt("id"));
				// gán giá trị cho đối tượng lớp
				lop.setTen(resultSet.getString("ten"));
				// thêm đối tượng lớp vào mảng lớp
				lops.add(lop);
			}
			return lops;
		} catch (Exception e) {
			return null;
		}
		

	} 
	@Override
	public ArrayList<Lop> search(String key) throws Exception {
		try {
			String query = "SELECT * FROM " + tableName + " WHERE `ten` LIKE '%" + key + "%'";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<Lop> lops = new ArrayList<>();
			while (resultSet.next()) {
				Lop lop = new Lop();
				lop.setId(resultSet.getInt("id"));
				lop.setTen(resultSet.getString("ten"));
				lops.add(lop);
			}
			return lops;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		LopHocDao lopHocDao = new LopHocDao();
		try {
			ArrayList<Lop> lops = lopHocDao.search("3B");
			for (Lop lop : lops) {
				System.out.println(lop);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
