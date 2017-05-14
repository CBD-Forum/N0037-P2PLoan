package cn.edu.fudan.bclab.hackathon.service;

import cn.edu.fudan.bclab.hackathon.entity.User;
import cn.edu.fudan.bclab.hackathon.util.DoubleFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.edu.fudan.bclab.hackathon.repository.RegisterRepository;

/**
 * Created by bintan on 17-4-13.
 */
@Service("registerService")
public class RegistServiceImpl implements RegisterService{

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private UserService userService;

    DoubleFormat doubleFormat = new DoubleFormat();

    public void save(User user) {
        registerRepository.save(user);
    }
    public String  register(User user) {
        //判断用户是否存在
        if (registerRepository.findByUsername(user.getUsername()) == null) {
            user.setAccount(doubleFormat.format(Math.random()*50000));
            user.setCreditScore(userService.getUserCreditScore(user));
            registerRepository.save(user);
            return "success";
        }
        else {
            return "failed";
        }
    }

}
