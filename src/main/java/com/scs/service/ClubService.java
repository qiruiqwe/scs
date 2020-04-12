package com.scs.service;

import com.scs.po.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClubService {

    Club saveClub(Long userId,Club club);

    Club saveClub(Club club);

    List<Club> listClubTop(Integer size);

    Club contentMarkdownToHtml(Long id);

    Club getClubByClubId(Long id);

    List<Club> getClubByCreaterId(Long userid);

    Page<Club> listClubs(Pageable pageable);

    Page<Club> listClubsByUserId(Pageable pageable, Long id);

    Club removeUser(Long userid, Long clubid);

    Club addUser(Long userid,Long clubid);

    Club deleteUser(Long userid, Long clubid);
}
