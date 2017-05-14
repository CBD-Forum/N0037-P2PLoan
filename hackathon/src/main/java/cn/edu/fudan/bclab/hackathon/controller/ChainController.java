package cn.edu.fudan.bclab.hackathon.controller;

import cn.edu.fudan.bclab.hackathon.entity.AnlinkContractCreateResult;
import cn.edu.fudan.bclab.hackathon.entity.BlockchainContract;
import org.springframework.beans.factory.annotation.Autowired;
import cn.edu.fudan.bclab.hackathon.service.ChainService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chain")
public class ChainController {

    @Autowired
    private ChainService chainService;

    @CrossOrigin
    @RequestMapping(value="/compile",method = RequestMethod.POST)
    public ResponseEntity<String> compileSolidity(@RequestBody String contract) {
//    public ResponseEntity<String> compileSolidity(@RequestParam(value = "contract", required = true)String contract) {
        System.out.println("RequestObject contract:"+contract);
        return new ResponseEntity<String>(chainService.compileSolidity(contract), HttpStatus.OK);

    }

    @CrossOrigin
    @RequestMapping(value="/contract/create",method = RequestMethod.POST)
    public ResponseEntity<AnlinkContractCreateResult> createContract(@RequestBody BlockchainContract bcContract) {
        return new ResponseEntity<AnlinkContractCreateResult>(chainService.createContract(bcContract),HttpStatus.OK);
    }



}
