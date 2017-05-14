package cn.edu.fudan.bclab.hackathon.service;

import cn.edu.fudan.bclab.hackathon.entity.*;
import cn.edu.fudan.bclab.hackathon.repository.CreditRepository;
import cn.edu.fudan.bclab.hackathon.service.CreditService;
import cn.edu.fudan.bclab.hackathon.util.CommonUtil;
import cn.edu.fudan.bclab.hackathon.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by bintan on 17-4-17.
 */
@Service("creditService")
public class CreditServiceImpl implements CreditService {


    @Autowired

    private CreditRepository creditRepository;


    public void save(Credit credit) {
        creditRepository.save(credit);
    }

    @Override
    public Credit getById(Long creditid) {
        return creditRepository.findOne(creditid);
    }

    @Override
    public List<Credit> getList() {
        return (List<Credit>) creditRepository.findAll();
    }

    @Override
    public List<Credit> getAvailableCredits() {
        return creditRepository.findByCreditStatus(CreditStatus.AVAILABLE);
    }

    @Override
    public String creditCurrentSumModify(Credit credit, Double modify) {
        try {
            credit.setCurrentSum(modify);
            creditRepository.save(credit);
            return "success";
        } catch (Exception e) {
            return e.toString();
        }
    }

    @Override
    public String CreditNeededSumModify(Credit credit, Double modify) {
        try {
            credit.setNeededSum(modify);
            creditRepository.save(credit);
            return "success";
        } catch (Exception e) {
            return e.toString();
        }
    }

    @Override
    public String CreditPersonNumsModify(Credit credit, int modify) {
        try {
            credit.setCurrentPersonNums(modify);
            creditRepository.save(credit);
            return "success";
        } catch (Exception e) {
            return e.toString();
        }
    }

    @Override
    public Credit getByName(String name) {
        return creditRepository.findByName(name);
    }

    @Override
    public List<Credit> getCompleteCredit() {
        return creditRepository.findByCreditStatus(CreditStatus.FINISHED);
    }

    @Override
    public List<CreditTransaction> getCreditTransactions(Credit creditCom) {
        return (List<CreditTransaction>) creditCom.getCreditTransactions();
    }

    @Override
    public List<User> getUsers(Credit creditCom) {
        List<CreditTransaction> creditTransactionList = getCreditTransactions(creditCom);

        List<User> userList = new ArrayList<User>();

        for (CreditTransaction creditTransaction : creditTransactionList) {
            userList.add(creditTransaction.getUser());
        }
        return userList;
    }

    @Override
    public List<Pair<Integer, Double>> getSingalcreditWeekly(Long creditId) {

        HashMap<String, Integer> weekMap = new HashMap<String, Integer>();

        weekMap.put("Mon", 0);
        weekMap.put("Tue", 1);
        weekMap.put("Wed", 2);
        weekMap.put("Thu", 3);
        weekMap.put("Fri", 4);
        weekMap.put("Sat", 5);
        weekMap.put("Sun", 6);


        Credit credit = getById(creditId);
        List<Pair<Integer, Double>> creditWeekly = new ArrayList<Pair<Integer, Double>>();

        for (int i = 0; i < 8; i++) {
            Pair<Integer, Double> addpair = new Pair<Integer, Double>(0, 0.0);
            creditWeekly.add(addpair);
        }

        List<CreditTransaction> creditTransactionList = (List<CreditTransaction>) credit.getCreditTransactions();

        int totalPerson = creditTransactionList.size();
        double totalAmount =0.0;
        for (CreditTransaction creditTransaction : creditTransactionList) {
            String day = creditTransaction.getTime().split(" ")[0];
            double amount = creditTransaction.getAccountSum() / 100;
            int index = weekMap.get(day);
            totalAmount+=amount;

            creditWeekly.set(index,new Pair<Integer, Double>(creditWeekly.get(index).getFirst()+1,creditWeekly.get(index).getSecond()+amount));

        }
        creditWeekly.set(7,new Pair<Integer,Double>(totalPerson,totalAmount*100));
        return creditWeekly;
    }

    @Override
    public void delete(Long creditId) {
        Credit oriCredit = creditRepository.findByCreditId(creditId);

        if (oriCredit != null) {


            if (!oriCredit.getStatus().equals("DELETED")) {
                oriCredit.setStatus(Status.DELETED);
                creditRepository.save(oriCredit);
            }
        }
    }

    public void update(Credit credit) {
        //TODO

    }

    private String calDigest(Credit credit) {
        StringBuffer message = new StringBuffer();
        message.append(credit.getAmountSum());
        message.append(credit.getCreditType());
        message.append(credit.getCurrentSum());
        message.append(credit.getName());

        String digest = CommonUtil.calSHA256(message.toString());
        credit.setDigest(digest);
        return digest;
    }

    public void updateStatus(Credit credit, Status status) {
        credit.setStatus(status);
        creditRepository.save(credit);
    }

    /*@Override
    public Boolean checkCredit(User user, CreditTransaction creditTransaction) {

        return creditTransaction.getCreditScoreRequire() <= user.getCreditScore();
    }*/
}