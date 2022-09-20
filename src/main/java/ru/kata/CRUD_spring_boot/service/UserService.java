package ru.kata.CRUD_spring_boot.service;

import ru.kata.CRUD_spring_boot.model.User;

import java.util.List;

public interface UserService {
    User create(User user);
    List<User> list(int count);
    List<User> listAll();
    User find(Long id);
    void delete(User user);
    void delete(Long id);
    User update(long id, User user);
}
