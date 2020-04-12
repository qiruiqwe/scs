package com.scs.service;

import com.scs.NotFoundException;
import com.scs.dao.ActivityRepository;
import com.scs.dao.UserRepository;
import com.scs.po.Activity;
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
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public Activity saveActivity(Activity activity) {
        if (activity.getId() == null)
        {
            activity.setViews(0);
            return activityRepository.save(activity);
        }else{
            Activity oldActivity = activityRepository.findActivityById(activity.getId());
            //将更新的activity中的不是空的属更新到oldUser中
            BeanUtils.copyProperties(activity,oldActivity, MyBeanUtils.getNullPropertyNames(activity));
            return activityRepository.save(oldActivity);
        }
    }

    @Override
    public List<Activity> listActivityTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"activitytime");
        Pageable pageable = new PageRequest(0,size,sort);
        return activityRepository.findTop(pageable);
    }

    @Override
    public Activity contentMarkdownToHtml(Long id) {
        Activity activity  = activityRepository.getOne(id);
        if (activity == null)
            throw new NotFoundException("活动不存在");
        Activity newActivity = new Activity();
//      将club中的内容复制到newclub中
        BeanUtils.copyProperties(activity,newActivity);
        String content = newActivity.getContent();
        newActivity.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        activityRepository.updateViews(id);
        return newActivity;
    }

    @Override
    public Page<Activity> listActivityies(Pageable pageable) {
        return activityRepository.findAll(pageable);
    }

    @Override
    public Activity getActivity(Long id) {
        return activityRepository.getOne(id);
    }

    @Override
    public Page<Activity> listActivitiesByUserId(Pageable pageable, Long userId) {
        return activityRepository.findAll(new Specification<Activity>() {
            @Override
            public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join<Activity,User> join = root.join("users");
                return criteriaBuilder.equal(join.get("id"),userId);
            }
        },pageable);
    }

    @Override
    public Page<Activity> listActivitiesByClubId(Long myClubId, Pageable pageable) {
        return activityRepository.findAll(new Specification<Activity>() {
            @Override
            public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join<Activity,User> join  = root.join("club");
                return criteriaBuilder.equal(join.get("id"),myClubId);
            }
        },pageable);
    }

    @Override
    public void deleteActivity(Long id) {
        activityRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Activity deleteUser(Long userid, Long activityid) {
        Activity activity = activityRepository.getOne(activityid);
        List<User> users = activity.getUsers();
        users.remove(userRepository.getOne(userid));
        return activity;
    }

    @Transactional
    @Override
    public Activity addUser(Long userid, Long activityid) {
        Activity activity = activityRepository.getOne(activityid);
        List<User> users = activity.getUsers();
        users.add(userRepository.getOne(userid));
        return activity;
    }
}
