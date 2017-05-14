package cn.edu.fudan.bclab.hackathon.util;

import cn.edu.fudan.bclab.hackathon.entity.User;
import cn.edu.fudan.bclab.hackathon.viewEntity.ResponseUser;

/**
 * Created by bintan on 17-5-12.
 */
public class EntityToViewUser {
    public ResponseUser getUserView(User user){
        ResponseUser responseUser = new ResponseUser();
        responseUser.setUserId(user.getUserId());
        responseUser.setUsername(user.getUsername());
        responseUser.setAccount(user.getAccount());
        responseUser.setCreditScore(user.getCreditScore());
        responseUser.setUserChainAdress(user.getUserChainAdress());
        responseUser.setUserPassPhrase(user.getUserPassPhrase());
        responseUser.setUserPrivateKey(user.getUserPrivateKey());
        return responseUser;
    }
}
