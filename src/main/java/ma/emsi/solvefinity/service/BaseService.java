package ma.emsi.solvefinity.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BaseService<T, ID> {
    List<T> getAll();

    T getById(ID id);

    T save(T baseEntity);

    void delete(ID id);
}
