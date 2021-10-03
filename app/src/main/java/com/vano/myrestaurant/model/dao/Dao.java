package com.vano.myrestaurant.model.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    List<T> findAll();

    T findById(int id);

    default T findByName(String name) {
        return null;
    }

    default List<T> findAllFavorite() {
        return null;
    }

    default void updateFavoriteColumn(int id, int favorite) {
    }

    default int create(T entity) {
        return 0;
    }

    default void delete(int id) {
    }
}
