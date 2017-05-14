package cn.edu.fudan.bclab.hackathon.controller;

import java.util.ArrayList;
import java.util.List;

import cn.edu.fudan.bclab.hackathon.entity.*;
import cn.edu.fudan.bclab.hackathon.service.CreditService;
import cn.edu.fudan.bclab.hackathon.util.EntityToViewCredit;
import cn.edu.fudan.bclab.hackathon.util.EntityToViewUser;
import cn.edu.fudan.bclab.hackathon.util.ViewToEntityObject;
import cn.edu.fudan.bclab.hackathon.viewEntity.ResponseCredit;
import cn.edu.fudan.bclab.hackathon.viewEntity.ResponseUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import cn.edu.fudan.bclab.hackathon.service.UserService;

import cn.edu.fudan.bclab.hackathon.viewEntity.RequestObject;

/**
 * Created by bintan on 17-4-10.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    ViewToEntityObject viewToEntityObject = new ViewToEntityObject();

    EntityToViewUser entityToViewUser = new EntityToViewUser();

    EntityToViewCredit entityToViewCredit = new EntityToViewCredit();

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private CreditService creditService;

    @CrossOrigin
    @RequestMapping(value = {"", "/", "list", "index"},method = RequestMethod.GET)
    @ResponseBody
    public List<ResponseUser> list(){
        List<User> userList = userService.getList();

        List<ResponseUser> ret = new ArrayList<ResponseUser>();

        for(User user:userList){
            ret.add(entityToViewUser.getUserView(user));
        }
        return ret;
    }

    /*@CrossOrigin
    @RequestMapping(value="/add", method = RequestMethod.POST)
    @ResponseBody
    public User addAction(@RequestBody User pu) {
        User user = new User();
        user.setUsername(pu.getUsername());
        user.setPassword(pu.getPassword());
        user.setUserStatus(UserStatus.NORMAL);
        try {
            userService.save(user);
            return user;
        } catch (Exception e) {
            return user;
        }
    }*/

    @CrossOrigin
    @RequestMapping(value = "/showReleaseCreditById",method = RequestMethod.POST)
    @ResponseBody
    public List<ResponseCredit> showReleaseCredit(@RequestBody User pu){
        List<Credit> creditList = userService.getReleaseCreditList(pu.getUserId());
        List<ResponseCredit> ret = new ArrayList<ResponseCredit>();
        for (Credit credit:creditList) {
            ret.add(entityToViewCredit.getCreditView(credit));
        }
        return ret;
    }
    @CrossOrigin
    @RequestMapping(value = "/showReleaseCreditByUsername",method = RequestMethod.POST)
    @ResponseBody
    public List<ResponseCredit> showReleaseCreditByUsername(@RequestBody User pu){
        List<Credit> creditList = userService.getReleaseCreditListByUsername(pu.getUsername());
        List<ResponseCredit> ret = new ArrayList<ResponseCredit>();
        for (Credit credit:creditList) {
            ret.add(entityToViewCredit.getCreditView(credit));
        }
        return ret;
    }

    @CrossOrigin
    @RequestMapping(value = "/showBuyCreditById",method = RequestMethod.POST)
    @ResponseBody
    public List<ResponseCredit>showBuyCredit(@RequestBody User pu){
        List<Credit> creditList = userService.getBuyCreditList(pu.getUserId());
        List<ResponseCredit> ret = new ArrayList<ResponseCredit>();
        for (Credit credit:creditList) {
            ret.add(entityToViewCredit.getCreditView(credit));
        }

        return ret;
    }

    @CrossOrigin
    @RequestMapping(value = "/showBuyCreditByUsername",method = RequestMethod.POST)
    @ResponseBody
    public List<ResponseCredit>showBuyCreditByUsername(@RequestBody User pu){
        List<Credit> creditList = userService.getBuyCreditListByUsername(pu.getUsername());
        List<ResponseCredit> ret = new ArrayList<ResponseCredit>();
        for (Credit credit:creditList) {
            ret.add(entityToViewCredit.getCreditView(credit));
        }

        return ret;
    }

    @CrossOrigin
    @RequestMapping(value = "/releaseCredit",method = RequestMethod.POST)
    @ResponseBody
    public String releaseCredit(@RequestBody RequestObject requestObject){
        User pu = viewToEntityObject.getUserEntity(requestObject);
        Credit pcredit = viewToEntityObject.getCreditEntity(requestObject);
        return userService.releaseCredit(pu,pcredit);
    }

    @CrossOrigin
    @RequestMapping(value = "/buyCredit",method = RequestMethod.POST)
    @ResponseBody
    public String buyCredit(@RequestBody RequestObject requestObject){

        User pu = viewToEntityObject.getUserEntity(requestObject);
        Credit pcredit = creditService.getByName(requestObject.getName());
        CreditTransaction pcreditTransaction = viewToEntityObject.getCreditTransactionEntity(requestObject);
        return userService.buyCredit(pu,pcredit,pcreditTransaction);
    }

}
