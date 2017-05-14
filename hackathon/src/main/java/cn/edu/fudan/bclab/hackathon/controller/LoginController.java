package cn.edu.fudan.bclab.hackathon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import cn.edu.fudan.bclab.hackathon.entity.User;
import cn.edu.fudan.bclab.hackathon.repository.UserRepository;
import cn.edu.fudan.bclab.hackathon.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserRepository userRepository;

    @CrossOrigin
    @RequestMapping(value = {"", "/", "login"},method = RequestMethod.GET)
    @ResponseBody
    public String index() {

        return "success";
    }

    @CrossOrigin
    @RequestMapping(value = {"/login/check"},method = RequestMethod.POST)
    @ResponseBody
    public String login(HttpSession httpSession,
                        @RequestBody User pu
    ) {

        if (pu.getUsername().trim().equals("") || pu.getPassword().trim().equals("")) {
            return "success";
        }

        User user = userRepository.findByUsername(pu.getUsername());


        if (user.getPassword().equals(pu.getPassword())) {

            httpSession.setAttribute("username", user.getUsername());

            return "success";
        } else {

            return "failed";
        }


    }

    @CrossOrigin
    @RequestMapping(value = {"/login/out"},method = RequestMethod.GET)
    public String logout(HttpSession httpSession) {
        httpSession.setAttribute("username", null);
        return "logout";

    }

}


