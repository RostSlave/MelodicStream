package com.example.kursovoi.controllers;

import com.example.kursovoi.models.Song;
import com.example.kursovoi.models.User;
import com.example.kursovoi.repositories.SongsRepository;
import com.example.kursovoi.repositories.UsersRepository;
import com.example.kursovoi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;


@Controller
public class UserController{

    @Autowired
    private UserService userService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private SongsRepository songsRepository;

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }
    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @GetMapping("/user")
    public ModelAndView menuUser(){
        ModelAndView mav = new ModelAndView("user");
        User user = userService.getUserByEmail(getCurrentUsername());
        mav.addObject("user", user);
        return mav;
    }

    @GetMapping("/user_info/del/{id}")
    public String deleteProfile(@PathVariable(name = "id") long id) {
        Song song=songsRepository.getOne(id);
        song.setUser(null);
        songsRepository.delete(song);
        File file=new File("C:\\Users\\rostislav\\Downloads\\Kusra4\\src\\main\\resources\\static\\songs\\"+id+".mp3");
        if(file.delete()){
            System.out.println("Файл удален");
        }else System.out.println("Файла не обнаружено");
        User user = userService.getUserByEmail(getCurrentUsername());
        long id_user=user.getId();
        return "redirect:/user_info/"+id_user;
    }

    @PostMapping("/save/{id}")
    public String updateData(@PathVariable(value = "id") long id, @ModelAttribute("name") String name,
            @ModelAttribute("surname") String surname, Model model) {
        // Находим нужную запись по ID
        User user1 = userService.getUserById(id);
        user1.setName(name);
        user1.setSurname(surname);
        usersRepository.save(user1); // Сохраняем (обновляем) запись
        return "redirect:/user_info/{id}";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable( value = "id") long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "update_user_info";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUserAdmin(@PathVariable (value = "id") long id) {
        usersRepository.deleteById(id);
        return "redirect:/admin/";
    }
}
