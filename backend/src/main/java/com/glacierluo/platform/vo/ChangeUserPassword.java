package com.glacierluo.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "ChangUserPassword对象",description = "用户修改密码对象")
public class ChangeUserPassword implements Serializable {
    @ApiModelProperty(value = "用户名",name = "userId",example = "123456")
    private Long userId;
    @ApiModelProperty(value = "旧密码",name = "oldPassword",example = "123456")
    private String oldPassword;
    @ApiModelProperty(value = "新密码",name = "newPassword",example = "123123")
    private String newPassword;
}
