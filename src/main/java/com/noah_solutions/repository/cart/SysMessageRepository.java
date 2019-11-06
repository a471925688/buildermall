package com.noah_solutions.repository.cart;

import com.noah_solutions.entity.chat.Group;
import com.noah_solutions.entity.chat.SysMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMessageRepository extends JpaRepository<SysMessage,Integer> {
    List<SysMessage> findAllByUid(Integer uid, Pageable pageable);
    Long countByUidAndRead(Integer uid,boolean read);

    @Modifying
    @Query("update SysMessage  sm set sm.status = ?1 where sm.id = ?2")
    void updateStatus(Integer status,Integer oldId);

    Boolean existsByUidAndFromAndStatus(Integer userId,Integer fromId,Integer status);
}
