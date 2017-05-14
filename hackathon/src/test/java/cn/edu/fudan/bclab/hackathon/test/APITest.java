package cn.edu.fudan.bclab.hackathon.test;

import cn.edu.fudan.bclab.hackathon.entity.*;
import cn.edu.fudan.bclab.hackathon.service.LoanInfoServiceImpl;
import cn.edu.fudan.bclab.hackathon.service.P2PService;
import cn.edu.fudan.bclab.hackathon.service.P2PServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by bxliu on 2017/5/14.
 */
public class APITest {


    @Autowired
    private BlockchainProperties blockchainProperties;

    @Autowired
    private P2PServiceImpl p2pService;



    public void runTest() {
        LoanInfoServiceImpl loanInfoService = new LoanInfoServiceImpl();


        BlockchainContract bcc = new BlockchainContract();

        bcc.setAccount("0x975da261b2b5deda4dee65c71ddb09617f49e001");
        bcc.setPassphrase("bclab123");
        bcc.setPrivkey("94d5276c8f67d3abc31f932d89d1a9518c272abe147087cd02707c000e7b7a57");
        bcc.setAbiDefinition("[{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"loanAmounts\",\"outputs\":[{\"name\":\"amount\",\"type\":\"uint256\"},{\"name\":\"id\",\"type\":\"uint256\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"key\",\"type\":\"uint256\"},{\"name\":\"_amount\",\"type\":\"uint256\"},{\"name\":\"_id\",\"type\":\"uint256\"}],\"name\":\"setAmount\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"key\",\"type\":\"uint256\"}],\"name\":\"getAmount\",\"outputs\":[{\"name\":\"_amount\",\"type\":\"uint256\"}],\"payable\":false,\"type\":\"function\"}]\n");
        bcc.setCode("6060604052341561000c57fe5b5b6101a98061001c6000396000f30060606040526000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630c4b772e146100515780634cfb22de1461008c5780639980ec86146100be575bfe5b341561005957fe5b61006f60048080359060200190919050506100f2565b604051808381526020018281526020019250505060405180910390f35b341561009457fe5b6100bc6004808035906020019091908035906020019091908035906020019091905050610116565b005b34156100c657fe5b6100dc600480803590602001909190505061015c565b6040518082815260200191505060405180910390f35b60006020528060005260406000206000915090508060000154908060010154905082565b604060405190810160405280838152602001828152506000600085815260200190815260200160002060008201518160000155602082015181600101559050505b505050565b6000600060008381526020019081526020016000206000015490505b9190505600a165627a7a7230582053ca098a33ead3d7647eedac4182b96bf396c6706082ac03d2a149746542533d0029");
        bcc.setMethod("getAmount");


        String[] params = new String[1];


        params[0] = "77";

        bcc.setParams(params);



        String result = loanInfoService.getAmount(bcc);

    }

    //@Test
    public void runContractCreateTest() {
        P2PServiceImpl p2pService = new P2PServiceImpl();

        User user = new User();
        user.setUserChainAdress("0x975da261b2b5deda4dee65c71ddb09617f49e001");
        user.setUserPassPhrase("bclab123");
        user.setUserPrivateKey("94d5276c8f67d3abc31f932d89d1a9518c272abe147087cd02707c000e7b7a57");

        AnlinkContractCreateResult result = p2pService.initContract("0x71b7dd369d71768c087a8e517cdcc4fd72e44829",100000,24,7,user);
        System.out.println("result:"+result.toString());
    }

//    @Test
    public void runContractPayTest() {
        P2PServiceImpl p2pService = new P2PServiceImpl();

        User user = new User();
        user.setUserChainAdress("0x731c194ced360709a3e617377a0300dd3610bc7f");
        user.setUserPassPhrase("bclab123");
        user.setUserPrivateKey("3eefc7005e14b9f5964a78de033189ccba432a97194181d846b73ca567050730");

        //p2pService.pay("0x731c194ced360709a3e617377a0300dd3610bc7f",1000,user);

    }
//
//    @Test
//    public void runCurrentStageTest() {
//        P2PServiceImpl p2pService = new P2PServiceImpl();
//
//        p2pService.currentStage();
//    }

    @Test
    public void runCrossChainInfoTest() {
        P2PServiceImpl p2pService = new P2PServiceImpl();
        Credit credit = new Credit();
        credit.setCreditChainAdress("0x79d2082d353126dc5df411e5f9069e8068bd6416");
        //p2pService.getConf("CrossChainLoanCheckInfo",credit);
        p2pService.getCheckLoanConf(credit);
    }




}
