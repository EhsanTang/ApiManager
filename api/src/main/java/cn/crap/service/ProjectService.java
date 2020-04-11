package cn.crap.service;

import cn.crap.adapter.Adapter;
import cn.crap.dao.custom.CustomProjectDao;
import cn.crap.dao.mybatis.ProjectDao;
import cn.crap.dto.ProjectDTO;
import cn.crap.enu.*;
import cn.crap.framework.IdGenerator;
import cn.crap.framework.MyException;
import cn.crap.model.Log;
import cn.crap.model.ProjectPO;
import cn.crap.query.ProjectQuery;
import cn.crap.service.tool.SettingCache;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.List;

@Service
public class ProjectService extends NewBaseService<ProjectPO, ProjectQuery> implements ILogConst, IConst {
    @Autowired
    private LogService logService;
    @Autowired
    private CustomProjectDao customMapper;
    @Autowired
    private SettingCache settingCache;

    private ProjectDao projectDao;
    @Resource
    public void ProjectDao(ProjectDao projectDao) {
        this.projectDao = projectDao;
        super.setBaseDao(projectDao, TableId.PROJECT);
    }

    public ProjectPO getByUniKey(String userId, String projectUniKey) throws MyException{
        List<ProjectPO> projectPOS = this.select(new ProjectQuery().setUserId(userId).setUniKey(projectUniKey));
        if (CollectionUtils.isEmpty(projectPOS)){
            return null;
        }
        return projectPOS.get(0);
    }
    /**
     * 添加
     * @param project
     * @return
     */
    @Override
    public boolean insert(ProjectPO project) throws MyException{
        if (project == null) {
            return false;
        }
        if (MyString.isNotEmpty(project.getPassword())) {
            project.setPassword(MD5.encrytMD5(project.getPassword(), project.getId()));
        }

        if (project.getStatus() == null){
            project.setStatus(ProjectStatus.COMMON.getStatus());
        }

        if (project.getType() == null){
            project.setType(ProjectType.PRIVATE.getByteType());
        }

        if (project.getUniKey() == null){
            project.setUniKey(IdGenerator.getId(TableId.PROJECT));
        }

        if (MyString.isEmpty(project.getCover())){
            project.setCover(Tools.getAvatar());
        }
        return super.insert(project);
    }

    /**
     * 记录日志，再更新
     * @param project
     */
    public boolean update(ProjectPO project, boolean needAddLog) throws MyException{
        if (needAddLog) {
            ProjectPO dbModel = super.get(project.getId());
            Log log = Adapter.getLog(dbModel.getId(), L_PROJECT_CHINESE, dbModel.getName(), LogType.UPDATE, dbModel.getClass(), dbModel);
            logService.insert(log);
        }

        if (project.getPassword() != null) {
            if (project.getPassword().equals(C_DELETE_PASSWORD)) {
                project.setPassword("");
            } else {
                project.setPassword(MD5.encrytMD5(project.getPassword(), project.getId()));
            }
        }
        return super.update(project);
    }

    /**
     * 记录日志，再删除
     *
     * @param id
     */
    @Override
    public boolean delete(String id) throws MyException{
        Assert.notNull(id);
        ProjectPO dbModel = super.get(id);

        Log log = Adapter.getLog(dbModel.getId(), L_PROJECT_CHINESE, dbModel.getName(), LogType.DELTET, dbModel.getClass(), dbModel);
        logService.insert(log);

        return super.delete(id);
    }


    public List<ProjectPO> query(String userId, boolean onlyJoin, String name, Page page) throws MyException{
        Assert.notNull(userId, "userId can't be null");
        return customMapper.queryProjectByUserId(userId, onlyJoin, name, page);
    }

    public int count(String userId, boolean onlyJoin, String name) {
        Assert.notNull(userId, "userId can't be null");
        return customMapper.countProjectByUserId(userId, onlyJoin, name);
    }

    /**
     * 获取邀请将入的链接
     * @param projectDto
     * @return
     */
    public String getInviteUrl(ProjectDTO projectDto) throws MyException {
        Assert.notNull(projectDto);
        if (LoginUserHelper.getUser().getId().equals(projectDto.getUserId())) {
            return Tools.getUrlPath() + "/user/projectUser/invite.do?code=" + Aes.encrypt(projectDto.getId() + SEPARATOR + System.currentTimeMillis());
        }
        return null;
    }

    /**
     * 解析邀请码
     * @param code
     * @return
     * @throws MyException
     */
    @Nullable
    public String getProjectIdFromInviteCode(String code) throws MyException{
        if (MyString.isEmpty(code)){
            throw new MyException(MyError.E000065, "邀请码不能为空");
        }

        try{
            String originalCode = Aes.desEncrypt(code);
            String projectId = originalCode.split(SEPARATOR)[0];
            String timeStr = originalCode.split(SEPARATOR)[1];
            if (Long.parseLong(timeStr) + TWO_HOUR > System.currentTimeMillis()){
                return projectId;
            }
            throw new MyException(MyError.E000065, "抱歉，来得太晚了，邀请码过期");
        } catch (Exception e){
            throw new MyException(MyError.E000065, "未知异常：" + e.getMessage());
        }
    }


}
