package cn.edu.fudan.bclab.hackathon.controller;

import cn.edu.fudan.bclab.hackathon.entity.BlockchainAccount;
import cn.edu.fudan.bclab.hackathon.service.AccountService;
import cn.edu.fudan.bclab.hackathon.util.BlockchainUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jwen on 2017/2/16.
 */

@Slf4j
@Controller
@RequestMapping("/account")
public class AccountController {
    private final AtomicInteger counter = new AtomicInteger(0);

    @Autowired
    private AccountService accountService;


    @CrossOrigin
    @RequestMapping(value="/unlock/{password}",method = RequestMethod.GET)
//    public ResponseEntity<Boolean> unlockAccount() {
    public ResponseEntity<BlockchainAccount> unlockAccount(@PathVariable(value = "password", required = true)String password) {
//        int blockchainType = BlockchainUtil.getBlockchainType();
//        System.out.println("blockchainType is :"+String.valueOf(blockchainType));
//        if(blockchainType==0)
//            return new ResponseEntity<Boolean>(accountService.unlockEthAccount(), HttpStatus.OK);
//        else if(blockchainType==1)
//            return new ResponseEntity<Boolean>(accountService.registerFabricAccount(), HttpStatus.OK);
//        else
//            return new ResponseEntity<BlockchainAccount>(accountService.createAnlinkAccount(password), HttpStatus.OK);
//            return new ResponseEntity<Boolean>(accountService.createAnlinkAccount(), HttpStatus.OK);
            return new ResponseEntity<BlockchainAccount>(accountService.createAccount(password), HttpStatus.OK);
    }

    /**
     * This function invokes Eth personal api to unlock account asynchronously.
     * Note:this method can only be used in test net.
     * @return
     */
    @CrossOrigin
    @RequestMapping(value="/async_unlock",method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<?>> asyncUnlockAccount() {
        DeferredResult<ResponseEntity<?>> result = new DeferredResult<>() ;

        new Thread(() -> {
            result.setResult(ResponseEntity.ok(accountService.unlockEthAccount()));
        },"OriNewsThread-" + counter.incrementAndGet()).start();

        return result;
    }
}
