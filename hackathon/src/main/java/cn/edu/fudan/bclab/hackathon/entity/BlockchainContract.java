package cn.edu.fudan.bclab.hackathon.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by 文捷 on 2017/5/2.
 */
@Data
public class BlockchainContract {

    private String code;

    private String abiDefinition;

    private String account;

    private String passphrase;

    private String contract;

    private String privkey;

    private String[] params;

    private String method;

}
