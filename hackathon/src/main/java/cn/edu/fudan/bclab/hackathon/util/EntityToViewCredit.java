package cn.edu.fudan.bclab.hackathon.util;

import cn.edu.fudan.bclab.hackathon.entity.Credit;
import cn.edu.fudan.bclab.hackathon.viewEntity.ResponseCredit;

/**
 * Created by bintan on 17-5-12.
 */
public class EntityToViewCredit {
    public ResponseCredit getCreditView(Credit credit){
        ResponseCredit responseCredit = new ResponseCredit();

        responseCredit.setCreditId(credit.getCreditId());
        responseCredit.setAmountSum(credit.getAmountSum());
        responseCredit.setName(credit.getName());
        responseCredit.setCreditType(credit.getCreditType());
        responseCredit.setCurrentSum(credit.getCurrentSum());
        responseCredit.setNeededSum(credit.getNeededSum());
        responseCredit.setCreditReason(credit.getCreditReason());
        responseCredit.setCreditTime(credit.getCreditTime());
        responseCredit.setLongs(credit.getLongs());
        responseCredit.setRatio(credit.getRatio());
        responseCredit.setNeededScore(credit.getNeededScore());
        responseCredit.setCurrentPersonNums(credit.getCurrentPersonNums());
        responseCredit.setCreditChainAdress(credit.getCreditChainAdress());
        responseCredit.setCreditPassPhrase(credit.getCreditPassPhrase());
        responseCredit.setCreditPrivateKey(credit.getCreditPrivateKey());

        return responseCredit;
    }
}
