package com.scs.service;

import com.scs.NotFoundException;
import com.scs.dao.NewRepository;
import com.scs.po.Activity;
import com.scs.po.Club;
import com.scs.po.New;
import com.scs.util.MarkdownUtils;
import com.scs.util.MyBeanUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;
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
public class NewServiceImpl implements NewService {
    @Autowired
    private  NewRepository newRepository;
    
    @Override
    public List<New> listNewTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"publishtime");
        Pageable pageable = new PageRequest(0,size,sort);
        return newRepository.findTop(pageable);
    }

    @Override
    public New contentMarkdownToHtml(Long id) {
        New mynew = newRepository.getOne(id);
        if (mynew == null)
            throw new NotFoundException("新闻不存在");
        New newnew = new New();
//      将club中的内容复制到newclub中
        BeanUtils.copyProperties(mynew,newnew);
        String content = newnew.getContent();
        newnew.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        newRepository.updateViews(id);
        return newnew;
    }

    @Override
    public Page<New> listNews(Pageable pageable) {
        return newRepository.findAll(pageable);
    }

    @Override
    public Page<New> listNewsByClubId(Long myClubId, Pageable pageable) {
        return newRepository.findAll(new Specification<New>() {
            @Override
            public Predicate toPredicate(Root<New> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join<New,Club> join = root.join("club");
                return criteriaBuilder.equal(join.get("id"),myClubId);
            }
        },pageable);
    }

    @Override
    public void deleteActivity(Long id) {
        newRepository.deleteById(id);
    }

    @Override
    public New getActivity(Long id) {
        return newRepository.getOne(id);
    }

    @Transactional
    @Override
    public New saveNew(New mynew) {
        if (mynew.getId() == null)
        {
            mynew.setViews(0);
            mynew.setPublishtime(new Date());
            return newRepository.save(mynew);
        }else{
            New oldNew = newRepository.findNewById(mynew.getId());
            //将更新的activity中的不是空的属更新到oldUser中
            BeanUtils.copyProperties(mynew,oldNew, MyBeanUtils.getNullPropertyNames(mynew));
            return newRepository.save(oldNew);
        }
    }

}
