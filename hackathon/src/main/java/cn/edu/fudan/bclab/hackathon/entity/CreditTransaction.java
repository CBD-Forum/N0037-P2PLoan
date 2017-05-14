package cn.edu.fudan.bclab.hackathon.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import javax.persistence.*;
import java.io.Serializable;
/**
 * Created by bintan on 17-4-19.
 */
@Data
@Entity
@Table(name="creditTransaction")
public class CreditTransaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creditTransactionId;


    @ManyToOne
    @JoinColumn(name = "credit_id")
    private Credit credit;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Status status;

    //交易类型
    private String type;
    //本次交易金额
    private Double accountSum;

    private String time;

    private String creditTrasactionPassPhrase;

    private String creditTrasactionChainAdress;

    private String creditTrasactionPrivateKey;

}
