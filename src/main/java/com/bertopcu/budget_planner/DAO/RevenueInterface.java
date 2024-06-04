package com.bertopcu.budget_planner.DAO;

import com.bertopcu.budget_planner.Model.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RevenueInterface extends JpaRepository<Revenue, String> {

    @Query(value = "SELECT * FROM revenue r WHERE (r.timestamp >= :startTime AND r.timestamp <= :endTime) OR r.monthly = 1", nativeQuery = true)
    List<Revenue> findRevenuByTimes(@Param("startTime") Long startTime, @Param("endTime") Long endTime);

}
