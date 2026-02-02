package com.bupacas.repository;


import com.bupacas.model.TelefonoClte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefonoClteRepository extends JpaRepository<TelefonoClte, Integer>
{
}
