package com.queuing.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody; // Import this

@Controller
public class QueueCalculatorController {

    @RequestMapping("/")
    public String showForm() {
        return "queue-calculator";
    }

    @PostMapping("/calculate")
    @ResponseBody
    public String calculateQueueMetrics(@RequestParam("arrivalRate") double arrivalRate,
                                        @RequestParam("serviceRate") double serviceRate,
                                        @RequestParam("servicePoints") int servicePoints,
                                        Model model) {
        double lambda = arrivalRate;
        double mu = serviceRate;
        int S = servicePoints;

        
        double rho = lambda / (S * mu);

        
        double P0 = 0.0;

        // Calculate the denominator part of P0
        double denominator = 0.0;
        for (int n = 0; n < S; n++) {
            denominator += (Math.pow(lambda / mu, n) / factorial(n));
        }
        denominator += (Math.pow(lambda / mu, S) / (factorial(S) * (1 - rho)));

        
        P0 = 1.0 / denominator;

        // Calculate the number of customers in the queue (Lq)
        double Lq = (rho * P0 * Math.pow(lambda / mu, S)) / (factorial(S) * Math.pow(1 - rho, 2));
       
        double L = Lq + (lambda / mu);
        // Calculate Wq
        double Wq = Lq / lambda;
        // Calculate W
        double W = L / lambda;

       
        String results = "Expected Waiting Time per Customer in Queue: " + Wq + "\n";
        results += "Expected Waiting Time per Customer in System: " + W + "\n";
        results += "Number of Customers in System: " + L + "\n";
        results += "Number of Customers in Queue: " + Lq + "\n";
        results += "Probability of Idle System: " + P0 + "\n";

        return results;
    }

    private double factorial(int n) {
        if (n <= 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }
}

