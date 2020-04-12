package com.scs.service;

import com.scs.po.Academy;

import java.util.List;
import java.util.Optional;

public interface AcademyService {
    List<Academy> listAcademy();
    Academy getAcademyById(Long id);

}
