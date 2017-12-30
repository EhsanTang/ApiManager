package cn.crap.service.custom;

import cn.crap.dao.custom.CustomHotSearchDao;
import cn.crap.dao.mybatis.HotSearchDao;
import cn.crap.model.mybatis.HotSearch;
import cn.crap.model.mybatis.HotSearchCriteria;
import cn.crap.utils.MyString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;


@Service
public class CustomHotSearchService{
    @Autowired
    private CustomHotSearchDao customHotSearchDao;
   @Autowired
   private HotSearchDao hotSearchDao;

    public List<String> queryTop10() {
       return customHotSearchDao.queryTop10();
    }

    public HotSearch tryGetByKeyWord(String keyword){
        if (MyString.isEmpty(keyword)) {
            return null;
        }
        HotSearchCriteria example = new HotSearchCriteria();
        example.createCriteria().andKeywordEqualTo(keyword);

        List<HotSearch> hotSearches = hotSearchDao.selectByExample(example);
        if (!CollectionUtils.isEmpty(hotSearches)){
            return hotSearches.get(0);
        }
        return null;
    }
}
