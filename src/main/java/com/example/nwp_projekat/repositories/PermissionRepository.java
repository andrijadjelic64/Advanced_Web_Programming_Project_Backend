package com.example.nwp_projekat.repositories;

import com.example.nwp_projekat.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    public Permission findByPermissionId(Integer permissionId);
    public Permission findByPermission(String permission);

}
