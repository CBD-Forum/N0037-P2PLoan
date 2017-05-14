package cn.edu.fudan.bclab.hackathon.service;

import cn.edu.fudan.bclab.hackathon.entity.*;
import cn.edu.fudan.bclab.hackathon.util.BlockchainUtil;
import cn.edu.fudan.bclab.hackathon.util.ErrorHolder;
import cn.edu.fudan.bclab.hackathon.util.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 文捷 on 2017/5/14.
 */
@Service("p2pService")
public class P2PServiceImpl implements P2PService {
    private static final Logger logger = LoggerFactory.getLogger(P2PServiceImpl.class);

    @Autowired
    private BlockchainProperties blockchainProperties;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private LoanInfoService loanInfoService;

    private String abiDefinition = "[{\"constant\":false,\"inputs\":[],\"name\":\"checkGoalReached\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"repaymentTime\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"duration\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_loanAvail\",\"type\":\"uint256\"}],\"name\":\"loanCheck\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"sender\",\"type\":\"address\"},{\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"repay\",\"outputs\":[],\"payable\":true,\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"deadline\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"amountRepay\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"currentStage\",\"outputs\":[{\"name\":\"current\",\"type\":\"uint8\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"loanGoal\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"CrossChainLoanCheckInfo\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"amountRaised\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"borrower\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"lenders\",\"outputs\":[{\"name\":\"addr\",\"type\":\"address\"},{\"name\":\"amount\",\"type\":\"uint256\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"CrossChainUserCheckInfo\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"stage\",\"outputs\":[{\"name\":\"\",\"type\":\"uint8\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"interest\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"sender\",\"type\":\"address\"},{\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"pay\",\"outputs\":[],\"payable\":true,\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"creationTime\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"paidTime\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_score\",\"type\":\"uint256\"}],\"name\":\"userCheck\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"clean\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"inputs\":[{\"name\":\"_borrower\",\"type\":\"address\"},{\"name\":\"_loanGoal\",\"type\":\"uint256\"},{\"name\":\"_duration\",\"type\":\"uint256\"},{\"name\":\"_interest\",\"type\":\"uint256\"}],\"payable\":false,\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"backer\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"TokenTransfer\",\"type\":\"event\"}]\n";

    /**
     *
     */

    @Override
    public AnlinkContractCreateResult initContract(String _borrower, int _loanGoal, int _duration, int _interest, User user) {

        AnlinkContractCreateResult accResult = new AnlinkContractCreateResult();
        List<BlockchainProperties.BcInfo> bcInfos = blockchainProperties.getBcInfos();
        BlockchainProperties.BcInfo bcInfo = bcInfos.get(BlockchainUtil.getBlockchainType());

        String[] paramsArray = new String[4];
        paramsArray[0] = _borrower;
        paramsArray[1] = String.valueOf(_loanGoal);
        paramsArray[2] = String.valueOf(_duration);
        paramsArray[3] = String.valueOf(_interest);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("account", user.getUserChainAdress());
        params.put("passphrase", user.getUserPassPhrase());
        params.put("privkey", user.getUserPrivateKey());
        params.put("code", "6060604052604060405190810160405280600281526020017f3031000000000000000000000000000000000000000000000000000000000000815250600090805190602001906200005292919062000491565b50606060405190810160405280604081526020017f393464353237366338663637643361626333316639333264383964316139353181526020017f386332373261626531343730383763643032373037633030306537623761353781525060019080519060200190620000c792919062000491565b50604060405190810160405280600881526020017f62636c6162313233000000000000000000000000000000000000000000000000815250600290805190602001906200011692919062000491565b50604060405190810160405280600981526020017f75736572436865636b0000000000000000000000000000000000000000000000815250600390805190602001906200016592919062000491565b50606060405190810160405280602a81526020017f307839373564613236316232623564656461346465653635633731646462303981526020017f363137663439653030310000000000000000000000000000000000000000000081525060049080519060200190620001da92919062000491565b50604060405190810160405280600281526020017f3130000000000000000000000000000000000000000000000000000000000000815250600590805190602001906200022992919062000491565b50606060405190810160405280604081526020017f393464353237366338663637643361626333316639333264383964316139353181526020017f3863323732616265313437303837636430323730376330303065376237613537815250600690805190602001906200029e92919062000491565b50604060405190810160405280600881526020017f62636c616231323300000000000000000000000000000000000000000000000081525060079080519060200190620002ed92919062000491565b50604060405190810160405280600981526020017f6c6f616e436865636b0000000000000000000000000000000000000000000000815250600890805190602001906200033c92919062000491565b50606060405190810160405280602a81526020017f307839373564613236316232623564656461346465653635633731646462303981526020017f363137663439653030310000000000000000000000000000000000000000000081525060099080519060200190620003b192919062000491565b506000600a60006101000a81548160ff02191690836005811115620003d257fe5b021790555042600b553415620003e457fe5b60405160808062001eef833981016040528080519060200190919080519060200190919080519060200190919080519060200190919050505b83600e60006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555082600f819055506213c68042016012819055506201518082024201601381905550806014819055505b5050505062000540565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10620004d457805160ff191683800117855562000505565b8280016001018555821562000505579182015b8281111562000504578251825591602001919060010190620004e7565b5b50905062000514919062000518565b5090565b6200053d91905b80821115620005395760008160009055506001016200051f565b5090565b90565b61199f80620005506000396000f3006060604052361561011b576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806301cb3b201461011d578063075a36ed1461012f5780630fb5a6b4146101555780631eea94951461017b57806322867d78146101b357806329dcb0cf146101ea5780634d35340d146102105780635bf5d54c146102365780636f0a7f711461026a578063733334cb146102905780637b3e5e7b146105115780637df1f1b914610537578063929eea2114610589578063a5650eff146105f0578063c040e6b814610871578063c392f766146108a5578063c4076876146108cb578063d8270dce14610902578063eb1e72dc14610928578063f3a4d6581461094e578063fc4333cd14610986575bfe5b341561012557fe5b61012d610998565b005b341561013757fe5b61013f610acd565b6040518082815260200191505060405180910390f35b341561015d57fe5b610165610ad3565b6040518082815260200191505060405180910390f35b341561018357fe5b6101996004808035906020019091905050610ad9565b604051808215151515815260200191505060405180910390f35b6101e8600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091908035906020019091905050610b46565b005b34156101f257fe5b6101fa610e34565b6040518082815260200191505060405180910390f35b341561021857fe5b610220610e3a565b6040518082815260200191505060405180910390f35b341561023e57fe5b610246610e40565b6040518082600581111561025657fe5b60ff16815260200191505060405180910390f35b341561027257fe5b61027a610e58565b6040518082815260200191505060405180910390f35b341561029857fe5b6102a0610e5e565b60405180806020018060200180602001806020018060200186810386528b8181518152602001915080519060200190808383600083146102ff575b8051825260208311156102ff576020820191506020810190506020830392506102db565b505050905090810190601f16801561032b5780820380516001836020036101000a031916815260200191505b5086810385528a818151815260200191508051906020019080838360008314610373575b8051825260208311156103735760208201915060208101905060208303925061034f565b505050905090810190601f16801561039f5780820380516001836020036101000a031916815260200191505b508681038452898181518152602001915080519060200190808383600083146103e7575b8051825260208311156103e7576020820191506020810190506020830392506103c3565b505050905090810190601f1680156104135780820380516001836020036101000a031916815260200191505b5086810383528881815181526020019150805190602001908083836000831461045b575b80518252602083111561045b57602082019150602081019050602083039250610437565b505050905090810190601f1680156104875780820380516001836020036101000a031916815260200191505b508681038252878181518152602001915080519060200190808383600083146104cf575b8051825260208311156104cf576020820191506020810190506020830392506104ab565b505050905090810190601f1680156104fb5780820380516001836020036101000a031916815260200191505b509a505050505050505050505060405180910390f35b341561051957fe5b6105216111ae565b6040518082815260200191505060405180910390f35b341561053f57fe5b6105476111b4565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561059157fe5b6105a760048080359060200190919050506111da565b604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018281526020019250505060405180910390f35b34156105f857fe5b61060061122e565b60405180806020018060200180602001806020018060200186810386528b81815181526020019150805190602001908083836000831461065f575b80518252602083111561065f5760208201915060208101905060208303925061063b565b505050905090810190601f16801561068b5780820380516001836020036101000a031916815260200191505b5086810385528a8181518152602001915080519060200190808383600083146106d3575b8051825260208311156106d3576020820191506020810190506020830392506106af565b505050905090810190601f1680156106ff5780820380516001836020036101000a031916815260200191505b50868103845289818151815260200191508051906020019080838360008314610747575b80518252602083111561074757602082019150602081019050602083039250610723565b505050905090810190601f1680156107735780820380516001836020036101000a031916815260200191505b508681038352888181518152602001915080519060200190808383600083146107bb575b8051825260208311156107bb57602082019150602081019050602083039250610797565b505050905090810190601f1680156107e75780820380516001836020036101000a031916815260200191505b5086810382528781815181526020019150805190602001908083836000831461082f575b80518252602083111561082f5760208201915060208101905060208303925061080b565b505050905090810190601f16801561085b5780820380516001836020036101000a031916815260200191505b509a505050505050505050505060405180910390f35b341561087957fe5b61088161157e565b6040518082600581111561089157fe5b60ff16815260200191505060405180910390f35b34156108ad57fe5b6108b5611591565b6040518082815260200191505060405180910390f35b610900600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091908035906020019091905050611597565b005b341561090a57fe5b6109126117b6565b6040518082815260200191505060405180910390f35b341561093057fe5b6109386117bc565b6040518082815260200191505060405180910390f35b341561095657fe5b61096c60048080359060200190919050506117c2565b604051808215151515815260200191505060405180910390f35b341561098e57fe5b61099661182e565b005b60038060058111156109a657fe5b600a60009054906101000a900460ff1660058111156109c157fe5b1415156109ce5760006000fd5b600e60009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc6010549081150290604051809050600060405180830381858888f193505050501515610a3257fe5b7f1d72140a7ccdb496bef50918d2598dd7edefb514f70e6d4beb528cbd16c80b7d600e60009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16601054604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018281526020019250505060405180910390a142600c819055505b5b50565b600d5481565b60135481565b60006001806005811115610ae957fe5b600a60009054906101000a900460ff166005811115610b0457fe5b141515610b115760006000fd5b600f5483111515610b2d57610b24611869565b60019150610b3f565b610b356118b7565b60009150610b3f565b5b5b50919050565b6000600060026005811115610b5757fe5b600a60009054906101000a900460ff166005811115610b7257fe5b148015610b8a57506201518060125402600b54014210155b15610b9857610b97611869565b5b60036005811115610ba557fe5b600a60009054906101000a900460ff166005811115610bc057fe5b148015610bd857506201518060135402600c54014210155b15610be657610be5611869565b5b6004806005811115610bf457fe5b600a60009054906101000a900460ff166005811115610c0f57fe5b141515610c1c5760006000fd5b600e60009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168573ffffffffffffffffffffffffffffffffffffffff161415610e2a5783601160008282540192505081905550601454600101601054026011541415610e2957600092505b601580549050831015610e20576064601454606401601585815481101515610cbe57fe5b906000526020600020906002020160005b506001015402811515610cde57fe5b049150601583815481101515610cf057fe5b906000526020600020906002020160005b5060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc839081150290604051809050600060405180830381858888f193505050501515610d6557fe5b7f1d72140a7ccdb496bef50918d2598dd7edefb514f70e6d4beb528cbd16c80b7d601584815481101515610d9557fe5b906000526020600020906002020160005b5060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1683604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018281526020019250505060405180910390a15b826001019250610c9a565b610e28611869565b5b5b5b5b505b50505050565b60125481565b60115481565b6000600a60009054906101000a900460ff1690505b90565b600f5481565b610e666118de565b610e6e6118de565b610e766118de565b610e7e6118de565b610e866118de565b60056006600760086009848054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610f255780601f10610efa57610100808354040283529160200191610f25565b820191906000526020600020905b815481529060010190602001808311610f0857829003601f168201915b50505050509450838054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610fc15780601f10610f9657610100808354040283529160200191610fc1565b820191906000526020600020905b815481529060010190602001808311610fa457829003601f168201915b50505050509350828054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561105d5780601f106110325761010080835404028352916020019161105d565b820191906000526020600020905b81548152906001019060200180831161104057829003601f168201915b50505050509250818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156110f95780601f106110ce576101008083540402835291602001916110f9565b820191906000526020600020905b8154815290600101906020018083116110dc57829003601f168201915b50505050509150808054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156111955780601f1061116a57610100808354040283529160200191611195565b820191906000526020600020905b81548152906001019060200180831161117857829003601f168201915b50505050509050945094509450945094505b9091929394565b60105481565b600e60009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6015818154811015156111e957fe5b906000526020600020906002020160005b915090508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060010154905082565b6112366118de565b61123e6118de565b6112466118de565b61124e6118de565b6112566118de565b60006001600260036004848054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156112f55780601f106112ca576101008083540402835291602001916112f5565b820191906000526020600020905b8154815290600101906020018083116112d857829003601f168201915b50505050509450838054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156113915780601f1061136657610100808354040283529160200191611391565b820191906000526020600020905b81548152906001019060200180831161137457829003601f168201915b50505050509350828054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561142d5780601f106114025761010080835404028352916020019161142d565b820191906000526020600020905b81548152906001019060200180831161141057829003601f168201915b50505050509250818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156114c95780601f1061149e576101008083540402835291602001916114c9565b820191906000526020600020905b8154815290600101906020018083116114ac57829003601f168201915b50505050509150808054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156115655780601f1061153a57610100808354040283529160200191611565565b820191906000526020600020905b81548152906001019060200180831161154857829003601f168201915b50505050509050945094509450945094505b9091929394565b600a60009054906101000a900460ff1681565b60145481565b600260058111156115a457fe5b600a60009054906101000a900460ff1660058111156115bf57fe5b1480156115d757506201518060125402600b54014210155b156115e5576115e4611869565b5b600360058111156115f257fe5b600a60009054906101000a900460ff16600581111561160d57fe5b14801561162557506201518060135402600c54014210155b1561163357611632611869565b5b600280600581111561164157fe5b600a60009054906101000a900460ff16600581111561165c57fe5b1415156116695760006000fd5b6040604051908101604052808473ffffffffffffffffffffffffffffffffffffffff168152602001838152506015601580548091906001016116ab91906118f2565b8154811015156116b757fe5b906000526020600020906002020160005b5060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010155905050816010600082825401925050819055507f1d72140a7ccdb496bef50918d2598dd7edefb514f70e6d4beb528cbd16c80b7d8383604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018281526020019250505060405180910390a1600f546010541015156117ae576117ad611869565b5b5b5b505b5050565b600b5481565b600c5481565b600060008060058111156117d257fe5b600a60009054906101000a900460ff1660058111156117ed57fe5b1415156117fa5760006000fd5b6101f48311156118155761180c611869565b60019150611827565b61181d6118b7565b60009150611827565b5b5b50919050565b600580600581111561183c57fe5b600a60009054906101000a900460ff16600581111561185757fe5b1415156118645760006000fd5b5b5b50565b6001600a60009054906101000a900460ff16600581111561188657fe5b01600581111561189257fe5b600a60006101000a81548160ff021916908360058111156118af57fe5b02179055505b565b6005600a60006101000a81548160ff021916908360058111156118d657fe5b02179055505b565b602060405190810160405280600081525090565b81548183558181151161191f5760020281600202836000526020600020918201910161191e9190611924565b5b505050565b61197091905b8082111561196c5760006000820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff021916905560018201600090555060020161192a565b5090565b905600a165627a7a723058207dbe78fe2eb2f84e9fa505d4daa590f6d589771f1d86e52de4f0e2dc4cc7be6c0029");
        params.put("abiDefinition", abiDefinition);
        params.put("method", "");
        params.put("params", paramsArray);

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(params, headers);
            ResponseEntity<String> contractResult = restTemplate.exchange(bcInfo.getClientUrl() + "/contract/create", HttpMethod.POST, request, String.class);
            logger.info(String.valueOf(contractResult));
            logger.info(String.valueOf(contractResult.getBody()));
            String callResponse = contractResult.getBody();

            if (contractResult.getStatusCode() == HttpStatus.OK) {
                accResult = HttpUtil.processResponse(callResponse, accResult.getClass());
            }
            return accResult;
        } catch (HttpClientErrorException e) {
            /**
             *
             * If we get a HTTP Exception display the error message
             */
            logger.error("error:  " + e.getResponseBodyAsString());
            ObjectMapper mapper = new ObjectMapper();
            try {
                ErrorHolder eh = mapper.readValue(e.getResponseBodyAsString(), ErrorHolder.class);
                logger.error("eh-error:  {}", eh.getErrorMessage());
            } catch (Exception ioe) {
                logger.error("ioe-error: {}", ioe.getMessage());
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String currentStage(Credit credit) {
        String result = "";
        List<BlockchainProperties.BcInfo> bcInfos = blockchainProperties.getBcInfos();
        BlockchainProperties.BcInfo bcInfo = bcInfos.get(BlockchainUtil.getBlockchainType());


        Map<String, Object> params = new HashMap<String, Object>();
        params.put("account", "0x71b7dd369d71768c087a8e517cdcc4fd72e44829");
        params.put("passphrase", "bclab123");
        params.put("privkey", "4d0de5ba5ff66c368f2953a982cbfe7bfdcbacce67df04041b1483afed3dec84");
        params.put("contract", credit.getCreditChainAdress());
        params.put("abiDefinition", abiDefinition);
        params.put("method", "currentStage");
        params.put("params", "");

        try {
            ObjectMapper mapper = new ObjectMapper();
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(params, headers);
            logger.info("params is {}", params);
            logger.info("request is {}", request.toString());
            ResponseEntity<String> contractResult = restTemplate.exchange(bcInfo.getClientUrl() + "/contract/read", HttpMethod.POST, request, String.class);

            logger.info(String.valueOf(contractResult));
            logger.info(String.valueOf(contractResult.getBody()));
            String callResponse = contractResult.getBody();

            if (contractResult.getStatusCode() == HttpStatus.OK) {
                AnlinkResponse anlinkResponse = mapper.readValue(callResponse, AnlinkResponse.class);
                result = anlinkResponse.getResult().toString();
            }

        } catch (HttpClientErrorException e) {
            /**
             *
             * If we get a HTTP Exception display the error message
             */
            logger.error("error:  " + e.getResponseBodyAsString());
            ObjectMapper mapper = new ObjectMapper();
            try {
                ErrorHolder eh = mapper.readValue(e.getResponseBodyAsString(), ErrorHolder.class);
                logger.error("eh-error:  {}", eh.getErrorMessage());
            } catch (Exception ioe) {
                logger.error("ioe-error: {}", ioe.getMessage());
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean checkLoan(User user, Credit credit) {

        ArrayList param = getCheckLoanConf(credit);

        String score = getUserLoan(param, user.getUserId().toString());

        String result = setUserLoan(user, score, credit);

        if (result.equals("true"))
            return true;
        else
            return false;
    }


    @Override
    public boolean checkUser(User user, Credit credit) {

        ArrayList param = getCheckUserConf(credit);

        String score = getUserScore(param, user.getUserId().toString());

        String result = setUserScore(user, score, credit);

        if (result.equals("true"))
            return true;
        else
            return false;
    }

    public String setUserLoan(User user, String score, Credit credit) {

        String result = "";
        //List<BlockchainProperties.BcInfo> bcInfos = blockchainProperties.getBcInfos();
        //BlockchainProperties.BcInfo bcInfo = bcInfos.get(BlockchainUtil.getBlockchainType());

        String[] paramsArray = new String[1];
        paramsArray[0] = score;


        Map<String, Object> params = new HashMap<String, Object>();
        params.put("account", user.getUserChainAdress());
        params.put("passphrase", user.getUserPassPhrase());
        params.put("privkey", user.getUserPrivateKey());
        params.put("contract", credit.getCreditChainAdress());
        params.put("abiDefinition", abiDefinition);
        params.put("method", "loanCheck");
        params.put("params", paramsArray);

        try {
            ObjectMapper mapper = new ObjectMapper();
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(params, headers);
            logger.info("params is {}", params);
            logger.info("request is {}", request.toString());
            ResponseEntity<String> contractResult = restTemplate.exchange("http://10.253.14.131/v1" + "/contract/call", HttpMethod.POST, request, String.class);

            logger.info(String.valueOf(contractResult));
            logger.info(String.valueOf(contractResult.getBody()));
            String callResponse = contractResult.getBody();

            if (contractResult.getStatusCode() == HttpStatus.OK) {
                AnlinkResponse anlinkResponse = mapper.readValue(callResponse, AnlinkResponse.class);

                result = anlinkResponse.getResult().toString();
            }

        } catch (HttpClientErrorException e) {
            /**
             *
             * If we get a HTTP Exception display the error message
             */
            logger.error("error:  " + e.getResponseBodyAsString());
            ObjectMapper mapper = new ObjectMapper();
            try {
                ErrorHolder eh = mapper.readValue(e.getResponseBodyAsString(), ErrorHolder.class);
                logger.error("eh-error:  {}", eh.getErrorMessage());
            } catch (Exception ioe) {
                logger.error("ioe-error: {}", ioe.getMessage());
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return result;
    }

    public String setUserScore(User user, String score, Credit credit) {
        String result = "";
        //List<BlockchainProperties.BcInfo> bcInfos = blockchainProperties.getBcInfos();
        //BlockchainProperties.BcInfo bcInfo = bcInfos.get(BlockchainUtil.getBlockchainType());

        String[] paramsArray = new String[1];
        paramsArray[0] = score;


        Map<String, Object> params = new HashMap<String, Object>();
        params.put("account", user.getUserChainAdress());
        params.put("passphrase", user.getUserPassPhrase());
        params.put("privkey", user.getUserPrivateKey());
        params.put("contract", credit.getCreditChainAdress());
        params.put("abiDefinition", abiDefinition);
        params.put("method", "userCheck");
        params.put("params", paramsArray);

        try {
            ObjectMapper mapper = new ObjectMapper();
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(params, headers);
            logger.info("params is {}", params);
            logger.info("request is {}", request.toString());
            ResponseEntity<String> contractResult = restTemplate.exchange("http://10.253.14.131/v1" + "/contract/call", HttpMethod.POST, request, String.class);

            logger.info(String.valueOf(contractResult));
            logger.info(String.valueOf(contractResult.getBody()));
            String callResponse = contractResult.getBody();

            if (contractResult.getStatusCode() == HttpStatus.OK) {
                AnlinkResponse anlinkResponse = mapper.readValue(callResponse, AnlinkResponse.class);

                result = anlinkResponse.getResult().toString();
            }

        } catch (HttpClientErrorException e) {
            /**
             *
             * If we get a HTTP Exception display the error message
             */
            logger.error("error:  " + e.getResponseBodyAsString());
            ObjectMapper mapper = new ObjectMapper();
            try {
                ErrorHolder eh = mapper.readValue(e.getResponseBodyAsString(), ErrorHolder.class);
                logger.error("eh-error:  {}", eh.getErrorMessage());
            } catch (Exception ioe) {
                logger.error("ioe-error: {}", ioe.getMessage());
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return result;
    }

    public String getUserScore(ArrayList param, String id) {
        return userInfoService.getAmount(param, id);
    }

    public String getUserLoan(ArrayList param, String id) {
        return loanInfoService.getAmount(param, id);
    }


    public ArrayList getCheckLoanConf(Credit credit) {
        return getConf("CrossChainLoanCheckInfo", credit);

    }

    public ArrayList getCheckUserConf(Credit credit) {
        return getConf("CrossChainUserCheckInfo", credit);
    }

    private ArrayList getConf(String crossInfo, Credit credit) {
        ArrayList result = new ArrayList();
        //List<BlockchainProperties.BcInfo> bcInfos = blockchainProperties.getBcInfos();
        //BlockchainProperties.BcInfo bcInfo = bcInfos.get(BlockchainUtil.getBlockchainType());

        String[] paramArray = new String[0];

        logger.info("credit contract is {}",credit.getCreditChainAdress());
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("account", "0x71b7dd369d71768c087a8e517cdcc4fd72e44829");
        params.put("passphrase", "bclab123");
        params.put("privkey", "4d0de5ba5ff66c368f2953a982cbfe7bfdcbacce67df04041b1483afed3dec84");
        params.put("contract", credit.getCreditChainAdress());
        params.put("abiDefinition", abiDefinition);
        params.put("method", crossInfo);
        params.put("params", paramArray);

        logger.info("credit params is {}",params.toString());

        try {
            ObjectMapper mapper = new ObjectMapper();
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(params, headers);
            logger.info("params is {}", params);
            logger.info("request is {}", request.toString());
            ResponseEntity<String> contractResult = restTemplate.exchange("http://10.253.14.131/v1" + "/contract/read", HttpMethod.POST, request, String.class);

            logger.info(String.valueOf(contractResult));
            logger.info(String.valueOf(contractResult.getBody()));
            String callResponse = contractResult.getBody();

            logger.info("get conf callResponse is {}",callResponse);
            if (contractResult.getStatusCode() == HttpStatus.OK) {
                AnlinkResponse anlinkResponse = mapper.readValue(callResponse, AnlinkResponse.class);

                result = (ArrayList) anlinkResponse.getResult();
            }

            logger.info("get conf result is {}",String.valueOf(result.size()));

        } catch (HttpClientErrorException e) {
            /**
             *
             * If we get a HTTP Exception display the error message
             */
            logger.error("error:  " + e.getResponseBodyAsString());
            ObjectMapper mapper = new ObjectMapper();
            try {
                ErrorHolder eh = mapper.readValue(e.getResponseBodyAsString(), ErrorHolder.class);
                logger.error("eh-error:  {}", eh.getErrorMessage());
            } catch (Exception ioe) {
                logger.error("ioe-error: {}", ioe.getMessage());
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return result;
    }




    @Override
    public String pay(String _lender, int _amount, User user, Credit credit) {


        String result = "";
        //List<BlockchainProperties.BcInfo> bcInfos = blockchainProperties.getBcInfos();
        //BlockchainProperties.BcInfo bcInfo = bcInfos.get(BlockchainUtil.getBlockchainType());

        String[] paramsArray = new String[2];
        paramsArray[0] = user.getUserChainAdress();
        paramsArray[1] = String.valueOf(_amount);


        Map<String, Object> params = new HashMap<String, Object>();
        params.put("account", user.getUserChainAdress());
        params.put("passphrase", user.getUserPassPhrase());
        params.put("privkey", user.getUserPrivateKey());
        params.put("contract", credit.getCreditChainAdress());
        params.put("abiDefinition", abiDefinition);
        params.put("method", "pay");
        params.put("params", paramsArray);

        try {
            ObjectMapper mapper = new ObjectMapper();
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(params, headers);
            logger.info("params is {}", params);
            logger.info("request is {}", request.toString());
            ResponseEntity<String> contractResult = restTemplate.exchange("http://10.253.14.131/v1" + "/contract/call", HttpMethod.POST, request, String.class);

            logger.info(String.valueOf(contractResult));
            logger.info(String.valueOf(contractResult.getBody()));
            String callResponse = contractResult.getBody();

            if (contractResult.getStatusCode() == HttpStatus.OK) {
                AnlinkResponse anlinkResponse = mapper.readValue(callResponse, AnlinkResponse.class);

                result = anlinkResponse.getResult().toString();
            }

        } catch (HttpClientErrorException e) {
            /**
             *
             * If we get a HTTP Exception display the error message
             */
            logger.error("error:  " + e.getResponseBodyAsString());
            ObjectMapper mapper = new ObjectMapper();
            try {
                ErrorHolder eh = mapper.readValue(e.getResponseBodyAsString(), ErrorHolder.class);
                logger.error("eh-error:  {}", eh.getErrorMessage());
            } catch (Exception ioe) {
                logger.error("ioe-error: {}", ioe.getMessage());
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return result;

    }

    @Override
    public void checkGoalReached(Credit credit) {
        String[] result;

        List<BlockchainProperties.BcInfo> bcInfos = blockchainProperties.getBcInfos();
        BlockchainProperties.BcInfo bcInfo = bcInfos.get(BlockchainUtil.getBlockchainType());


        Map<String, Object> params = new HashMap<String, Object>();
        params.put("account", "0x71b7dd369d71768c087a8e517cdcc4fd72e44829");
        params.put("passphrase", "bclab123");
        params.put("privkey", "4d0de5ba5ff66c368f2953a982cbfe7bfdcbacce67df04041b1483afed3dec84");
        params.put("contract", credit.getCreditChainAdress());
        params.put("abiDefinition", abiDefinition);
        params.put("method", "checkGoalReached");
        params.put("params", "");

        try {
            ObjectMapper mapper = new ObjectMapper();
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(params, headers);
            logger.info("params is {}", params);
            logger.info("request is {}", request.toString());
            ResponseEntity<String> contractResult = restTemplate.exchange(bcInfo.getClientUrl() + "/contract/read", HttpMethod.POST, request, String.class);

            String callResponse = contractResult.getBody();

            if (contractResult.getStatusCode() == HttpStatus.OK) {
                AnlinkResponse anlinkResponse = mapper.readValue(callResponse, AnlinkResponse.class);

                result = (String[]) anlinkResponse.getResult();
            }

        } catch (HttpClientErrorException e) {
            /**
             *
             * If we get a HTTP Exception display the error message
             */
            logger.error("error:  " + e.getResponseBodyAsString());
            ObjectMapper mapper = new ObjectMapper();
            try {
                ErrorHolder eh = mapper.readValue(e.getResponseBodyAsString(), ErrorHolder.class);
                logger.error("eh-error:  {}", eh.getErrorMessage());
            } catch (Exception ioe) {
                logger.error("ioe-error: {}", ioe.getMessage());
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }


    }

    @Override
    public void repay(String _borrower, int _repay, Credit credit) {

    }
}
