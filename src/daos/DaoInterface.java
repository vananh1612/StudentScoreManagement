package daos;

import models.ThongBao;

import java.util.ArrayList;

public interface DaoInterface<T> {
    ThongBao create(T duLieu) throws Exception;

    ThongBao update(int id, T duLieu) throws Exception;

    ThongBao delete(int id) throws Exception;

    T findOne(int id) throws Exception;

    ArrayList<T> findAll() throws Exception;

    ArrayList<T> search(String key) throws Exception;
}