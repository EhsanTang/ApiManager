package cn.crap.dto;

import cn.crap.model.CommentPO;

import java.io.Serializable;

/**
 * Automatic generation by tools
 * dto: exchange data with view
 */
public class CommentDTO extends CommentPO implements Serializable {
	private String createTimeStr;
    private String updateTimeStr;

    private String imgCode;
	private boolean needImgCode = true;

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

    public String getImgCode() {
        return imgCode;
    }

    public void setImgCode(String imgCode) {
        this.imgCode = imgCode;
    }

    public boolean isNeedImgCode() {
        return needImgCode;
    }

    public void setNeedImgCode(boolean needImgCode) {
        this.needImgCode = needImgCode;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }
}
