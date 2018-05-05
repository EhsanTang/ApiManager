package cn.crap.service.mybatis;

import cn.crap.dao.mybatis.MenuDao;
import cn.crap.enumer.TableId;
import cn.crap.framework.IdGenerator;
import cn.crap.model.mybatis.Menu;
import cn.crap.model.mybatis.MenuCriteria;
import cn.crap.utils.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Service
public class MenuService {
    @Autowired
    private MenuDao menuMapper;

    public List<Menu> selectByExample(MenuCriteria example) {
        return menuMapper.selectByExample(example);
    }

    public int countByExample(MenuCriteria example) {
        return menuMapper.countByExample(example);
    }

    public Menu getById(String id) {
        if (id == null){
            return null;
        }
        return menuMapper.selectByPrimaryKey(id);
    }

    public boolean insert(Menu menu) {
        if (menu == null) {
            return false;
        }
        menu.setId(IdGenerator.getId(TableId.MENU));
        if (menu.getSequence() == null){
            MenuCriteria example = new MenuCriteria();
            example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);
            example.setMaxResults(1);
            List<Menu>  menus = this.selectByExample(example);
            if (menus.size() > 0){
                menu.setSequence(menus.get(0).getSequence() + 1);
            }else{
                menu.setSequence(0);
            }
        }
        menu.setCreateTime(new Date());
        return menuMapper.insertSelective(menu) > 0;
    }

    public boolean update(Menu menu) {
        if (menu == null) {
            return false;
        }
        return menuMapper.updateByPrimaryKeySelective(menu) > 0 ? true : false;
    }

    public boolean delete(String id) {
        Assert.notNull(id, "id 不能为空");
        return menuMapper.deleteByPrimaryKey(id) > 0 ? true : false;
    }

}
