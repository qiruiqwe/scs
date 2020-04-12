package com.scs.dao;

import com.scs.po.New;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public interface NewRepository extends JpaRepository<New,Long> , JpaSpecificationExecutor<New> {

    @Query("select n from New n")
    List<New> findTop(Pageable pageable);

    @Transactional
    @Modifying
    @Query("update New b set b.views = b.views+1 where b.id = ?1")
    int updateViews(Long id);

    New findNewById(Long id);
}
