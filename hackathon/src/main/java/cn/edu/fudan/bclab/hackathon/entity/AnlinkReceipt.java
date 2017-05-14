package cn.edu.fudan.bclab.hackathon.entity;

import lombok.Data;

/**
 * Created by 文捷 on 2017/5/3.
 * the result of transaction querying
 */
@Data
public class AnlinkReceipt {

    private String PostState;
    private String CumulativeGasUsed;
    private String Bloom;
    private String Logs;
    private String TxHash;
    private String ContractAddress;
    private String GasUsed;

}
