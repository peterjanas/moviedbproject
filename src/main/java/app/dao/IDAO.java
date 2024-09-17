package app.dao;

import java.util.Set;

public interface IDAO<T>
{
    T getById(Long Id);
    Set<T> getAll();
    void create(T t);
    void update(T t);
    void delete(T t);

}
