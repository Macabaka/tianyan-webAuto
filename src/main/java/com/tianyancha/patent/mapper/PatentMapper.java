package com.tianyancha.patent.mapper;

import com.tianyancha.patent.entity.Patent;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Johnny
 * @Date: 2020/4/18 21:59
 * @Description:
 */
public interface PatentMapper {
    /**
     * 查询所有数据
     * @return
     */
    @Select("SELECT * FROM patent_toweight")
    List<Patent> selectAll();

    /**
     * 在主表和去重表中批量插入数据
     * @param patents
     */
    @Insert("<script>" +
            "INSERT INTO patent_main (name,request_num,public_num,type,author,date) VALUES " +
            "<foreach collection=\"patentList\" item=\"patent\" index=\"index\"  separator=\",\">" +
            "(#{patent.name},#{patent.requestNum},#{patent.publicNum},#{patent.type},#{patent.author},#{patent.date}) " +
            "</foreach>" +
            "</script>")
    void batchInsert(@Param("patentList") List<Patent> patents);
}
