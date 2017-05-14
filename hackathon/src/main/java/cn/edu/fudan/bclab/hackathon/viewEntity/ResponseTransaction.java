package cn.edu.fudan.bclab.hackathon.viewEntity;

import lombok.Data;

/**
 * Created by bintan on 17-5-12.
 */
@Data
public class ResponseTransaction {

    private Long creditTransactionId;

    //交易类型
    private String type;
    //本次交易金额
    private double accountSum;

    private String time;

    private String creditTrasactionPassPhrase;

    private String creditTrasactionChainAdress;

    private String creditTrasactionPrivateKey;

}
