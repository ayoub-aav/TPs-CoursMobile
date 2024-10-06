package com.example.myapplication.Dao;

import java.util.List;

public interface idao<T> {
    public abstract boolean create(T O);

    boolean update(T o);

    boolean delete(T o);

    T findById(int id);

    List<T> findall();
}
