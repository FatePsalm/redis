package com.wemew.rediscache.model;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author CG
 * @since 2020-02-25
 */
public class RedisManage implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableField(exist = false)
    private List<RedisManage> redisManage;
    private String id;

    /**
     * 分类
     */
    private Integer classify;


    /**
     * 状态
     */
    private Integer status;

    /**
     * key_index
     */
    private Integer redisIndex;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    private String pid;

    /**
     * 操作命令
     */
    private String opsName;

    /**
     * 备注
     */
    private String remarks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public Integer getClassify() {
        return classify;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getRedisIndex() {
        return redisIndex;
    }

    public Integer getStatus() {
        return status;
    }

    public void setClassify(Integer classify) {
        this.classify = classify;
    }

    public void setRedisIndex(Integer redisIndex) {
        this.redisIndex = redisIndex;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
    public String getOpsName() {
        return opsName;
    }

    public void setOpsName(String opsName) {
        this.opsName = opsName;
    }
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<RedisManage> getRedisManage() {
        return redisManage;
    }

    public void setRedisManage(List<RedisManage> redisManage) {
        this.redisManage = redisManage;
    }

    @Override
    public String toString() {
        return "RedisManage{" +
        "id=" + id +
        ", classify=" + classify +
        ", status=" + status +
        ", redisIndex=" + redisIndex +
        ", createTime=" + createTime +
        ", pid=" + pid +
        ", opsName=" + opsName +
        ", remarks=" + remarks +
        ", redisManage=" + redisManage.toString() +
        "}";
    }
}
