package com.cfcons.moneymgr.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto implements Comparable<AccountDto> {
    private Long id;
    private String name;

    /**
     * Implement comparison for account list sorting
     *
     * @param o the object to be compared.
     * @return  as for comparable
     */
    @Override
    public int compareTo(AccountDto o) {
        return name.compareTo(o.getName());
    }
}
