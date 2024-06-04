package com.bertopcu.budget_planner.Services;

import com.bertopcu.budget_planner.DAO.ExpenditureInterface;
import com.bertopcu.budget_planner.DAO.RevenueInterface;
import com.bertopcu.budget_planner.Model.Budget;
import com.bertopcu.budget_planner.Model.Expenditure;
import com.bertopcu.budget_planner.Model.Revenue;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
        costInterface.save(cost);
        return true;
    }

    public boolean addRevenue(Revenue rev) {
        /**
         * TODO: implement dao communication for revenue insertion
         */
        revenuInterface.save(rev);
        return true;
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
