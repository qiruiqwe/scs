package com.scs.service;

import com.scs.dao.ActivityRepository;
import com.scs.dao.ClubRepository;
import com.scs.dao.UserRepository;
import com.scs.po.Activity;
import com.scs.po.User;
import com.scs.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public User checkUser(String username, String password) {
        User user =  userRepository.findUserByUsernameAndPassword(username, password);
        return user;
    }

    @Transactional
    @Override
    public User saveUser(User user) {
        if (user.getId() == null)
        {
            user.setRegistertime(new Date());
            return userRepository.save(user);
        }else{
            User oldUser = userRepository.findUserById(user.getId());
            if (user.getPassword() == null)
                user.setPassword(oldUser.getPassword());
            //将更新的user的信息更新到oldUser中
            BeanUtils.copyProperties(user,oldUser, MyBeanUtils.getNullPropertyNames(user));
            return userRepository.save(oldUser);
        }
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public Page<User> getUsersByClubId(Long id, Pageable pageable) {
        return userRepository.findAll(new Specification<User>(){
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("joinclubs");
                return criteriaBuilder.equal(join.get("id"),id);
            }
        },pageable);
    }


}
