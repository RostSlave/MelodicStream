package com.example.kursovoi.controllers;

import com.example.kursovoi.models.Support;
import com.example.kursovoi.models.User;
import com.example.kursovoi.repositories.SupportRepository;
import com.example.kursovoi.repositories.UsersRepository;
import com.example.kursovoi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;

@Controller
public class SupportController {

    @Autowired
    private UserService userService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private SupportRepository supportRepository;

    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @GetMapping("/support")
    public ModelAndView menuSup(){
        ModelAndView mav = new ModelAndView("support");
        User user = userService.getUserByEmail(getCurrentUsername());
        mav.addObject("support", user);
        return mav;
    }

    @GetMapping("/help/{id}")
    public ModelAndView menuHelp(@PathVariable(value = "id") long id, Model model){
        ModelAndView mav = new ModelAndView("help");
        User user = userService.getUserByEmail(getCurrentUsername());
        mav.addObject("help", user);
        return mav;
    }

    @GetMapping("/sup_del/{id}")
    public String deleteMail(@PathVariable (value = "id") long id) {
        supportRepository.deleteById(id);
        File file = new File("C:\\Users\\rostislav\\Downloads\\Kusra4\\src\\main\\resources\\static\\mails\\"+id+".txt");
        file.delete();
        return "redirect:/sup";
    }

    @GetMapping("/sup_read/{id}")
    public String getMail(@PathVariable(value = "id") long id, Model model){
        Support sup=supportRepository.getOne(id);
        File file = new File("C:\\Users\\rostislav\\Downloads\\Kusra4\\src\\main\\resources\\static\\mails\\"+id+".txt");
        String fileContent=null;
        try (FileReader fr = new FileReader(file))
        {
            char[] chars = new char[(int) file.length()];
            fr.read(chars);
            fileContent = new String(chars);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("sup", sup);
        model.addAttribute("fileContent", fileContent);
        return "sup_read";
    }

    @PostMapping("/help/{id}")
    public String getMail(@PathVariable(value = "id") long id, @ModelAttribute("theme") String theme,
                          @ModelAttribute("mail") String mail,
                             Model model) throws IOException {
        User user = userService.getUserById(id);
        Support support = new Support();
        support.setEmail(user.getEmail());
        support.setTheme(theme);
        supportRepository.save(support);
        File file = new File("C:\\Users\\rostislav\\Downloads\\Kusra4\\src\\main\\resources\\static\\mails\\"+
                support.getId()+".txt");
        try (FileWriter fw = new FileWriter(file);
             BufferedWriter bf = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bf))
        {
            out.print(mail);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/support";
    }

}
