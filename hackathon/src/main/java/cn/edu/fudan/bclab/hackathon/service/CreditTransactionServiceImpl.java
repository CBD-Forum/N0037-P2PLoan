package cn.edu.fudan.bclab.hackathon.service;

import cn.edu.fudan.bclab.hackathon.entity.CreditTransaction;
import cn.edu.fudan.bclab.hackathon.entity.Status;
import cn.edu.fudan.bclab.hackathon.repository.CreditTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by bintan on 17-4-19.
 */
@Service("creditTransactionService")
public class CreditTransactionServiceImpl implements CreditTransactionService {

    @Autowired
    CreditTransactionRepository creditTransactionRepository;

    public void createTransaction(){

    }


    public void checkTransaction(){
        //TODO
    }

    @Override
    public CreditTransaction getById(Long id) {
        return creditTransactionRepository.findByCreditTransactionId(id);
    }

    @Override
    public void save(CreditTransaction creditTransaction) {
        creditTransactionRepository.save(creditTransaction);
    }

    @Override
    public void update(CreditTransaction creditTransaction) {
        //TODO
    }

    @Override
    public void delete(CreditTransaction creditTransaction) {
        CreditTransaction oriCreditTransaction = creditTransactionRepository.findByCreditTransactionId(creditTransaction.getCreditTransactionId());

        if(oriCreditTransaction != null) {

            if(!oriCreditTransaction.getStatus().equals("DELETED")) {
                oriCreditTransaction.setStatus(Status.DELETED);
                creditTransactionRepository.save(oriCreditTransaction);
            }
        }

    }



    @Override
    public void updateStatus(CreditTransaction creditTransaction, Status status) {
        creditTransaction.setStatus(status);
        creditTransactionRepository.save(creditTransaction);
    }

    @Override
    public List<CreditTransaction> getList() {
        return (List<CreditTransaction>)creditTransactionRepository.findAll();
    }


}
