package cn.edu.fudan.bclab.hackathon.viewEntity;

import lombok.Data;

/**
 * Created by bintan on 17-5-12.
 */
@Data
public class ResponseUser {
    private Long userId;

    private String username;

    private double account;

    private Long creditScore;

    private String userPassPhrase;

    private String userChainAdress;

    private String userPrivateKey;
}
