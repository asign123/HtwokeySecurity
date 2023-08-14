package com.htwokey.htwokeysecurity.entity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hchbo
 * @date 2023/4/14 10:15
 */
@Getter
@Setter
public class AdminUserParam {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    private String icon;
    @Email
    private String email;
    private String nickName;
    private String note;
}
