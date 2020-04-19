package com.tianyancha.patent.mapper;

import com.tianyancha.patent.entity.Patent;
import com.tianyancha.patent.entity.PatentStat;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Johnny
 * @Date: 2020/4/19 18:34
 * @Description:
 */
public interface PatentStatMapper {
    /**
     * 在统计表中批量插入
     * @param patentStatList
     */
    @Insert("<script>" +
            "INSERT INTO patent_stat (name,count_invent,count_practical,count_appear,grade) VALUES " +
            "<foreach collection=\"patentStatList\" item=\"patentStat\" index=\"index\"  separator=\",\">" +
            "(#{patentStat.name},#{patentStat.countInvent},#{patentStat.countPractical},#{patentStat.countAppear},#{patentStat.grade}) " +
            "</foreach>" +
            "</script>")
    void batchInsert(@Param("patentStatList") List<PatentStat> patentStatList);

    /**
     * 根据姓名和专利类型查询数量
     * @param name
     * @param type
     * @return
     */
    @Select("SELECT COUNT(*) FROM patent_toweight WHERE author = #{name} AND type=#{type} ")
    //mybatis多参数传递
    int getCountByNameAndType(@Param("name") String name,@Param("type") String type);
}
