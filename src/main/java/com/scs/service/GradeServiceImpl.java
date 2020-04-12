package com.scs.service;


import com.scs.dao.GradeRepository;
import com.scs.po.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class GradeServiceImpl implements GradeService {
    @Autowired
    private GradeRepository gradeRepository;

    @Override
    public Grade getGradeById(Long id) {
        return gradeRepository.findById(id).get();
    }

    @Transactional
    @Override
    public List<Grade> listGrades() {
        return gradeRepository.findAll();
    }
}
