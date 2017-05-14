package cn.edu.fudan.bclab.hackathon.viewEntity;

import cn.edu.fudan.bclab.hackathon.entity.CreditStatus;
import lombok.Data;

/**
 * Created by bintan on 17-5-12.
 */

@Data
public class ResponseCredit {

    private Long creditId;

    //信贷名称
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

    private CreditStatus creditStatus;

    private String digest;

    private int currentPersonNums;

    private String creditPassPhrase;

    private String creditChainAdress;

    private String creditPrivateKey;


}
