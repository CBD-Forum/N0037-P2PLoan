package cn.edu.fudan.bclab.hackathon.util;

import cn.edu.fudan.bclab.hackathon.entity.CreditTransaction;
import cn.edu.fudan.bclab.hackathon.viewEntity.ResponseTransaction;

/**
 * Created by bintan on 17-5-12.
 */
public class EntityToViewTransaction {
    public ResponseTransaction getCreditTransactionView(CreditTransaction creditTransaction){


        ResponseTransaction responseTransaction = new ResponseTransaction();

        responseTransaction.setCreditTransactionId(creditTransaction.getCreditTransactionId());
        responseTransaction.setAccountSum(creditTransaction.getAccountSum());
        responseTransaction.setType(creditTransaction.getType());
        responseTransaction.setCreditTrasactionChainAdress(creditTransaction.getCreditTrasactionChainAdress());
        responseTransaction.setCreditTrasactionPassPhrase(creditTransaction.getCreditTrasactionPassPhrase());
        responseTransaction.setCreditTrasactionPrivateKey(creditTransaction.getCreditTrasactionPrivateKey());
        responseTransaction.setTime(creditTransaction.getTime());

        return responseTransaction;
    }
}
