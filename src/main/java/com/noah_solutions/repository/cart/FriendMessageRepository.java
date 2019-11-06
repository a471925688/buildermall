package com.noah_solutions.repository.cart;

import com.noah_solutions.entity.chat.FriendMessage;
import com.noah_solutions.pojo.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendMessageRepository extends JpaRepository<FriendMessage,Integer> {

    List<FriendMessage> findAllByToUserIdAndIsRead(Integer toUserId,Integer isRead);


    @Modifying
    @Query("update  FriendMessage fm set  fm.isRead = 1 where fm.fromUserId = ?2 and fm.toUserId = ?1")
    void markReaded(Integer uId,Integer toId);
}
