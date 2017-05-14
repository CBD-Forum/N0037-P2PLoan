package cn.edu.fudan.bclab.hackathon.viewEntity;

import cn.edu.fudan.bclab.hackathon.entity.Status;
import cn.edu.fudan.bclab.hackathon.entity.User;
import cn.edu.fudan.bclab.hackathon.entity.UserStatus;

import lombok.Data;

import java.util.Date;


/**
 * Created by lomo on 2017/5/11.
 */
@Data
public class RequestObject {
    //user

    private Long userId;

    private String username;

    private String password;

    private double account;

    private Long creditScore;

    private String userPassPhrase;

    private String userChainAdress;

    private String userPrivateKey;

    //credit

    private String name;
    //信贷类型
    private String creditType;
    //信贷总金额
    private double amountSum;
    //当前已募集金额
    private double currentSum;

    private double neededSum;

    private String creditReason;

    private String creditTime;

    private int longs;

    private double ratio;

    private Long neededScore;

    private int currentPersonNums;

    private int remPersonNums;

    private int personNumsLimit;

    private String creditPassPhrase;

    private String creditChainAdress;

    private String creditPrivateKey;

    //CreditTrasaction

    //交易类型
    private String type;
    //本次交易金额
    private double accountSum;

    private String time;

    private User lender;

    private String creditTrasactionPassPhrase;

    private String creditTrasactionChainAdress;

    private String creditTrasactionPrivateKey;



}
