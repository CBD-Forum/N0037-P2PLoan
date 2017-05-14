package cn.edu.fudan.bclab.hackathon.controller;

import cn.edu.fudan.bclab.hackathon.entity.*;
import cn.edu.fudan.bclab.hackathon.repository.UserRepository;
import cn.edu.fudan.bclab.hackathon.service.AccountService;
import cn.edu.fudan.bclab.hackathon.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpSession;

/**
 * Created by bintan on 17-4-13.
 */
@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    @CrossOrigin
    @RequestMapping(value = {"/add",}, method = RequestMethod.POST)
    @ResponseBody
    public String addAction(@RequestBody User pu) {
        User user = new User();
        user.setUsername(pu.getUsername());
        user.setPassword(pu.getPassword());
        user.setUserStatus(UserStatus.NORMAL);
        BlockchainAccount anlinkAccount = accountService.createAccount(pu.getPassword());
        user.setUserPrivateKey(anlinkAccount.getPrivkey());
        user.setUserPassPhrase(pu.getPassword());
        user.setUserChainAdress(anlinkAccount.getAddress());
        if (registerService.register(user).equals("failed")) {
            return "username existed";
        } else {

            userRepository.save(user);
            return "success";
        }
    }


}
