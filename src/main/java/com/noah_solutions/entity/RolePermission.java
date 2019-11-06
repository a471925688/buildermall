package com.noah_solutions.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "role_permission")
@Data
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class RolePermission {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  NOT NULL COMMENT '关系表id'")
//    private Integer rolePermissionId;
    @Id
    @Column(columnDefinition = "INT(10) NOT NULL COMMENT '角色id'")
    private String roleId;
    @Column(columnDefinition = "INT(10) NOT NULL COMMENT '权限id'")
    private String perId;
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Role role;
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Permission permission;
}
