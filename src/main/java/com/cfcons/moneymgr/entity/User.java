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

    public void addRole(Role role) {
        if (roles.stream().noneMatch(x -> x.equals(role))) {
            roles.add(role);
        }
    }

    public void deleteRole(Role role) {
        roles = roles.stream().filter(x -> !x.equals(role)).collect(Collectors.toList());
    }

    public void updateRole(Role role, Boolean value) {
        if (value) {
            addRole(role);
        } else {
            deleteRole(role);
        }
    }

    public Boolean hasRole(Role role) {
        return roles.stream()
                .anyMatch(x -> x.equals(role));
    }
}