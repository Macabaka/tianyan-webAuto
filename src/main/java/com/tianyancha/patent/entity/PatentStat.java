package com.tianyancha.patent.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Johnny
 * @Date: 2020/4/19 18:33
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatentStat {
    private  Integer id;
    private  String name;
    private  Integer countInvent;
    private  Integer countPractical;
    private  Integer countAppear;
    private  Integer grade;
}
