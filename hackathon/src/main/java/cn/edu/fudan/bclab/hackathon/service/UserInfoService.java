package cn.edu.fudan.bclab.hackathon.service;

import cn.edu.fudan.bclab.hackathon.entity.BlockchainContract;

import java.util.ArrayList;

/**
 * Created by bxliu on 2017/5/14.
 */
public interface UserInfoService {


    String setAmount(BlockchainContract bcContract);

    String getAmount(BlockchainContract bcContract);



    String setAmount(ArrayList chainParam, String id, String amount);

    String getAmount(ArrayList chainParam, String id);

}
