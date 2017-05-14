package cn.edu.fudan.bclab.hackathon.service;

import cn.edu.fudan.bclab.hackathon.entity.*;
import cn.edu.fudan.bclab.hackathon.util.BlockchainUtil;
import cn.edu.fudan.bclab.hackathon.util.ErrorHolder;
import cn.edu.fudan.bclab.hackathon.util.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 文捷 on 2017/4/30.
 */
@Service("chainService")
@EnableConfigurationProperties({BlockchainProperties.class})
public class ChainServiceImpl implements ChainService {

    private static final Logger logger = LoggerFactory.getLogger(ChainServiceImpl.class);

    @Autowired
    private BlockchainProperties blockchainProperties;

    /**
     * compile smart contract by local ethereum test net
     * param contract is code of contract
     *
     * @param contract
     * @return
     * @todo resolve response by corresponding object instead of using substring now.
     */
    public String compileSolidity(String contract) {
        String code = "";
        List<BlockchainProperties.BcInfo> bcInfos = blockchainProperties.getBcInfos();
        BlockchainProperties.BcInfo bcInfo = bcInfos.get(0);
        logger.info("contract:" + contract);
        String[] methodParams = {contract};

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("jsonrpc", "2.0");
        params.put("method", "eth_compileSolidity");
        params.put("params", methodParams);
        params.put("id", "1");

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(params, headers);
            ResponseEntity<String> compileResult = restTemplate.exchange(bcInfo.getClientUrl(), HttpMethod.POST, request, String.class);
            logger.info(String.valueOf(compileResult));
            logger.info(String.valueOf(compileResult.getBody()));
            String compileResultBody = compileResult.getBody();
            if (compileResult.getStatusCode() == HttpStatus.OK) {
                String tempResult1 = compileResultBody.substring(compileResultBody.indexOf("\"code\":\"") + 8);
                String tempResult2 = tempResult1.split("\",\"")[0];
                code = tempResult2;
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return code;
    }

    public AnlinkContractCreateResult createContract(BlockchainContract bcContract) {
        AnlinkContractCreateResult contractResult = new AnlinkContractCreateResult();
        List<BlockchainProperties.BcInfo> bcInfos = blockchainProperties.getBcInfos();
        BlockchainProperties.BcInfo bcInfo = bcInfos.get(BlockchainUtil.getBlockchainType());

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("account", bcContract.getAccount());
        params.put("passphrase", bcContract.getPassphrase());
        params.put("privkey", bcContract.getPrivkey());
        params.put("code", bcContract.getCode());
        params.put("abiDefinition", bcContract.getAbiDefinition());
        params.put("params", bcContract.getParams());

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(params, headers);
            ResponseEntity<String> contractCreateResult = restTemplate.exchange(bcInfo.getClientUrl()+"/contract/create", HttpMethod.POST, request, String.class);
            logger.info(String.valueOf(contractCreateResult));
            logger.info(String.valueOf(contractCreateResult.getBody()));
            String compileResponse = contractCreateResult.getBody();

            if (contractCreateResult.getStatusCode()== HttpStatus.OK){
                contractResult = HttpUtil.processResponse(compileResponse,contractResult.getClass());
            }

        } catch (HttpClientErrorException e)
        {
            /**
             *
             * If we get a HTTP Exception display the error message
             */
            logger.error("error:  " + e.getResponseBodyAsString());
            ObjectMapper mapper = new ObjectMapper();
            try {
                ErrorHolder eh = mapper.readValue(e.getResponseBodyAsString(), ErrorHolder.class);
                logger.error("eh-error:  {}" , eh.getErrorMessage());
            }catch(Exception ioe){
                logger.error("ioe-error: {}",ioe.getMessage());
            }

        }catch (Throwable e) {
            e.printStackTrace();
        }

        return contractResult;
    }

    @Override
    public AnlinkContractResult callContract(BlockchainContract bcContract) {
        return null;
    }

    @Override
    public AnlinkContractResult readContract(BlockchainContract bcContract) {
        return null;
    }


}
