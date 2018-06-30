package cn.crap.dao.mybatis;

import cn.crap.model.HotSearch;
import cn.crap.model.HotSearchCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HotSearchDao extends BaseDao<HotSearch>{
    int countByExample(HotSearchCriteria example);

    int deleteByExample(HotSearchCriteria example);

    int deleteByPrimaryKey(String id);

    int insert(HotSearch record);

    int insertSelective(HotSearch record);

    List<HotSearch> selectByExample(HotSearchCriteria example);

    HotSearch selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") HotSearch record, @Param("example") HotSearchCriteria example);

    int updateByExample(@Param("record") HotSearch record, @Param("example") HotSearchCriteria example);

    int updateByPrimaryKeySelective(HotSearch record);

    int updateByPrimaryKey(HotSearch record);
}