package cn.edu.fudan.bclab.hackathon.service;


import cn.edu.fudan.bclab.hackathon.entity.AnlinkContractCreateResult;
import cn.edu.fudan.bclab.hackathon.entity.AnlinkContractResult;
import cn.edu.fudan.bclab.hackathon.entity.BlockchainContract;

/**
 * Created by 文捷 on 2017/4/30.
 */
public interface ChainService {
    String compileSolidity(String contract);

    AnlinkContractCreateResult createContract(BlockchainContract bcContract);

    AnlinkContractResult callContract(BlockchainContract bcContract);

    AnlinkContractResult readContract(BlockchainContract bcContract);


}
