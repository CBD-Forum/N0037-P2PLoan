package cn.edu.fudan.bclab.hackathon.service;

import cn.edu.fudan.bclab.hackathon.entity.Credit;
import cn.edu.fudan.bclab.hackathon.entity.CreditTransaction;
import cn.edu.fudan.bclab.hackathon.entity.Status;

import java.util.List;

/**
 * Created by bintan on 17-4-19.
 */
public interface CreditTransactionService {

    void save(CreditTransaction creditTransaction);

    void update(CreditTransaction creditTransaction);

    void delete(CreditTransaction creditTransaction);

    void createTransaction();

    void checkTransaction();

    CreditTransaction getById(Long id);

    void updateStatus(CreditTransaction creditTransaction, Status status);

    List<CreditTransaction> getList();

}
