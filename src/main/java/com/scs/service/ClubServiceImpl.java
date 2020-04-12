package com.scs.service;

import com.scs.NotFoundException;
import com.scs.dao.ClubRepository;
import com.scs.dao.UserRepository;
import com.scs.po.Club;
import com.scs.po.User;
import com.scs.util.MarkdownUtils;
import com.scs.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

import static org.apache.catalina.security.SecurityUtil.remove;


@Service
public class ClubServiceImpl implements ClubService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Transactional
    @Override
    public Club saveClub(Long userId, Club club) {
        if (club.getId() == null) {
            club.setStatus(false);
            club.setCreattime(new Date());
            club.setCreater(userRepository.findUserById(userId));
            return clubRepository.save(club);
        } else {
            Club oldClub = clubRepository.getOne(club.getId());
            //将更新的user的信息更新到oldUser中
            BeanUtils.copyProperties(club, oldClub, MyBeanUtils.getNullPropertyNames(club));
            return clubRepository.save(oldClub);
        }
    }

    @Transactional
    @Override
    public Club saveClub(Club club) {
        Club oldClub = clubRepository.getOne(club.getId());
        //将更新的user的信息更新到oldUser中
        BeanUtils.copyProperties(club, oldClub, MyBeanUtils.getNullPropertyNames(club));
        return clubRepository.save(oldClub);
    }

    @Override
    public List<Club> listClubTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "users.size");
        Pageable pageable = new PageRequest(0, size, sort);
        return clubRepository.findTop(pageable);
    }

    @Override
    public Club contentMarkdownToHtml(Long id) {
        Club club = clubRepository.getOne(id);
        if (club == null)
            throw new NotFoundException("社团不存在");
        Club newClub = new Club();
//      将club中的内容复制到newclub中
        BeanUtils.copyProperties(club, newClub);
        String content = newClub.getContent();
        newClub.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return newClub;
    }

    @Override
    public Club getClubByClubId(Long id) {
        return clubRepository.findClubById(id);
    }

    @Override
    public List<Club> getClubByCreaterId(Long userid) {
        return clubRepository.findClubByCreater_Id(userid);
    }

    @Override
    public Page<Club> listClubs(Pageable pageable) {
        return clubRepository.findAll(pageable);
    }

    @Override
    public Page<Club> listClubsByUserId(Pageable pageable, Long id) {
        return clubRepository.findAll(new Specification<Club>() {
            @Override
            public Predicate toPredicate(Root<Club> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join<Club, User> join = root.join("users");
                return criteriaBuilder.equal(join.get("id"), id);
            }
        }, pageable);
    }

    @Transactional
    @Override
    public Club removeUser(Long userid, Long clubid) {
        Club club = clubRepository.findClubById(clubid);
        List<User> users = club.getUsers();
        users.remove(userRepository.getOne(userid));
        return club;
    }

    @Transactional
    @Override
    public Club addUser(Long userid, Long clubid) {
        Club club = clubRepository.findClubById(clubid);
        List<User> users = club.getUsers();
        users.add(userRepository.getOne(userid));
        return club;
    }

    @Transactional
    @Override
    public Club deleteUser(Long userid, Long clubid) {
        Club club = clubRepository.findClubById(clubid);
        List<User> users = club.getUsers();
        users.remove(userRepository.getOne(userid));
        return null;
    }


}
