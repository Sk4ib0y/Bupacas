package com.bupacas.repository;


import com.bupacas.model.Papa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PapaRepository extends JpaRepository<Papa, Integer>
{
}
