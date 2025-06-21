package com.arimayi.facturation_module.Repositories;

import com.arimayi.facturation_module.Entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {}
