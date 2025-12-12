package com.senac.gerenciamentodecompras.repository;

import com.senac.gerenciamentodecompras.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
