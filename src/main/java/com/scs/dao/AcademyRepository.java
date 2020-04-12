package com.scs.dao;

import com.scs.po.Academy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademyRepository extends JpaRepository<Academy,Long> {
}
