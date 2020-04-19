package com.tianyancha.patent;

import com.tianyancha.patent.config.MapperConfig;
import com.tianyancha.patent.entity.Patent;
import com.tianyancha.patent.mapper.PatentMapper;
import com.tianyancha.patent.util.Spider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Johnny
 * @Date: 2020/4/19 19:37
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MapperConfig.class);
        ac.scan("com.tianyancha.patent");
        PatentMapper patentMapper = (PatentMapper) ac.getBean("patentMapper");
        Spider.getAll();
        List<Patent> patentList = Spider.patentList;
        patentMapper.batchInsert(patentList);
    }
}
