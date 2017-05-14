package cn.edu.fudan.bclab.hackathon.repository;

import cn.edu.fudan.bclab.hackathon.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by bintan on 17-4-13.
 */
@RepositoryRestResource
public interface RegisterRepository extends PagingAndSortingRepository<User, Long> {

    @Override
    @RestResource(exported = false)
    void delete(User user);

    User findByUsername(String username);
}

