package com.challengeitau.challengeitaujunior.respository;

import java.util.List;

public interface IDatabase<T> {
    void save(T object);
    List<T> findAll();
    void delete();
}
