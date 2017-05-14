package cn.edu.fudan.bclab.hackathon.service;

import cn.edu.fudan.bclab.hackathon.entity.AnlinkResponse;
import cn.edu.fudan.bclab.hackathon.entity.BlockchainContract;
import cn.edu.fudan.bclab.hackathon.entity.BlockchainProperties;
import cn.edu.fudan.bclab.hackathon.util.BlockchainUtil;
import cn.edu.fudan.bclab.hackathon.util.ErrorHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
 * Created by 文捷 on 2017/5/12.
 */
@Service("userInfoService")
@EnableConfigurationProperties({BlockchainProperties.class})
public class UserInfoServiceImpl implements UserInfoService {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    private BlockchainProperties blockchainProperties;


    /**
     * 设置用户的诚信数值
     * @param bcContract
     * @return
     */
    public String setAmount(BlockchainContract bcContract) {
        String result="";
        List<BlockchainProperties.BcInfo> bcInfos = blockchainProperties.getBcInfos();
        BlockchainProperties.BcInfo bcInfo = bcInfos.get(BlockchainUtil.getBlockchainType());

        String[] stringParams = bcContract.getParams();
        int[] intArray = new int[stringParams.length];
        for(int i=0;i<stringParams.length;i++){
            intArray[i]=Integer.parseInt(stringParams[i]);
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("account", bcContract.getAccount());
        params.put("passphrase", bcContract.getPassphrase());
        params.put("privkey", bcContract.getPrivkey());
        params.put("contract", bcContract.getCode());
        params.put("abiDefinition", bcContract.getAbiDefinition());
        params.put("method",bcContract.getMethod());
        params.put("params", intArray);

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(params, headers);
            ResponseEntity<String> contractResult = restTemplate.exchange(bcInfo.getClientUrl()+"/contract/call", HttpMethod.POST, request, String.class);
            logger.info(String.valueOf(contractResult));
            logger.info(String.valueOf(contractResult.getBody()));
            String callResponse = contractResult.getBody();

            if (contractResult.getStatusCode()== HttpStatus.OK){
                ObjectMapper mapper = new ObjectMapper();
                AnlinkResponse anlinkResponse = mapper.readValue(callResponse, AnlinkResponse.class);
                result = anlinkResponse.getResult().toString();
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

        return result;
    }

    public String getAmount(BlockchainContract bcContract) {
        String result="";
        List<BlockchainProperties.BcInfo> bcInfos = blockchainProperties.getBcInfos();
        BlockchainProperties.BcInfo bcInfo = bcInfos.get(BlockchainUtil.getBlockchainType());


        String[] stringParams = bcContract.getParams();
        int intArray[] = new int[stringParams.length];
        for(int i=0;i<stringParams.length;i++){
            intArray[i]=Integer.parseInt(stringParams[i]);
            logger.info("param is {}",intArray[i]);
        }

        Map<String, Object> params = new HashMap<String, Object>();
//        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
        params.put("account", bcContract.getAccount());
        params.put("passphrase", bcContract.getPassphrase());
        params.put("privkey", bcContract.getPrivkey());
        params.put("contract", bcContract.getCode());
        params.put("abiDefinition", bcContract.getAbiDefinition());
        params.put("method",bcContract.getMethod());
        params.put("params", intArray);


        logger.info("params is {}",intArray.toString());

        try {
            ObjectMapper mapper = new ObjectMapper();
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//            restTemplate.setMessageConverters(getMessageConverters());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
//            HttpEntity<String> request = new HttpEntity<String>(params.toString(), headers);
            HttpEntity<Map<String,Object>> request = new HttpEntity<Map<String,Object>>(params, headers);
//            HttpEntity<String> request = new HttpEntity<String>(jsonInString, headers);
            logger.info("params is {}",params);
            logger.info("request is {}",request.toString());
            ResponseEntity<String> contractResult = restTemplate.exchange(bcInfo.getClientUrl()+"/contract/read", HttpMethod.POST, request, String.class);
//            result = restTemplate.postForObject(bcInfo.getClientUrl()+"/contract/read", request, String.class);
//            logger.info(String.valueOf(result));
            logger.info(String.valueOf(contractResult));
            logger.info(String.valueOf(contractResult.getBody()));
            String callResponse = contractResult.getBody();
//            String callResponse = contractResult.getBody();

            if (contractResult.getStatusCode()== HttpStatus.OK){
                AnlinkResponse anlinkResponse = mapper.readValue(callResponse, AnlinkResponse.class);
                result = anlinkResponse.getResult().toString();
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

        return result;
    }

    @Override
    public String setAmount(ArrayList chainParam, String id, String amount) {
        String result="";
        List<BlockchainProperties.BcInfo> bcInfos = blockchainProperties.getBcInfos();
        BlockchainProperties.BcInfo bcInfo = bcInfos.get(BlockchainUtil.getBlockchainType());


        String[] paramsArray = new String[2];
        paramsArray[0] = id;
        paramsArray[1] = amount;

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("account", chainParam.get(4));
        params.put("passphrase", chainParam.get(2));
        params.put("privkey", chainParam.get(1));
        params.put("contract", "0xc96b34f5c0b30b43bbbe86b7e8dd7bb0a9c8ac2a");
        params.put("abiDefinition", "[{\"constant\":false,\"inputs\":[{\"name\":\"key\",\"type\":\"uint256\"},{\"name\":\"_credit\",\"type\":\"uint256\"},{\"name\":\"_id\",\"type\":\"uint256\"}],\"name\":\"setCredit\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"userCredits\",\"outputs\":[{\"name\":\"credit\",\"type\":\"uint256\"},{\"name\":\"id\",\"type\":\"uint256\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"key\",\"type\":\"uint256\"}],\"name\":\"getCredit\",\"outputs\":[{\"name\":\"_credit\",\"type\":\"uint256\"}],\"payable\":false,\"type\":\"function\"}]\n");
        params.put("method","getCredit");
        params.put("params", paramsArray);

        try {
            ObjectMapper mapper = new ObjectMapper();
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String,Object>> request = new HttpEntity<Map<String,Object>>(params, headers);
            logger.info("params is {}",params);
            logger.info("request is {}",request.toString());
            ResponseEntity<String> contractResult = restTemplate.exchange(bcInfo.getClientUrl()+"/contract/call", HttpMethod.POST, request, String.class);

            logger.info(String.valueOf(contractResult));
            logger.info(String.valueOf(contractResult.getBody()));
            String callResponse = contractResult.getBody();

            if (contractResult.getStatusCode()== HttpStatus.OK){
                AnlinkResponse anlinkResponse = mapper.readValue(callResponse, AnlinkResponse.class);
                result = anlinkResponse.getResult().toString();
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

        return result;




    }

    @Override
    public String getAmount(ArrayList chainParam, String id){

        String result="";
//        List<BlockchainProperties.BcInfo> bcInfos = blockchainProperties.getBcInfos();
//        BlockchainProperties.BcInfo bcInfo = bcInfos.get(BlockchainUtil.getBlockchainType());


        String[] paramsArray = new String[1];
        paramsArray[0] = id;

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("account", chainParam.get(4));
        params.put("passphrase", chainParam.get(2));
        params.put("privkey", chainParam.get(1));
        params.put("contract", "0xc96b34f5c0b30b43bbbe86b7e8dd7bb0a9c8ac2a");
        params.put("abiDefinition", "[{\"constant\":false,\"inputs\":[{\"name\":\"key\",\"type\":\"uint256\"},{\"name\":\"_credit\",\"type\":\"uint256\"},{\"name\":\"_id\",\"type\":\"uint256\"}],\"name\":\"setCredit\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"userCredits\",\"outputs\":[{\"name\":\"credit\",\"type\":\"uint256\"},{\"name\":\"id\",\"type\":\"uint256\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"key\",\"type\":\"uint256\"}],\"name\":\"getCredit\",\"outputs\":[{\"name\":\"_credit\",\"type\":\"uint256\"}],\"payable\":false,\"type\":\"function\"}]\n");
        params.put("method","getCredit");
        params.put("params", paramsArray);

        try {
            ObjectMapper mapper = new ObjectMapper();
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String,Object>> request = new HttpEntity<Map<String,Object>>(params, headers);
            logger.info("params is {}",params);
            logger.info("request is {}",request.toString());
            ResponseEntity<String> contractResult = restTemplate.exchange("http://10.253.14.131/v1/contract/read", HttpMethod.POST, request, String.class);

            logger.info(String.valueOf(contractResult));
            logger.info(String.valueOf(contractResult.getBody()));
            String callResponse = contractResult.getBody();

            if (contractResult.getStatusCode()== HttpStatus.OK){
                AnlinkResponse anlinkResponse = mapper.readValue(callResponse, AnlinkResponse.class);
                result = anlinkResponse.getResult().toString();
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

        return result;



    }
}
