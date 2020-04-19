package com.tianyancha.patent.util;

import com.tianyancha.patent.entity.Patent;
import com.tianyancha.patent.entity.PatentStat;
import com.tianyancha.patent.mapper.PatentMapper;
import com.tianyancha.patent.mapper.PatentStatMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author Johnny
 * @Date: 2020/4/19 19:29
 * @Description:
 */
public class DataHandle {
    private static PatentMapper patentMapper;
    private  static PatentStatMapper patentStatMapper;

    /**
     * 查找所有信息，并且去重，得到第一作者
     */
    public static void getWeight() {
        HashMap<String, Patent> patentHashMap = new HashMap<>();
        //获取所有信息
        List<Patent> patentList = patentMapper.selectAll();
        for(Patent patent :patentList){
            //去重
            patentHashMap.put(patent.getPublicNum(), patent);
        }
        //获取所有去重后的数据
        Collection<Patent> values = patentHashMap.values();
        List<Patent> newPatentList = new ArrayList<>();
        //遍历每一个对象，全部修改为第一作者信息
        for(Patent patent : values){
            String author =patent.getAuthor();
            if (author.contains(";")) {
                String[] authors = author.split(";");
                patent.setAuthor(authors[0]);
            }
            newPatentList.add(patent);
        }
        //插入到去重表
        patentMapper.batchInsert(newPatentList);
    }

    /**
     * 将数据进行统计处理，并存到统计表
     */
    public static void getStatic(){
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
        //插入到统计表
        patentStatMapper.batchInsert(patentStatList);
    }
}
