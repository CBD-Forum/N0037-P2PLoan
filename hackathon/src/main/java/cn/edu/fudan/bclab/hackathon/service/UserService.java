package cn.edu.fudan.bclab.hackathon.service;


import java.util.List;

import cn.edu.fudan.bclab.hackathon.entity.*;
import org.springframework.stereotype.Service;

/**
 * Created by bintan on 17-4-9.
 */
public interface UserService {
    void save(User user);

    void update(User user);

    void delete(String username);

    User getById(Long id);

    User getByUsername(String username);

    void updateStatus(User user, UserStatus userStatus);

    List<User> getList();

    String releaseCredit(User user,Credit credit);

    String buyCredit(User user , Credit credit , CreditTransaction creditTransaction);

    String userNameModify(String username, String modify);

    String userPasswdModify(String username, String modify);

    String userAccountModify(User user,Double modify);

    String userNameModifyChain(String username, String modify);

    String userPasswdModifyChain(String username, String modify);

    List<Credit> getReleaseCreditList(Long id);

    Long getUserCreditScore(User user);

    List<Credit> getBuyCreditList(Long id);

    List<Credit> getReleaseCreditListByUsername(String username);

    List<Credit> getBuyCreditListByUsername(String username);
}
