package cn.edu.fudan.bclab.hackathon.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import cn.edu.fudan.bclab.hackathon.entity.Credit;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by bintan on 17-4-9.
 */
@Data
@Entity
@Table(name="user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Credit> credits;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<CreditTransaction> creditTransactions;

    @Basic
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private double account;

    private Long creditScore=new Long(0);

    @Enumerated(EnumType.STRING)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserStatus userStatus;

    private String userPassPhrase;

    private String userChainAdress;

    private String userPrivateKey;

    private String contractAddress;

}
