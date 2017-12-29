package cn.crap.dao.mybatis;

import cn.crap.model.mybatis.Setting;
import cn.crap.model.mybatis.SettingCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SettingDao {
    int countByExample(SettingCriteria example);

    int deleteByExample(SettingCriteria example);

    int deleteByPrimaryKey(String id);

    int insert(Setting record);

    int insertSelective(Setting record);

    List<Setting> selectByExample(SettingCriteria example);

    Setting selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Setting record, @Param("example") SettingCriteria example);

    int updateByExample(@Param("record") Setting record, @Param("example") SettingCriteria example);

    int updateByPrimaryKeySelective(Setting record);

    int updateByPrimaryKey(Setting record);
}