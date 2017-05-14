package cn.edu.fudan.bclab.hackathon.service;

import cn.edu.fudan.bclab.hackathon.entity.BlockchainContract;

import java.util.ArrayList;

/**
 * Created by 文捷 on 2017/5/12.
 */
public interface LoanInfoService {

    String setAmount(BlockchainContract bcContract);
    String getAmount(BlockchainContract bcContract);


    String setAmount(ArrayList chainParam, String id, String amount);

    String getAmount(ArrayList chainParam, String id);

}
