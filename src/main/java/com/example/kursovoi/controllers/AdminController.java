package com.example.kursovoi.controllers;

import com.example.kursovoi.models.Contract;
import com.example.kursovoi.models.Song;
import com.example.kursovoi.models.Support;
import com.example.kursovoi.models.User;
import com.example.kursovoi.repositories.*;
import com.example.kursovoi.service.UserService;
import com.example.kursovoi.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private SongsRepository songsRepository;

    @Autowired
    private ContractsRepository contractsRepository;

    @Autowired
    private SupportRepository supportRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @PostMapping("/saveUserAdmin")
    public String saveAdmin(@ModelAttribute("user") UserRegistrationDto user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/showFormForUpdateAdmin/{id}")
    public String showFormForUpdate(@PathVariable( value = "id") long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "update_user";
    }

    @GetMapping("/deleteUserAdmin/{id}")
    public String deleteUserAdmin(@PathVariable (value = "id") long id) {
        User user = userService.getUserById(id);
        usersRepository.delete(user);
        return "redirect:/admin/";
    }

    @GetMapping("/subs")
    public String getSubs(Model model){
        List<Contract> contracts=contractsRepository.findAll();
        model.addAttribute("contracts", contracts);
        return "subs";
    }

    @GetMapping("/tracks")
    public String getTracks(Model model){
        List<Song> songs=songsRepository.findAll();
        model.addAttribute("songs", songs);
        return "tracks";
    }

    @GetMapping("/sup")
    public String getMails(Model model){
        List< Support> supports=supportRepository.findAll();
        model.addAttribute("supports", supports);
        return "sup";
    }

    @RequestMapping("/admin")
    public String viewHomePage(Model model, @Param("keyword") String keyword) {
        List<User> list = usersRepository.findByIdNot(29);
        int sum=0;
        for (int i=0; i< list.size();i++){
            sum+= list.get(i).getSum();
        }
        String mes="Общая сумма денег, которые не успели быть уплаченными пользователями за услуги, составляет "+sum+"$.";
        List<User> listUser = userService.findByEmail(keyword);
        model.addAttribute("sum", mes);
        model.addAttribute("keyword", keyword);
        model.addAttribute("listUser", listUser);
        model.addAttribute("list", list);
        return "admin";
    }
}