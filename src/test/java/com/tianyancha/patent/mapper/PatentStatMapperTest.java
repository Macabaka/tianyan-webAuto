package com.tianyancha.patent.mapper;

import com.tianyancha.patent.config.MapperConfig;
import com.tianyancha.patent.entity.Patent;
import com.tianyancha.patent.entity.PatentStat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MapperConfig.class)
public class PatentStatMapperTest {

    @Resource
    private  PatentStatMapper patentStatMapper;

    @Resource
    private  PatentMapper patentMapper;

    @Test
    public void batchInsert() {
        //查询所有的数据
        List<Patent> patentList = patentMapper.selectAll();
        //将所有作者去重
        HashMap<String,Object> allAuthor = new HashMap<>();
        for (Patent patent : patentList) {
            allAuthor.put(patent.getAuthor(), "");
        }
        //获取所有作者
        Collection collection = allAuthor.keySet();
        List<PatentStat> patentStatList = new ArrayList<>();
        //遍历所有作者，获取各类型数量及总分
        for(Object object:collection){
            int countInvent=patentStatMapper.getCountByNameAndType(object.toString(), "发明专利");
            int countPractical = patentStatMapper.getCountByNameAndType(object.toString(), "实用新型");
            int countAppear= patentStatMapper.getCountByNameAndType(object.toString(), "外观专利");
            int grade =countInvent*100+countPractical*20+countAppear*5;
            patentStatList.add(PatentStat.builder().name(object.toString()).countPractical(countPractical).countInvent(countInvent).countAppear(countAppear).grade(grade).build());
        }
        patentStatMapper.batchInsert(patentStatList);


    }

    @Test
    public void getCountByNameAndType() {
        System.out.println(patentStatMapper.getCountByNameAndType("白顺科","发明专利"));
    }
}