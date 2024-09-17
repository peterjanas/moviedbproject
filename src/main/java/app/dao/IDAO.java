package app.dao;

import java.util.Set;

public interface IDAO<T>
{
    T getById(Integer Id);
    Set<T> getAll();
    void create(T t);
    void update(T t);
    void delete(T t);

}
