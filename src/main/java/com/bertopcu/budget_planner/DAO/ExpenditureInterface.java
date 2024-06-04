package com.bertopcu.budget_planner.DAO;

import com.bertopcu.budget_planner.Model.Expenditure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenditureInterface extends JpaRepository<Expenditure, String> {

    @Query(value = "SELECT * FROM expenditure e WHERE (e.timestamp >= :startTime AND e.timestamp <= :endTime) OR e.monthly = 1", nativeQuery = true)
    List<Expenditure> findCostByTimes(@Param("startTime") Long startTime, @Param("endTime") Long endTime);

    // Add more custom queries as needed
}
