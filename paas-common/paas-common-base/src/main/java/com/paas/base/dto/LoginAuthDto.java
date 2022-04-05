package com.paas.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Date 2022/4/4 周一 11:07
 * @Author xu
 * @FileName LoginAuthDto
 * @Description dto
 */
@Data
@ApiModel(value = "登录人信息")
public class LoginAuthDto implements Serializable {
    private static final long serialVersionUID = -9176180361533219684L;
    @ApiModelProperty(value = "用户ID")
    private Long userId;
    @ApiModelProperty(value = "登录名")
    private String loginName;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "组织ID")
    private Long groupId;
    @ApiModelProperty(value = "组织名称")
    private String groupName;

    public LoginAuthDto() {
    }

    public LoginAuthDto(Long userId, String loginName, String userName) {
        this.userId = userId;
        this.loginName = loginName;
        this.userName = userName;
    }

    public LoginAuthDto(Long userId, String loginName, String userName, Long groupId, String groupName) {
        this.userId = userId;
        this.loginName = loginName;
        this.userName = userName;
        this.groupId = groupId;
        this.groupName = groupName;
    }

}
