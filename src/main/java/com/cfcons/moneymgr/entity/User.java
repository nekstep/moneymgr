package com.cfcons.moneymgr.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Entity for User related data.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private Set<Account> accounts;

    /**
     * Grant role to user.
     *
     * @param role  Role to be granted.
     */
    public void addRole(Role role) {
        if (roles.stream().noneMatch(x -> x.equals(role))) {
            roles.add(role);
        }
    }

    /**
     * Revoke role from user.
     *
     * @param role  Role to be revoked.
     */
    public void deleteRole(Role role) {
        roles = roles.stream().filter(x -> !x.equals(role)).collect(Collectors.toList());
    }

    /**
     * Grant or revoke role to user depending on parameter.
     *
     * @param role      Role.
     * @param value     true to grant, false to revoke.
     */
    public void updateRole(Role role, Boolean value) {
        if (value) {
            addRole(role);
        } else {
            deleteRole(role);
        }
    }

    /**
     * Check if user has a role.
     *
     * @param role  Role
     * @return      true if user has this role
     */
    public Boolean hasRole(Role role) {
        return roles.stream()
                .anyMatch(x -> x.equals(role));
    }
}