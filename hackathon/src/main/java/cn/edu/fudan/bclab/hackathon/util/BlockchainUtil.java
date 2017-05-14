package cn.edu.fudan.bclab.hackathon.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BlockchainUtil {

    private static Log log = LogFactory.getLog(BlockchainUtil.class);
    public static final int DEFAULT=0;

    private static String blockchainType;

    @Value("${blockchain.type}")
    public void setBlockchainType(String type) {
        blockchainType = type;
    }

    public static int getBlockchainType(){
        if(CommonUtil.isEmpty(blockchainType))
            return DEFAULT;
        return Integer.parseInt(blockchainType);
    }

    /**
     * Todo:packaging a static method to get blockchain infomation
     */
    //public static BlockchainProperties.BcInfo getBcInfo()

}
