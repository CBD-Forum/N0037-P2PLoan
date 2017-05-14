package cn.edu.fudan.bclab.hackathon.repository;

import cn.edu.fudan.bclab.hackathon.entity.Credit;
import cn.edu.fudan.bclab.hackathon.entity.CreditTransaction;
import cn.edu.fudan.bclab.hackathon.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * Created by bintan on 17-4-19.
 */
public interface CreditTransactionRepository extends PagingAndSortingRepository<CreditTransaction, Long> {
    @Override
    @RestResource(exported = false)
    void delete(Long creditTransaction);

    @Override
    @RestResource(exported = false)
    void delete(CreditTransaction creditTransaction);

    CreditTransaction findByCreditTransactionId(Long id);

}
