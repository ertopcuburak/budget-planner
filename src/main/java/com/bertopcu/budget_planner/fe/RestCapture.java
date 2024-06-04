package com.bertopcu.budget_planner.fe;

import com.bertopcu.budget_planner.Model.*;
import com.bertopcu.budget_planner.Services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestCapture {
    @Autowired
    BudgetService budgetService;

    @PostMapping("/cost/add")
    public ResponseEntity<Response> createExpenditure(@RequestBody Expenditure expenditure) {
        boolean result = budgetService.addCost(expenditure);
        String responseMessage = "Expenditure " + expenditure.getTitle() + " with amount " + expenditure.getRate() + " has been created.";
        Response response = new Response(responseMessage, false, expenditure);
        if(!result) {
            response.setMessage("Expenditure " + expenditure.getTitle() + " with amount " + expenditure.getRate() + " could not be created.");
            response.setHasErrors(true);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/revenue/add")
    public ResponseEntity<Response> createRevenue(@RequestBody Revenue revenue) {
        boolean result = budgetService.addRevenue(revenue);
        String responseMessage = "Revenue " + revenue.getTitle() + " with amount " + revenue.getRate() + " has been created.";
        Response response = new Response(responseMessage, false, revenue);
        if(!result) {
            response.setMessage("Revenue " + revenue.getTitle() + " with amount " + revenue.getRate() + " could not be created.");
            response.setHasErrors(true);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/budget/get")
    public ResponseEntity<Response> getBudget(@RequestBody BudgetRequest budgetRequest) {
        Budget budget = null;
        String responseMessage = null;
        Response response = null;
        if(budgetRequest.getStartTime() == null || budgetRequest.getEndTime() == null) {
            responseMessage = "startTime and endTime are both mandatory fields for this request! Please fill them both and retry!";
            response = new Response(responseMessage, true, budget);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if(budgetRequest.getCurrency() == null) {
            budgetRequest.setCurrency("EUR");
        }

        budget = this.budgetService.getBudgetByTerm(budgetRequest.getStartTime(), budgetRequest.getEndTime());
        if(budget == null || budget.getBalance() == null) {
            response.setMessage("An error had occurred during calculation of budget!");
            response.setHasErrors(true);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        responseMessage = "Budget balance for term between " + budget.getStartTime() + " and " + budget.getEndTime() + " is " + budget.getBalance() + " EUR";
        response = new Response(responseMessage, false, budget);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
