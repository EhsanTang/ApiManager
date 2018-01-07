package cn.crap.service.mybatis;

import cn.crap.dao.mybatis.CommentDao;
import cn.crap.enumer.TableId;
import cn.crap.framework.IdGenerator;
import cn.crap.model.mybatis.Comment;
import cn.crap.model.mybatis.CommentCriteria;
import cn.crap.utils.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * Automatic generation by tools
 * service
 */
@Service
public class CommentService {
    @Autowired
    private CommentDao mapper;

    public List<Comment> selectByExample(CommentCriteria example) {
        return mapper.selectByExample(example);
    }

    public int countByExample(CommentCriteria example) {
        return mapper.countByExample(example);
    }

    public Comment getById(String id) {
        if (id == null){
            return null;
        }
        return mapper.selectByPrimaryKey(id);
    }

    public boolean insert(Comment model) {
        if (model == null) {
            return false;
        }
        model.setId(IdGenerator.getId(TableId.COMMENT));
        if (model.getSequence() == null){
            CommentCriteria example = new CommentCriteria();
            example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);
            example.setMaxResults(1);
            List<Comment>  models = this.selectByExample(example);
            if (models.size() > 0){
                model.setSequence(models.get(0).getSequence() + 1);
            }else{
                model.setSequence(0);
            }
        }
        model.setCreateTime(new Date());
        return mapper.insertSelective(model) > 0;
    }

    public boolean update(Comment model) {
        if (model == null) {
            return false;
        }
        return mapper.updateByPrimaryKeySelective(model) > 0 ? true : false;
    }

    public boolean delete(String id) {
        Assert.notNull(id, "id ????");
        return mapper.deleteByPrimaryKey(id) > 0 ? true : false;
    }

}
