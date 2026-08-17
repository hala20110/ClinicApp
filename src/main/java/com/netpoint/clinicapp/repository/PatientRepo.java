package com.netpoint.clinicapp.repository;

import com.netpoint.clinicapp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface PatientRepo extends JpaRepository<Patient,Integer> {

    @Query("select p.name from Patient p where p.age> :age order by p.name asc")
    List<String> findPatientNamesByAgeGreaterThan(@Param("age") int age);



}
