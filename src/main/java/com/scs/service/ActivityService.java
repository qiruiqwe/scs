package com.scs.service;

import com.scs.po.Activity;
import com.scs.po.Club;
import com.scs.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ActivityService {

    Activity saveActivity(Activity activity);

    List<Activity> listActivityTop(Integer size);

    Activity contentMarkdownToHtml(Long id);

    Page<Activity> listActivityies(Pageable pageable);

    Activity getActivity(Long id);

    Page<Activity> listActivitiesByUserId(Pageable pageable,Long userId);

    Page<Activity> listActivitiesByClubId(Long myClubId, Pageable pageable);

    void  deleteActivity(Long id);

    Activity deleteUser(Long userid,Long activityid);

    Activity addUser(Long userid,Long activityid);
}
