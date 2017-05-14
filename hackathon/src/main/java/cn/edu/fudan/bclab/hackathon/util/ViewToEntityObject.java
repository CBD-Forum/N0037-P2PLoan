package cn.edu.fudan.bclab.hackathon.util;

import cn.edu.fudan.bclab.hackathon.entity.Credit;
import cn.edu.fudan.bclab.hackathon.entity.CreditTransaction;
import cn.edu.fudan.bclab.hackathon.entity.User;
import cn.edu.fudan.bclab.hackathon.service.UserService;
import cn.edu.fudan.bclab.hackathon.viewEntity.RequestObject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lomo on 2017/5/11.
 */
public class ViewToEntityObject {

    public User getUserEntity(RequestObject requestObject){
        User user = new User();
        user.setUsername(requestObject.getUsername());
        user.setPassword(requestObject.getPassword());
        user.setAccount(requestObject.getAccount());
        user.setCreditScore(requestObject.getCreditScore());
        user.setUserChainAdress(requestObject.getUserChainAdress());
        user.setUserPassPhrase(requestObject.getUserPassPhrase());
        user.setUserPrivateKey(requestObject.getUserPrivateKey());
        return user;
    }
    public Credit getCreditEntity(RequestObject requestObject){
        Credit credit = new Credit();
        credit.setAmountSum(requestObject.getAmountSum());
        credit.setName(requestObject.getName());
        credit.setCreditType(requestObject.getCreditType());
        credit.setCurrentSum(requestObject.getCurrentSum());
        credit.setNeededSum(requestObject.getNeededSum());
        credit.setCreditReason(requestObject.getCreditReason());
        credit.setCreditTime(requestObject.getCreditTime());
        credit.setLongs(requestObject.getLongs());
        credit.setRatio(requestObject.getRatio());
        credit.setNeededScore(requestObject.getNeededScore());
        credit.setCurrentPersonNums(requestObject.getCurrentPersonNums());
        credit.setCreditChainAdress(requestObject.getCreditChainAdress());
        credit.setCreditPassPhrase(requestObject.getCreditPassPhrase());
        credit.setCreditPrivateKey(requestObject.getCreditPrivateKey());

        return credit;
    }
    public CreditTransaction getCreditTransactionEntity(RequestObject requestObject){


        CreditTransaction creditTransaction = new CreditTransaction();
        creditTransaction.setAccountSum(requestObject.getAccountSum());
        creditTransaction.setType(requestObject.getType());
        creditTransaction.setCreditTrasactionChainAdress(requestObject.getCreditTrasactionChainAdress());
        creditTransaction.setCreditTrasactionPassPhrase(requestObject.getCreditTrasactionPassPhrase());
        creditTransaction.setCreditTrasactionPrivateKey(requestObject.getCreditTrasactionPrivateKey());
        creditTransaction.setTime(requestObject.getTime());
        return creditTransaction;
    }
}
