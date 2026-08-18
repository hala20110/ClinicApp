package com.netpoint.clinicapp.repository;

import com.netpoint.clinicapp.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorRepo extends JpaRepository<Doctor,Long> {
    @Query("select d from Doctor d where d.isActive=:isactive And d.specialization.id=:specId")
    public List<Doctor> findAllDocsActiveAndspecId(@Param("isactive") Boolean isactive,@Param("specId") Long SpecId);

//    boolean isActive(boolean isActive);
}
