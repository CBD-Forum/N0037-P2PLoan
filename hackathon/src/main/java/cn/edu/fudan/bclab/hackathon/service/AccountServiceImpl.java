package cn.edu.fudan.bclab.hackathon.service;

import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.fudan.bclab.hackathon.entity.*;
import cn.edu.fudan.bclab.hackathon.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

import cn.edu.fudan.bclab.hackathon.util.BlockchainUtil;
import cn.edu.fudan.bclab.hackathon.util.ErrorHolder;

@Service("accountService")
@EnableConfigurationProperties({BlockchainProperties.class})
public class AccountServiceImpl implements AccountService{

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private BlockchainProperties blockchainProperties;

    /**
     * invoking Ethereum JSON-RPC API to unlock account
     * @return unlock result
     */
    public Boolean unlockEthAccount() {
        List<BlockchainProperties.BcInfo> bcInfos= blockchainProperties.getBcInfos();
        BlockchainProperties.BcInfo bcInfo = bcInfos.get(BlockchainUtil.getBlockchainType());
        String methodName = bcInfo.getAccountMethod();
        Object[] params = new Object[]{bcInfo.getAccount(), bcInfo.getPassword()};

        Boolean result = false;
        long start = System.currentTimeMillis();

        try {
            logger.info("start time: {}", start);
            JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(bcInfo.getClientUrl()));
            result = client.invoke(methodName, params, Boolean.class);
            logger.info("finish time : {}",System.currentTimeMillis());
            System.out.println("unlock result:" + result);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;

    }

    /**
     * invoking Fabric REST api to register account
     * @return register result
     */
    public Boolean registerFabricAccount() {
        try {
            List<BlockchainProperties.BcInfo> bcInfos = blockchainProperties.getBcInfos();
            BlockchainProperties.BcInfo bcInfo = bcInfos.get(BlockchainUtil.getBlockchainType());
            String methodName = bcInfo.getAccountMethod();
            RestTemplate restTemplate = new RestTemplate();
            FabricEnroll enroll = new FabricEnroll();
            enroll.setEnrollId(bcInfo.getAccount());
            enroll.setEnrollSecret(bcInfo.getPassword());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
            HttpEntity<FabricEnroll> request = new HttpEntity<FabricEnroll>(enroll,headers);
            Object result = restTemplate.exchange(bcInfo.getClientUrl() + "/" + methodName, HttpMethod.POST,request, Object.class);
            logger.info(result.toString());
            if (result.toString().indexOf("OK") > 0)
                return true;

        }catch (HttpClientErrorException e)
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

        }catch (Exception e){
            logger.error("e-error: {}",e.getMessage());
        }
        return false;

    }

    /**
     * invoking anlink api to create account
     */
//    public Boolean createAnlinkAccount() {
    public AnlinkAccount createAnlinkAccount(String passphrase) {

        AnlinkAccount anlinkAccount = new AnlinkAccount();

        try{

            List<BlockchainProperties.BcInfo> bcInfos = blockchainProperties.getBcInfos();

            BlockchainProperties.BcInfo bcInfo = bcInfos.get(BlockchainUtil.getBlockchainType());

            String methodName = bcInfo.getAccountMethod();

            RestTemplate restTemplate = new RestTemplate();

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            FabricEnroll enroll = new FabricEnroll();

            enroll.setEnrollId(bcInfo.getAccount());

            enroll.setEnrollSecret(bcInfo.getPassword());

//            enroll.setEnrollSecret(passphrase);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
            HttpEntity<FabricEnroll> request = new HttpEntity<FabricEnroll>(enroll,headers);
            ResponseEntity<String> accountResult = restTemplate.exchange(bcInfo.getClientUrl() + "/account/" + methodName, HttpMethod.POST,request, String.class);
//            ResponseEntity<AnlinkResponse> result = restTemplate.exchange(bcInfo.getClientUrl() + "/account/" + methodName, HttpMethod.POST,request, AnlinkResponse.class);
            logger.info(String.valueOf(accountResult));
            logger.info(String.valueOf(accountResult.getBody()));
            String accountResponse = accountResult.getBody();
            if (accountResult.getStatusCode()== HttpStatus.OK){
//                JSONObject accountInfo = new JSONObject(accountResult.getBody());
                ObjectMapper mapper = new ObjectMapper();
                AnlinkResponse anlinkResponse = mapper.readValue(accountResponse,AnlinkResponse.class);
                String jsonInString = mapper.writeValueAsString(anlinkResponse.getResult());
                logger.info("jsonString is {}",jsonInString);
                anlinkAccount = mapper.readValue(jsonInString,AnlinkAccount.class);
                return anlinkAccount;
            }

        }catch (HttpClientErrorException e)
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

        }catch (Exception e){
            logger.error("e-error: {}",e.getMessage());
        }
        return anlinkAccount;
    }

    public BlockchainAccount createAccount(String passwd) {
        BlockchainAccount bcAccount = new BlockchainAccount();
        try {
            List<BlockchainProperties.BcInfo> bcInfos = blockchainProperties.getBcInfos();
            int blockchainType = BlockchainUtil.getBlockchainType();
            BlockchainProperties.BcInfo bcInfo = bcInfos.get(BlockchainUtil.getBlockchainType());
            String methodName = bcInfo.getAccountMethod();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("passphrase", passwd);

            if (blockchainType == 0) {

            } else if (blockchainType == 1) {

            } else if (blockchainType == 2) {
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
                HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(params, headers);
                ResponseEntity<String> accountResult = restTemplate.exchange(bcInfo.getClientUrl() + "/account/" + methodName, HttpMethod.POST,request, String.class);
//            ResponseEntity<AnlinkAccount> result = restTemplate.exchange(bcInfo.getClientUrl() + "/account/" + methodName, HttpMethod.POST,request, AnlinkAccount.class);
                logger.info(String.valueOf(accountResult));
                logger.info(String.valueOf(accountResult.getBody()));
                String accountResponse = accountResult.getBody();
                if (accountResult.getStatusCode()== HttpStatus.OK){
                    bcAccount = HttpUtil.processResponse(accountResponse,bcAccount.getClass());
                }
                return bcAccount;
            }

        }catch (HttpClientErrorException e)
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

        }catch (Exception e){
            logger.error("e-error: {}",e.getMessage());
        }

        return bcAccount;
    }

}
