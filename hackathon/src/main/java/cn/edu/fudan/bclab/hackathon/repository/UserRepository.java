package cn.edu.fudan.bclab.hackathon.repository;

import java.util.List;

import cn.edu.fudan.bclab.hackathon.entity.Credit;
import cn.edu.fudan.bclab.hackathon.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;


/**
 * Created by bintan on 17-4-10.
 */
@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    @Override
    @RestResource(exported = false)
    void delete(Long userid);

    @Override
    @RestResource(exported = false)
    void delete(User user);

    User findAllByUserId(Long userId);

    //List<User> findByCredit(Credit credit);

    User findByUsername(String username);

    void deleteByUsername(String username);
}
