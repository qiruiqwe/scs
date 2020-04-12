package com.scs.service;


import com.scs.dao.AcademyRepository;
import com.scs.po.Academy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AcademyServiceImpl implements AcademyService {
    @Autowired
    private AcademyRepository academyRepository;

    @Transactional
    @Override
    public List<Academy> listAcademy() {
        return academyRepository.findAll();
    }

    @Override
    public Academy getAcademyById(Long id) {
        return academyRepository.findById(id).get();
    }


}
