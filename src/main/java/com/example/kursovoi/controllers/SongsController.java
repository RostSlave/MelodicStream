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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.nio.file.Files;

@Controller
public class SongsController {

    @Autowired
    private UserService userService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private SongsRepository songsRepository;

    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @GetMapping("/songs")
    public ModelAndView menuSongs(){
        ModelAndView mav = new ModelAndView("songs");
        User user = userService.getUserByEmail(getCurrentUsername());
        mav.addObject("songs", user);
        return mav;
    }

    @GetMapping("/music/{id}")
    public ModelAndView menuMus(@PathVariable(value = "id") long id, Model model){
        ModelAndView mav = new ModelAndView("music");
        User user = userService.getUserByEmail(getCurrentUsername());
        mav.addObject("music", user);
        return mav;
    }

    @GetMapping("/track_update/{id}")
    public String showFormForUpdate(@PathVariable( value = "id") long id, Model model) {
        Song track=songsRepository.getOne(id);
        model.addAttribute("track", track);
        return "track_update";
    }

    @PostMapping("/update_track/{id}")
    public String updateData(@PathVariable(value = "id") long id, @ModelAttribute("name") String name,
                             @ModelAttribute("singer") String singer, Model model) {
        Song song=songsRepository.getOne(id);
        song.setName(name);
        song.setSinger(singer);
        songsRepository.save(song);
        return "redirect:/tracks";
    }


    @GetMapping("/track_del/{id}")
    public String deleteUserAdmin(@PathVariable (value = "id") long id) {
        Song song=songsRepository.getOne(id);
        song.setUser(null);
        songsRepository.delete(song);
        File file=new File("C:\\Users\\rostislav\\Downloads\\Kusra4\\src\\main\\resources\\static\\songs\\"+song.getId()+".mp3");
        if(file.delete()){
            System.out.println("Файл удален");
        }else System.out.println("Файла не обнаружено");
        return "redirect:/tracks";
    }

    @RequestMapping(value="/music/{id}", method= RequestMethod.POST)
    public String getMail(@PathVariable(value = "id") long id, @RequestParam("name") String name,
                          @RequestParam("artist") String artist,
                          @RequestParam("song") MultipartFile song,
                          Model model) throws IOException {
        User user = userService.getUserById(id);
        Song song1=new Song();
        song1.setUser(user);
        song1.setName(name);
        song1.setSinger(artist);
        if(user.getContract()==null){
            user.setSum(user.getSum()+20);
        }
        songsRepository.save(song1);
        usersRepository.save(user);
        if (!song.isEmpty()) {
            try {
                byte[] bytes = song.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(Files.newOutputStream(new File("C:\\Users\\rostislav\\Downloads\\Kusra4\\src\\main\\resources\\static\\songs\\" + song1.getId()+".mp3").toPath()));
                stream.write(bytes);
                stream.close();
                return "redirect:/songs";
            } catch (Exception e) {
                return "redirect:/songs";
            }
        } else {
            return "redirect:/songs";
        }
    }
}
