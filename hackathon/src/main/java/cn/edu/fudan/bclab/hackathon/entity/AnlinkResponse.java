package cn.edu.fudan.bclab.hackathon.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by 文捷 on 2017/4/30.
 */
@Data
public class AnlinkResponse<T> {

    private String isSuccess;


    private T result;

}
