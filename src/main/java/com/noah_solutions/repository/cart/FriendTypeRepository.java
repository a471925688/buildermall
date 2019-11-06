package com.noah_solutions.repository.cart;

import com.noah_solutions.entity.Brand;
import com.noah_solutions.entity.chat.FriendType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendTypeRepository extends JpaRepository<FriendType,Integer> {
    List<FriendType> getAllByUserId(Integer userId);
    @Modifying
    @Query("update  FriendType  ft set ft.typeName = ?1 where ft.id = ?2 and ft.userId = ?3")
    void renameFriendType(String typeName,Integer id,Integer userId);
}
