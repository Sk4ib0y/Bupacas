package com.bupacas.repository;


import com.bupacas.model.TelefonoProv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefonoProvRepository extends JpaRepository<TelefonoProv, Integer>
{
}
