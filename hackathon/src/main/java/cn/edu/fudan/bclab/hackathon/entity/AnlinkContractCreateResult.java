package cn.edu.fudan.bclab.hackathon.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by 文捷 on 2017/5/2.
 * the result of contract creating
 */
@Data
public class AnlinkContractCreateResult {

    private String contract;

    private String tx;
}
