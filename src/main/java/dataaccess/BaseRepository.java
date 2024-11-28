package dataaccess;

import java.util.Optional;

public interface BaseRepository<T, I> {

    Optional<T> insert(T entity);
    Optional<T> getById(I id);

}
