package com.example.queriesmantenimientos.repository;

import com.example.queriesmantenimientos.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionRepository extends JpaRepository<Action,Integer> {
    List<Action> findByIdIn(List<Integer> ids);
}
