package com.scs.dao;

import com.scs.po.Activity;
import com.scs.po.Club;
import com.scs.po.New;
import com.scs.po.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity,Long>, JpaSpecificationExecutor<Activity> {

    @Query("select a from Activity a")
    List<Activity> findTop(Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Activity b set b.views = b.views+1 where b.id = ?1")
    int updateViews(Long id);

    @Transactional
    @Modifying
    @Query("update Activity a set a.users =?2 where a.id = ?1")
    int insertActivityAnd(Long aid, User uid);

    Activity findActivityById(Long activityId);

}
