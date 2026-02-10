package com.example.bfhl.service;

import com.example.bfhl.dto.BfhlRequest;
import com.example.bfhl.dto.BfhlResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BfhlService {

    private final AiService aiService;

    @Value("${chitkara.email}")
    private String officialEmail;

    @Autowired
    public BfhlService(AiService aiService) {
        this.aiService = aiService;
    }

    public BfhlResponse processRequest(BfhlRequest request) {
        if (request.getFibonacci() != null) {
            return new BfhlResponse(true, officialEmail, calculateFibonacci(request.getFibonacci()));
        } else if (request.getPrime() != null) {
            return new BfhlResponse(true, officialEmail, filterPrimes(request.getPrime()));
        } else if (request.getLcm() != null) {
            return new BfhlResponse(true, officialEmail, calculateLcm(request.getLcm()));
        } else if (request.getHcf() != null) {
            return new BfhlResponse(true, officialEmail, calculateHcf(request.getHcf()));
        } else if (request.getAI() != null) {
            return new BfhlResponse(true, officialEmail, aiService.getAiResponse(request.getAI()));
        }
        return new BfhlResponse(false, officialEmail, "Invalid Request");
    }

    private List<Integer> calculateFibonacci(int n) {
        List<Integer> fib = new ArrayList<>();
        if (n < 1)
            return fib;
        int a = 0, b = 1;
        fib.add(a);
        if (n == 1)
            return fib;
        fib.add(b);
        for (int i = 2; i < n; i++) {
            int next = a + b;
            fib.add(next);
            a = b;
            b = next;
        }
        return fib;
    }

    private List<Integer> filterPrimes(List<Integer> numbers) {
        return numbers.stream().filter(this::isPrime).collect(Collectors.toList());
    }

    private boolean isPrime(int n) {
        if (n <= 1)
            return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    private int calculateLcm(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty())
            return 0;
        int result = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            result = lcm(result, numbers.get(i));
        }
        return result;
    }

    private int lcm(int a, int b) {
        if (a == 0 || b == 0)
            return 0;
        return Math.abs(a * b) / hcf(a, b);
    }

    private int calculateHcf(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty())
            return 0;
        int result = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            result = hcf(result, numbers.get(i));
        }
        return result;
    }

    private int hcf(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
