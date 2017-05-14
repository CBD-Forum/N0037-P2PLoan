package cn.edu.fudan.bclab.hackathon.controller;

import cn.edu.fudan.bclab.hackathon.entity.Credit;
import cn.edu.fudan.bclab.hackathon.entity.CreditTransaction;
import cn.edu.fudan.bclab.hackathon.entity.User;
import cn.edu.fudan.bclab.hackathon.service.CreditService;
import cn.edu.fudan.bclab.hackathon.util.EntityToViewCredit;
import cn.edu.fudan.bclab.hackathon.util.Pair;
import cn.edu.fudan.bclab.hackathon.viewEntity.RequestObject;
import cn.edu.fudan.bclab.hackathon.viewEntity.ResponseCredit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bintan on 17-4-24.
 */
@Controller
@RequestMapping("/credit")
public class CreditController {

    EntityToViewCredit entityToViewCredit = new EntityToViewCredit();
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private CreditService creditService;

    @CrossOrigin
    @RequestMapping(value = {"", "/", "list", "index"},method = RequestMethod.GET)
    @ResponseBody
    public List<ResponseCredit> list() {
        List<Credit> creditList = creditService.getList();
        List<ResponseCredit> ret = new ArrayList<ResponseCredit>();
        for (Credit credit : creditList) {
            ret.add(entityToViewCredit.getCreditView(credit));
        }
        return ret;
    }

    @CrossOrigin
    @RequestMapping(value = "/availableCredit",method = RequestMethod.GET)
    @ResponseBody
    public List<ResponseCredit> getAvailableCredit(){
        List<Credit> creditList = creditService.getAvailableCredits();
        List<ResponseCredit> ret = new ArrayList<ResponseCredit>();
        for (Credit credit : creditList) {
            ret.add(entityToViewCredit.getCreditView(credit));
        }
        return ret;
    }

    @CrossOrigin
    @RequestMapping(value = "/completeCredit",method = RequestMethod.GET)
    @ResponseBody
    public List<ResponseCredit> getCompleteCredit(){
        List<Credit> creditList = creditService.getCompleteCredit();
        List<ResponseCredit> ret = new ArrayList<ResponseCredit>();
        for (Credit credit : creditList) {
            ret.add(entityToViewCredit.getCreditView(credit));
        }
        return ret;
    }
    @CrossOrigin
    @RequestMapping(value = "/creditDetail",method = RequestMethod.POST)
    @ResponseBody
    public List<RequestObject> getCreditDetail(@RequestBody Credit credit){
        Credit creditCom = creditService.getById(credit.getCreditId());

        List<CreditTransaction> creditTransactionList = creditService.getCreditTransactions(creditCom);

        List<RequestObject> requestObjectList = new ArrayList<RequestObject>();

        for(CreditTransaction creditTransaction : creditTransactionList){
            RequestObject requestObject = new RequestObject();
            User user = creditTransaction.getUser();
            requestObject.setUserId(user.getUserId());
            requestObject.setUsername(user.getUsername());
            requestObject.setAccountSum(creditTransaction.getAccountSum());
            requestObject.setTime(creditTransaction.getTime());
            requestObject.setType(creditTransaction.getType());
            requestObject.setRatio(creditCom.getRatio());
            requestObject.setCreditScore(user.getCreditScore());
            requestObject.setAmountSum(creditCom.getAmountSum());
            requestObject.setName(creditCom.getName());
            requestObject.setCreditType(creditCom.getCreditType());
            requestObject.setCurrentSum(creditCom.getCurrentSum());
            requestObject.setNeededSum(creditCom.getNeededSum());
            requestObject.setCreditReason(creditCom.getCreditReason());
            requestObject.setCreditTime(creditCom.getCreditTime());
            requestObject.setLongs(creditCom.getLongs());
            requestObject.setRatio(creditCom.getRatio());
            requestObject.setNeededScore(creditCom.getNeededScore());
            requestObject.setCurrentPersonNums(creditCom.getCurrentPersonNums());
            requestObject.setCreditChainAdress(creditCom.getCreditChainAdress());
            requestObject.setCreditPassPhrase(creditCom.getCreditPassPhrase());
            requestObject.setCreditPrivateKey(creditCom.getCreditPrivateKey());
            requestObjectList.add(requestObject);
        }
        return requestObjectList;

    }
    @CrossOrigin
    @RequestMapping(value = "/creditId",method = RequestMethod.POST)
    @ResponseBody
    public ResponseCredit getSingalCredit(@RequestBody Credit credit){
        return entityToViewCredit.getCreditView(creditService.getById(credit.getCreditId()));
    }

    @CrossOrigin
    @RequestMapping(value = "/creditWeekly",method = RequestMethod.POST)
    @ResponseBody
    public List<Pair<Integer,Double>> getSingalcreditWeekly(@RequestBody Credit credit) {
        return creditService.getSingalcreditWeekly(credit.getCreditId());
    }


}
