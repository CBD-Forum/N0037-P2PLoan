package cn.edu.fudan.bclab.hackathon.service;

import cn.edu.fudan.bclab.hackathon.entity.AnlinkContractCreateResult;
import cn.edu.fudan.bclab.hackathon.entity.Credit;
import cn.edu.fudan.bclab.hackathon.entity.User;

/**
 * Created by 文捷 on 2017/5/14.
 */
public interface P2PService {


    AnlinkContractCreateResult initContract(String _borrower, int _loanGoal, int _duration, int _interest, User user);
    String currentStage(Credit credit);
    boolean checkUser(User user, Credit credit);
    boolean checkLoan(User user, Credit credit);

    String pay(String _lender, int _amount, User user, Credit credit);
    void checkGoalReached(Credit credit);
    void repay(String _borrower, int _repay, Credit credit);

}
