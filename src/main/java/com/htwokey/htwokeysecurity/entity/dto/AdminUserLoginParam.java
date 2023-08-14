package com.htwokey.htwokeysecurity.entity.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hchbo
 * @date 2023/4/14 14:06
 */
@Data
@EqualsAndHashCode
public class AdminUserLoginParam {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
