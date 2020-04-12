package com.scs.service;

import com.scs.po.New;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface NewService {

   List<New> listNewTop(Integer size);

   New contentMarkdownToHtml(Long id);

   Page<New> listNews(Pageable pageable);

   Page<New> listNewsByClubId(Long myClubId, Pageable pageable);

    void deleteActivity(Long id);

    New getActivity(Long id);

    New saveNew(New mynew);
}
