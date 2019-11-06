package com.noah_solutions.repository.cart;

import com.noah_solutions.entity.chat.FriendType;
import com.noah_solutions.entity.chat.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group,Integer> {
    @Query("select new com.noah_solutions.pojo.Group(gp.id,gp.groupName,gp.avatar) from  Group  gp where gp.userId=?1")
    List<com.noah_solutions.pojo.Group> selectByUserId(Integer userId);
}
