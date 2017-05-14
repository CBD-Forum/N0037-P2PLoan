package cn.edu.fudan.bclab.hackathon.service;

import cn.edu.fudan.bclab.hackathon.entity.Credit;
import cn.edu.fudan.bclab.hackathon.entity.CreditTransaction;
import cn.edu.fudan.bclab.hackathon.entity.User;
import cn.edu.fudan.bclab.hackathon.entity.Status;
import cn.edu.fudan.bclab.hackathon.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by bintan on 17-4-17.
 */

public interface CreditService {
    void save(Credit credit);

    void update(Credit credit);

    void delete(Long creditId);

    Credit getById(Long creditId);


    void updateStatus(Credit credit, Status status);

   // Boolean checkCredit(User user, CreditTransaction creditTransaction);

    List<Credit> getList();

    List<Credit> getAvailableCredits();

    String creditCurrentSumModify(Credit credit,Double modify);

    String CreditNeededSumModify(Credit credit,Double modify);

    String CreditPersonNumsModify(Credit credit,int modify);

    Credit getByName(String name);

    List<Credit> getCompleteCredit();

    List<CreditTransaction> getCreditTransactions(Credit creditCom);

    List<User> getUsers(Credit creditCom);

    List<Pair<Integer,Double>> getSingalcreditWeekly(Long creditId);
}
