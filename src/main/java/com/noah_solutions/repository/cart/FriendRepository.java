package com.noah_solutions.repository.cart;

import com.noah_solutions.entity.chat.Friend;
import com.noah_solutions.entity.chat.FriendType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend,Integer> {
    Friend findByFriendIdAndUserId(String friendId,String userId);
    Boolean existsByUserIdAndFriendId(String userId,String friendId);
    void deleteByUserIdAndFriendId(String userId, String friendId);
}
