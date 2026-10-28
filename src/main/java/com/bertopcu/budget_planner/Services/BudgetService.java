package com.bertopcu.budget_planner.Services;

import com.bertopcu.budget_planner.DAO.ExpenditureInterface;
import com.bertopcu.budget_planner.DAO.RevenueInterface;
import com.bertopcu.budget_planner.Model.Budget;
import com.bertopcu.budget_planner.Model.Expenditure;
import com.bertopcu.budget_planner.Model.Revenue;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BudgetService {

    @Autowired
    ExpenditureInterface costInterface;

    @Autowired
    RevenueInterface revenuInterface;

    @PersistenceContext
    private EntityManager entityManager;

    public boolean addCost(Expenditure cost) {
        /**
         * TODO: implement dao communication for expenditure insertion
         */
        if (cost.getId() == null || cost.getId().isEmpty()) {
            cost.setId(UUID.randomUUID().toString());
        }
        costInterface.save(cost);
        return true;
    }

    public boolean addRevenue(Revenue rev) {
        /**
         * TODO: implement dao communication for revenue insertion
         */
        if (rev.getId() == null || rev.getId().isEmpty()) {
            rev.setId(UUID.randomUUID().toString());
        }
        revenuInterface.save(rev);
        return true;
    }

    public Optional<Expenditure> getCostById(String id) {
        return costInterface.findById(id);
    }

    public Optional<Revenue> getRevenueById(String id) {
        return revenuInterface.findById(id);
    }

    public List<Expenditure> getAllCosts() {
        return costInterface.findAll();
    }

    public List<Revenue> getAllRevenues() {
        return revenuInterface.findAll();
    }

    public ArrayList<Expenditure> getCostsByTerm(Long startTime, Long endTime) {
        /**
         * TODO: implement dao communication to get expenditureList by time boundaries
         */
        ArrayList<Expenditure> costList = (ArrayList<Expenditure>) costInterface.findCostByTimes(startTime, endTime);
        return costList;
    }

    public ArrayList<Revenue> getRevenuesByTerm(Long startTime, Long endTime) {
        /**
         * TODO: implement dao communication to get revenueList by time boundaries
         */
        ArrayList<Revenue> revenueList = (ArrayList<Revenue>) revenuInterface.findRevenuByTimes(startTime, endTime);
        return revenueList;
    }

    public Budget getBudgetByTerm(Long startTime, Long endTime) {
        ArrayList<Expenditure> costs = this.getCostsByTerm(startTime, endTime);
        ArrayList<Revenue> revenues = this.getRevenuesByTerm(startTime, endTime);
        Budget budget = new Budget(startTime, endTime, costs, revenues, null);
        return budget;
    }
}
