package com.tianyancha.patent.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Johnny
 * @Date: 2020/4/18 16:12
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patent {
    private Integer id;
    private String name;
    private String requestNum;
    private String publicNum;
    private  String type;
    private  String author;
    private String date;
}
