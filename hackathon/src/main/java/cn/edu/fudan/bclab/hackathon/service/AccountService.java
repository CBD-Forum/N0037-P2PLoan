package cn.edu.fudan.bclab.hackathon.service;

import cn.edu.fudan.bclab.hackathon.entity.AnlinkAccount;
import cn.edu.fudan.bclab.hackathon.entity.BlockchainAccount;

public interface AccountService {

    Boolean unlockEthAccount();

    Boolean registerFabricAccount();

    AnlinkAccount createAnlinkAccount(String passphrase);

    BlockchainAccount createAccount(String passwd);
}

