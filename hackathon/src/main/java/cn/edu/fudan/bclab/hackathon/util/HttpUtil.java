package cn.edu.fudan.bclab.hackathon.util;

import cn.edu.fudan.bclab.hackathon.entity.AnlinkAccount;
import cn.edu.fudan.bclab.hackathon.entity.AnlinkResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

/**
 * Created by 文捷 on 2017/5/2.
 */
@Slf4j
public class HttpUtil<T> {

    /**
     * process anlink response and map result to conresponding object
     * @param response
     * @param type
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T extends Object> T processResponse(String response,Class<T> type) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        AnlinkResponse anlinkResponse = mapper.readValue(response, AnlinkResponse.class);
        log.info("result is {}", anlinkResponse.getResult());
        String jsonInString = mapper.writeValueAsString(anlinkResponse.getResult());
        log.info("jsonString is {}", jsonInString);
        log.info("type.class is {}",type.getClass());
        return mapper.readValue(jsonInString,type);
    }
}
