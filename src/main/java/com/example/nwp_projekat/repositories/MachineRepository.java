package com.example.nwp_projekat.repositories;

import com.example.nwp_projekat.model.Machine;
import com.example.nwp_projekat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Integer> {
    public Machine findByName(String name);
    public Machine findByStatus(String status);
    public List<Machine> findAllByCreatedBy(User user);

    @Query(value = "select * from machines where machines.active = true AND machines.created_by = :userId AND (machines.name like CONCAT('%', :name, '%') AND machines.status like CONCAT('%', :status, '%'));", nativeQuery = true)
    List<Machine> findAllBySearch(@Param("userId") Integer userId, @Param("name") String name, @Param("status") String status);
}
