package cn.edu.fudan.bclab.hackathon.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by bintan on 17-4-17.
 */
@Data
@Entity
@Table(name="credit")
public class Credit  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creditId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade =  CascadeType.ALL , mappedBy = "credit")
    public Collection<CreditTransaction> creditTransactions ;


    //信贷名称
    private String name;
    //信贷类型
    private String creditType;
    //信贷总金额
    private Double amountSum;
    //当前已募集金额
    private Double currentSum;

    private Double neededSum;

    private String creditReason;

    private String creditTime;

    private int longs;

    private Double ratio;

    private Long neededScore;

    private int currentPersonNums;


    @Enumerated(EnumType.STRING)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CreditStatus creditStatus;

    @Enumerated(EnumType.STRING)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Status status;


    @Basic
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String digest;

    private String creditPassPhrase;

    private String creditChainAdress;

    private String creditPrivateKey;

}
