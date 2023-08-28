package com.example.kursovoi.controllers;

import com.example.kursovoi.models.Contract;
import com.example.kursovoi.models.Song;
import com.example.kursovoi.models.User;
import com.example.kursovoi.repositories.ContractsRepository;
import com.example.kursovoi.repositories.SongsRepository;
import com.example.kursovoi.repositories.UsersRepository;
import com.example.kursovoi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user_info/{id}")
public class InfoController {

    @Autowired
    private UserService userService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private SongsRepository songsRepository;

    @Autowired
    private ContractsRepository contractsRepository;
    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    //    @GetMapping("/user_info/{id}")
//    public String menuUser_Info(@PathVariable( name = "id") int id,Model model){
//        User user = userService.getUserById(id);
//        model.addAttribute("userData", user);
//        return "/user_info";
//    }
    @GetMapping
    public String song(@PathVariable(name = "id") int id, Model model)
    {
        User user = userService.getUserById(id);
        model.addAttribute("userData", user);
        User user2 = usersRepository.findByEmail(getCurrentUsername());
        List<Song> song = songsRepository.findAllByUserId(user2.getId());
        model.addAttribute("listSongs", song);
        Contract contract = usersRepository.findByQuery(user.getId());
        if(contract==null){
            String mes="Нет подписки, стоимость выпуска каждого трека - 20$, ROYALTY -50%";
            user.setRoyalty("50%");
            usersRepository.save(user);
            model.addAttribute("ContractInfo", mes);
        }
        else{
            String mes="Подписка активирована с "+user.getDateBegin()+" по "+user.getDateEnd()+". Стоимость подписки - "+contract.getPrice()+
                    "$, а также с каждого вашего трека будет взиматься прибыль в размере "
                    +contract.getRoyalty()+"%.";
            model.addAttribute("ContractInfo", mes);
        }
        return "/user_info";
    }
}
