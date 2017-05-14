package cn.edu.fudan.bclab.hackathon.controller;

import cn.edu.fudan.bclab.hackathon.entity.AnlinkContractCreateResult;
import cn.edu.fudan.bclab.hackathon.entity.AnlinkContractResult;
import cn.edu.fudan.bclab.hackathon.entity.BlockchainContract;
import cn.edu.fudan.bclab.hackathon.service.LoanInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by 文捷 on 2017/5/12.
 */
@Slf4j
@Controller
@RequestMapping("/loan")
public class LoanInfoController {

    @Autowired
    private LoanInfoService loanInfoService;

    @CrossOrigin
    @RequestMapping(value="/setAmount",method = RequestMethod.POST)
    public ResponseEntity<String> setAmount(@RequestBody BlockchainContract bcContract) {
        return new ResponseEntity<String>(loanInfoService.setAmount(bcContract), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value="/getAmount",method = RequestMethod.POST)
    public ResponseEntity<String> getAmount(@RequestBody BlockchainContract bcContract) {
        return new ResponseEntity<String>(loanInfoService.getAmount(bcContract), HttpStatus.OK);
    }
//
}
