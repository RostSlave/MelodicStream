package com.example.kursovoi.controllers;

import com.example.kursovoi.models.Contract;
import com.example.kursovoi.models.User;
import com.example.kursovoi.repositories.ContractsRepository;
import com.example.kursovoi.repositories.UsersRepository;
import com.example.kursovoi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class SubscribeController {

    @Autowired
    private UserService userService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ContractsRepository contractsRepository;

    @GetMapping("/subscribe/{id}")
    public String updateData(@PathVariable(value = "id") long id, Model model) {
        // Находим нужную запись по ID
        User user = userService.getUserById(id);
        model.addAttribute("userData", user);
        List<Contract> contracts = contractsRepository.findAll();
        model.addAttribute("contracts", contracts);
        if(user.getContract()==null){
            String mes="false";
            model.addAttribute("but_change", mes);
            String mes2="Выберите любую из предложенных подписок";
            model.addAttribute("info", mes2);
        }
        else{
            String mes="true";
            model.addAttribute("but_change", mes);
            String mes2="У вас уже есть подписка";
            model.addAttribute("info", mes2);
        }
        return "/subscribe";
    }

    @PostMapping("/update_sub/{id}")
    public String updateData(@PathVariable(value = "id") long id, @ModelAttribute("price") int price,
                             @ModelAttribute("royalty") String royalty,
                             @ModelAttribute("days") int days, Model model) {
        Contract contract=contractsRepository.getOne(id);
        contract.setPrice(price);
        contract.setRoyalty(royalty);
        contract.setDays(days);
        contractsRepository.save(contract);
        return "redirect:/subs";
    }

    @GetMapping("/subs_del/{id}")
    public String deleteUserAdmin(@PathVariable (value = "id") long id) {
        contractsRepository.deleteById(id);
        return "redirect:/subs";
    }

    @PostMapping("/create_sub")
    public String createSUb(@ModelAttribute("price") int price,
                             @ModelAttribute("royalty") String royalty,
                             @ModelAttribute("days") int days, Model model) {
        Contract contract=new Contract();
        contract.setPrice(price);
        contract.setRoyalty(royalty);
        contract.setDays(days);
        contractsRepository.save(contract);
        return "redirect:/subs";
    }

    @GetMapping("/new_sub")
    public String newSub(){return "new_sub";}

    @GetMapping("/subs_update/{id}")
    public String showFormForUpdate(@PathVariable( value = "id") long id, Model model) {
        Contract contract=contractsRepository.getOne(id);
        model.addAttribute("contract", contract);
        return "subs_update";
    }

    @PostMapping("/subscribe/{id}")
    public String updateData(@PathVariable(value = "id") long id, @ModelAttribute("subscription_id") long sub_id,
                              Model model) {
        User user1 = userService.getUserById(id);
        Contract contract=contractsRepository.findContractById(sub_id);
            user1.setContract(contractsRepository.findContractById(sub_id));
            user1.setDateBegin(LocalDate.now());
            user1.setDateEnd(user1.getDateBegin().plusDays(contract.getDays()));
            user1.setSum(user1.getSum()+contract.getPrice());
            user1.setRoyalty(contract.getRoyalty());
            usersRepository.save(user1); // Сохраняем (обновляем) запись
        return "redirect:/user_info/{id}";
    }
}
