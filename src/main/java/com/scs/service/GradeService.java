package com.scs.service;

import com.scs.po.Grade;

import java.util.List;

public interface GradeService {

    Grade getGradeById(Long id);
    List<Grade> listGrades();
}
