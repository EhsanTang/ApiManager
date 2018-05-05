package cn.crap.service.mybatis;

import cn.crap.dao.mybatis.UserDao;
import cn.crap.enumer.TableId;
import cn.crap.framework.IdGenerator;
import cn.crap.model.mybatis.User;
import cn.crap.model.mybatis.UserCriteria;
import cn.crap.utils.TableField;
import cn.crap.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

// TODO 重新生成所有的ID，保证ID有序
@Service
public class UserService {
    @Autowired
    private UserDao userMapper;

    public List<User> selectByExample(UserCriteria example) {
        return userMapper.selectByExample(example);
    }

    public int countByExample(UserCriteria example) {
        return userMapper.countByExample(example);
    }

    public User getById(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public boolean insert(User user) {
        if (user == null) {
            return false;
        }
        user.setId(IdGenerator.getId(TableId.USER));
        if (user.getSequence() == null){
            UserCriteria example = new UserCriteria();
            example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);
            example.setMaxResults(1);
            List<User>  users = this.selectByExample(example);
            if (users.size() > 0){
                user.setSequence(users.get(0).getSequence() + 1);
            }else{
                user.setSequence(0);
            }
        }
        user.setCreateTime(new Date());
        if (user.getAvatarUrl() == null){
            user.setAvatarUrl(Tools.getAvatar());
        }
        return userMapper.insertSelective(user) > 0;
    }

    public boolean update(User user) {
        if (user == null) {
            return false;
        }
        return userMapper.updateByPrimaryKeySelective(user) > 0 ? true : false;
    }

}
