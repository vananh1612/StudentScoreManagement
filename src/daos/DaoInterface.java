package daos;

import java.util.ArrayList;

import models.ThongBao;

public interface DaoInterface<T>  {
	

    public ThongBao create(T duLieu) throws Exception;
    public ThongBao update(int id ,T duLieu) throws Exception;
    public ThongBao delete(int id) throws Exception;
    public T findOne(int id) throws Exception;
    public ArrayList<T> findAll() throws Exception;
    public ArrayList<T> search(String key) throws Exception; 
}