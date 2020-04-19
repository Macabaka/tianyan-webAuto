package com.tianyancha.patent.mapper;

import com.tianyancha.patent.config.MapperConfig;
import com.tianyancha.patent.entity.Patent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MapperConfig.class)
public class PatentMapperTest {

    @Resource
    PatentMapper patentMapper;

    @Test
    public void selectAll() {
        System.out.println(patentMapper.selectAll());
    }

    @Test
    public void batchInsert() {
        //根据发布号去重、获取第一作者
        HashMap<String, Patent> patentHashMap = new HashMap<>();
        List<Patent> patentList = patentMapper.selectAll();
        for(Patent patent :patentList){
            patentHashMap.put(patent.getPublicNum(), patent);
        }
        Collection<Patent> values = patentHashMap.values();
        List<Patent> newPatentList = new ArrayList<>();
        for(Patent patent : values){
            String author =patent.getAuthor();
            if (author.contains(";")) {
                String[] authors = author.split(";");
                patent.setAuthor(authors[0]);
            }
            newPatentList.add(patent);
        }
        patentMapper.batchInsert(newPatentList);
    }
}