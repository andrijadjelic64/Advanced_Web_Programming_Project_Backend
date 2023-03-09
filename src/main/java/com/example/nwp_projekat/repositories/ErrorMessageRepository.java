package com.example.nwp_projekat.repositories;

import com.example.nwp_projekat.model.ErrorMessage;
import com.example.nwp_projekat.model.Machine;
import com.example.nwp_projekat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ErrorMessageRepository extends JpaRepository<ErrorMessage, Integer> {

    public List<ErrorMessage> findAllByUser(User user);
}
