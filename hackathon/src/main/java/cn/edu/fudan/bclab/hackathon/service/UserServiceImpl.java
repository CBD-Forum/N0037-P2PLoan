package cn.edu.fudan.bclab.hackathon.service;

import cn.edu.fudan.bclab.hackathon.entity.*;
import cn.edu.fudan.bclab.hackathon.repository.CreditRepository;
import cn.edu.fudan.bclab.hackathon.repository.CreditTransactionRepository;
import cn.edu.fudan.bclab.hackathon.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by bintan on 17-4-9.
 */


@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private CreditTransactionRepository creditTransactionRepository;

    @Autowired
    private  AccountService accountService;

    @Autowired
    private CreditService creditService;

    @Autowired
    private P2PService p2pService;

    public void save(User user) {
        userRepository.save(user);
    }

    public void update(User user) {
        User oriUser = userRepository.findAllByUserId(user.getUserId());
        if (oriUser != null) {

        }
    }

    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }

    public User getById(Long userid) {
        return userRepository.findOne(userid);
    }

    public List<User> getList() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    /*@TODO 征信分数未确定,写入链不确定*/
    public String releaseCredit(User user, Credit credit) {

        User ruser = getByUsername(user.getUsername());

        if(creditService.getByName(credit.getName())!=null){
            return "credit already exist";
        }
        ruser.setCreditScore(new Long(10));
        if(ruser.getCreditScore() >= 0){
            credit.setCurrentPersonNums(0);
            credit.setUser(ruser);
            credit.setCreditTime(new Date().toString());
            credit.setNeededScore((long)820);

            AnlinkContractCreateResult result = p2pService.initContract(ruser.getUserChainAdress(), credit.getAmountSum().intValue() ,credit.getLongs(),credit.getRatio().intValue(),ruser);

            String contractAddress =result.getContract();
            credit.setCreditChainAdress(contractAddress);

            logger.info("anlink result is {}",result);
            if(p2pService.checkUser(ruser, credit)) {
                if(p2pService.checkLoan(ruser, credit)) {
                    credit.setCreditStatus(CreditStatus.AVAILABLE);
                } else {
                    credit.setCreditStatus(CreditStatus.FAIL);                }
            } else
            {
                credit.setCreditStatus(CreditStatus.FAIL);
            }

            creditRepository.save(credit);
            return "success";
        }
        else{
            return "CreditScore too low";
        }
    }

    /*@TODO 写入链,交易存储不成功依然扣钱*/
    @Override
    public String buyCredit(User user, Credit credit,CreditTransaction creditTransaction) {
        User buser = getByUsername(user.getUsername());
        Credit bcredit = creditRepository.findByCreditId(credit.getCreditId());
        CreditTransaction bcreditTransaction = creditTransaction;

        if(bcredit.getUser().getUserId() == buser.getUserId()){
            return "cannot buy own credit";
        }
        if(credit.getCreditStatus() == CreditStatus.AVAILABLE) {
            //项目可用

            if (buser.getCreditScore() >= 0) {
                //现阶段不判断购买者资格


                if(buser.getAccount() >= creditTransaction.getAccountSum()){
                    //余额大于填入金额

                    if(creditTransaction.getAccountSum() <= bcredit.getNeededSum()) {
                        //期望购买金额小于等于征集金额

                        //启动合约信息

                        p2pService.pay(buser.getUserChainAdress(), creditTransaction.getAccountSum().intValue(), buser, bcredit);

                        if (!userAccountModify(buser, buser.getAccount() - creditTransaction.getAccountSum())
                                .equals("success")) return "userAccountModify failed";

                        if (!creditService.creditCurrentSumModify(bcredit, bcredit.getCurrentSum() + creditTransaction.getAccountSum())
                                .equals("success")) return "creditCurrentSumModify failed";

                        if (!creditService.CreditNeededSumModify(bcredit, bcredit.getNeededSum() - creditTransaction.getAccountSum())
                                .equals("success")) return "CreditNeededSumModify failed";

                        if(!creditService.CreditPersonNumsModify(bcredit,bcredit.getCurrentPersonNums()+1)
                                .equals("success")) return "CreditPersonNumsModify failed";

                        bcreditTransaction.setUser(buser);
                        bcreditTransaction.setCredit(bcredit);
                        bcreditTransaction.setTime(new Date().toString());
                        creditTransactionRepository.save(bcreditTransaction);
                        return "success";
                    }else{
                        return "credit not enough";
                    }
                }else{
                    return "account not enough";
                }
            } else {
                return "CreditScore not enough";
            }
        }else {
            return "This credit is not available";
        }
    }

    @Override
    public String userNameModify(String username, String modify) {

        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setUsername(modify);
            userRepository.save(user);
            return "success";
        } else {
            return "user not exist";
        }
    }

    @Override
    public String userPasswdModify(String username, String modify) {

        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setPassword(modify);
            userRepository.save(user);
            return "success";
        } else {
            return "user not exist";
        }

    }

    @Override
    public String userAccountModify(User user, Double modify) {
        try {
            user.setAccount(modify);

            userRepository.save(user);
            return "success";
        }catch (Exception e){
            return e.toString();
        }
    }

    @Override
    public String userNameModifyChain(String username, String modify) {
        //TODO
        return null;
    }

    @Override
    public String userPasswdModifyChain(String username, String modify) {
        //TODO
        return null;
    }

    @Override
    public List<Credit> getReleaseCreditList(Long id) {
        return (List<Credit>)getById(id).getCredits();
    }


    @Override
    public User getByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    @Override
    public void updateStatus(User user, UserStatus userStatus) {
        //TODO
    }

    @Override
    public Long getUserCreditScore(User user){
        return Integer.toUnsignedLong(1000);
    }

    @Override
    public List<Credit> getBuyCreditList(Long id) {
        User user = getById(id);

        List<CreditTransaction> creditTransactionList= (List<CreditTransaction>)user.getCreditTransactions();

        List<Credit> creditList = new ArrayList<Credit>();
        for (CreditTransaction creditTransaction:creditTransactionList) {
            creditList.add(creditTransaction.getCredit());
        }
        return creditList;
    }

    @Override
    public List<Credit> getReleaseCreditListByUsername(String username) {
        return (List<Credit>)getByUsername(username).getCredits();
    }

    @Override
    public List<Credit> getBuyCreditListByUsername(String username) {
        User user = getByUsername(username);
        List<CreditTransaction> creditTransactionList= (List<CreditTransaction>)user.getCreditTransactions();

        List<Credit> creditList = new ArrayList<Credit>();
        for (CreditTransaction creditTransaction:creditTransactionList) {
            creditList.add(creditTransaction.getCredit());
        }
        return creditList;
    }
}
