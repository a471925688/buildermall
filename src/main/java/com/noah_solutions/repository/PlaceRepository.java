package com.noah_solutions.repository;

import com.noah_solutions.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 2019 1 9 lijun
 */
@Repository
public interface PlaceRepository extends JpaRepository<Place,String> {
    List<Place> findAllByParentId(String parentId);
    List<Place> findAllByLevelOrId(String Level,String id);
}
