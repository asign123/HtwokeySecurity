package com.htwokey.htwokeysecurity.entity.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hchbo
 * @date 2023/4/14 10:17
 */
@Getter
@Setter
public class UpdateAdminUserPasswordParam {
    @NotEmpty
    private String username;
    @NotEmpty
    private String oldPassword;
    @NotEmpty
    private String newPassword;
}
