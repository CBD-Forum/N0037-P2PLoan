package cn.edu.fudan.bclab.hackathon.repository;

import cn.edu.fudan.bclab.hackathon.entity.Credit;
import cn.edu.fudan.bclab.hackathon.entity.CreditStatus;
import cn.edu.fudan.bclab.hackathon.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * Created by bintan on 17-4-17.
 */
@RepositoryRestResource
public interface CreditRepository extends PagingAndSortingRepository<Credit, Long> {

    @Override
    @RestResource(exported = false)
    void delete(Long creditid);

    @Override
    @RestResource(exported = false)
    void delete(Credit credit);

    List<Credit> findByUser(User user);

    Credit findByCreditId(Long creditid);

    List<Credit>findByCreditStatus(CreditStatus creditStatus);

    Credit findByName(String name);

}
