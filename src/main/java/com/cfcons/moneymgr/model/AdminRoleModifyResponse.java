package com.cfcons.moneymgr.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminRoleModifyResponse {
    String message;
    String email;
    Boolean isadmin;
}
