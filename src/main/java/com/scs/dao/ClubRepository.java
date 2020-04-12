package com.scs.dao;

import com.scs.po.Activity;
import com.scs.po.Club;
import com.scs.po.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club ,Long> , JpaSpecificationExecutor<Club> {

    @Query("select c from Club c where c.status = true")
    List<Club> findTop(Pageable pageable);

    List<Club> findClubByCreater_Id(Long id);

    Club findClubById(Long id);


}
