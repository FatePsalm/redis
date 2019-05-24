package com.solace.redis_data_transaction.solace.beans;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author CaoGang
 * @since 2019-04-20
 */
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    private String id;

    @TableField("userName")
    private String userName;

    private String password;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "User{" +
        "id=" + id +
        ", userName=" + userName +
        ", password=" + password +
        "}";
    }
}
