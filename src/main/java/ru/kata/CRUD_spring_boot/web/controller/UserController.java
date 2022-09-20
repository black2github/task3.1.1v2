package ru.kata.CRUD_spring_boot.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.CRUD_spring_boot.model.User;
import ru.kata.CRUD_spring_boot.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    // добавление пользователей при первом запуске, если еще не созданы
    @PostConstruct
    public void init() {
        log.debug("init: <-");
            List<User> users = userService.listAll();
            if (!users.iterator().hasNext()) {
                for (int i = 0; i < 5; i++) {
                    User user = userService.create(new User("firstName" + i, "secondName" + i, i));
                    log.debug("list:" + user);
                }
                users = userService.listAll();
            }
    }

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
    GET /users/ - получение списка всех пользователей
     */
    @GetMapping()
    public String list(ModelMap model) {
        log.debug("list: <- ");
        List<User> users = userService.listAll();
        model.addAttribute("users", users);
        log.debug("list: -> " + users);
        return "users/index";
    }

    /*
    GET /users/:id - заполнение данных данных о конкретном пользователе для просмотра
     */
    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, ModelMap model) {
        log.debug("show: <- id=" + id);

        // получение одного пользователя по id и передача на отображение
        User user = userService.find(id);
        model.addAttribute("user", user);
        log.debug("show: -> " + user);
        return "users/show";
    }

    /*
    GET /users/new - создание пустого объекта для заполнения данными формы
    и ссылка на форму создания нового пользователя
     */
    @GetMapping(value = "/new")
    public String newUser(@ModelAttribute("user") User user) {
        log.debug("newUser: <- ");
        return "users/new";
    }

    /*
     POST /users/ - обработка данных с формы:
      - создание пользователя по объекту, заполненному на форме
      - перенаправление на начальну страницу вывода списка
     */
    @PostMapping()
    public String create(@ModelAttribute("user") User user) {
        log.debug("create: <- " + user);
        User u = userService.create(user);
        log.debug("create: -> " + u);
        return "redirect:/users";
    }

    /*
     GET /users/:id/edit - заполнение объекта данными
     и отправка на отправка на форму редактирование данных пользователя
     */
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        log.debug("edit: <- id=" + id);
        User user = userService.find(id);
        model.addAttribute("user", user);
        log.debug("edit: -> "+ user);
        return "users/edit";
    }

    /*
     PATCH /users/:id - обновление данных пользователя c конкретным id
     */
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        log.debug("update: <- user=" + user + ", id=" + id);
        userService.update(id, user);
        log.debug("update: ->");
        return "redirect:/users";
    }

    /*
     DELETE /users/:id - удаление пользователя по id
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        log.debug("delete: <- id=" + id);
        userService.delete(id);
        log.debug("delete: ->");
        return "redirect:/users";
    }
}
