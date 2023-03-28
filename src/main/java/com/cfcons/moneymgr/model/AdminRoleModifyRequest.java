package com.cfcons.moneymgr.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminRoleModifyRequest {
    String email;
    Boolean isadmin;
}
