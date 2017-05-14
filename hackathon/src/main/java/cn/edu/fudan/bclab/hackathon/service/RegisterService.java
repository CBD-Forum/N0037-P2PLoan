package cn.edu.fudan.bclab.hackathon.service;

import cn.edu.fudan.bclab.hackathon.entity.User;

/**
 * Created by bintan on 17-4-13.
 */
public interface RegisterService {

    void save(User user);

    String register(User user);
}
