package cn.edu.fudan.bclab.hackathon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by 文捷 on 2017/4/30.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockchainAccount {
    private String privkey;
    private String address;

}
