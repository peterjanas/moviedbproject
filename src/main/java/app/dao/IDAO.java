package app.dao;

import app.entity.Movie;
import app.entity.Personnel;

import java.util.Set;

public interface IDAO<T>
{
    void create(T t);
    T getById(Long Id);
    Set<T> getAll();

}
