package com.noah_solutions.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * 2018 11 26 lijun
 * 建材网权限表实体类
 */
@Entity
@Table(name = "role")
@Data
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT '角色id'")
    private String roleId;
    @Column(columnDefinition = "VARCHAR(100)   COMMENT '(角色名称)'")
    private String roleName;
    @Column(columnDefinition = "VARCHAR(100)   COMMENT '(角色名称)'")
    private String roleNameEng;
    @Column(columnDefinition = "INT(2) NOT NULL COMMENT '(用户类型.1.管理员，2.供应商)'")
    private Integer roleType;
    @Column(columnDefinition = "VARCHAR(200) NOT NULL COMMENT '角色描述'")
    private String  roleDescribe;
    @Column(columnDefinition = "VARCHAR(200) NOT NULL COMMENT '角色描述'")
    private String  roleDescribeEng;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private String  roleCreateTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
    private String  roleUpdateTime;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permission",
            joinColumns = {@JoinColumn(name = "roleId")},
            inverseJoinColumns = {@JoinColumn(name = "perId")}
    )
    private Set<Permission> permissions;

//    @JsonIgnore
//    @OneToMany(mappedBy ="role")
//    private Set<UserInfo> users=new HashSet<>();

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public void setRoleDescribe(String roleDescribe) {
        this.roleDescribe = roleDescribe;
    }

    public void setRoleCreateTime(String roleCreateTime) {
        this.roleCreateTime = roleCreateTime;
    }

    public void setRoleUpdateTime(String roleUpdateTime) {
        this.roleUpdateTime = roleUpdateTime;
    }

//    public void setUsers(Set<UserInfo> users) {
//        this.users = users;
//    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
//    @OneToMany(mappedBy = "role",cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
////    @JoinTable(
////            name = "role_permission",
////            joinColumns = {@JoinColumn(name = "roleId")},
////            inverseJoinColumns = {@JoinColumn(name = "perId")}
////    )
//    private Set<RolePermission> rolePermissions=new HashSet<>();
}
