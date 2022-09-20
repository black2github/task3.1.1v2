package ru.kata.CRUD_spring_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.kata.CRUD_spring_boot.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kata.CRUD_spring_boot.model.UserRepository;

@Service("userService")
@Repository
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        log.debug("create: <- " + user);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> list(int count) {
        log.debug("list: <- count = " + count);

        Iterator<User> it  = userRepository.findAll().iterator();
        List<User> list = new ArrayList<>();
        for (int i=0; it.hasNext() && i < count; i++) {
            User user = it.next();
            list.add(user);
        }
        return list;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> listAll() {
        log.debug("listAll: <-");
        List<User> list = new ArrayList<>();
        Iterable<User> i = userRepository.findAll();
        for (User user : i) {
            list.add(user);
        }
        log.debug("listAll: -> " + Arrays.toString(list.toArray()));
        return list;
    }

    @Transactional(readOnly = true)
    @Override
    public User find(Long id) {
        log.debug("find: <- id=" + id);
        Optional<User> u = userRepository.findById(id);
        if (u.isEmpty()) {
            log.warn("find: User with id=" + id + " not found");
        }
        return u.orElse(null);
    }

    @Override
    public void delete(User user) {
        log.debug("delete: <- " + user);
        userRepository.delete(user);
    }

    @Override
    public void delete(Long id) {
        log.debug("delete: <- id=" + id);
        User usr = find(id);
        if (usr != null) {
            delete(usr);
        } else {
            log.warn("delete: User with id=" + id + " not found");
        }
    }

    @Override
    public User update(long id, User user) {
        log.debug(String.format("update: <- id=%d, user=%s", id, user));

        User u = userRepository.findById(id).get();
        if (u == null) {
            log.warn("update: User with id=" + id + " not found");
            return null;
        }
        if (user != null) {
            u.setFirstName(user.getFirstName());
            u.setSecondName(user.getSecondName());
            u.setAge(user.getAge());
            u.setRoles(user.getRoles());
            u = userRepository.save(u);
        }

        log.debug("update: -> " + u);
        return u;
    }
}
