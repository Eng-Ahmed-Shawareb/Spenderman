package com.spenderman.service;

import com.spenderman.model.*;
import com.spenderman.model.StatusEnums.EnTransactionType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ClsAiChatService {

    private static final String API_KEY = "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";
    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";
    private static final String MODEL = "llama-3.3-70b-versatile";

    private final HttpClient _httpClient;
    private final ClsTransactionService _transactionService;
    private final ClsWalletService _walletService;
    private final ClsCategoryService _categoryService;
    private final ClsCycleService _cycleService;
    private final ClsSavingGoalService _savingGoalService;

    public ClsAiChatService() {
        _httpClient = HttpClient.newHttpClient();
        _transactionService = new ClsTransactionService();
        _walletService = new ClsWalletService();
        _categoryService = new ClsCategoryService();
        _cycleService = new ClsCycleService();
        _savingGoalService = new ClsSavingGoalService();
    }

    private String _prepareFinancialContext(int userId) {
        StringBuilder context = new StringBuilder();
        try {

            double totalBalance = _walletService.getTotalBalance(userId);
            context.append(String.format("Total balance: %.2f EGP. ", totalBalance));

            try {
                double totalIncome = 0, totalExpenses = 0;
                List<ClsTransaction> transactions = _transactionService.getByUser(userId);
                for (ClsTransaction t : transactions) {
                    if (t.get_type() == EnTransactionType.EXPENSE) totalExpenses += t.get_amount();
                    else if (t.get_type() == EnTransactionType.DEPOSIT) totalIncome += t.get_amount();
                }
                context.append(String.format("Total income: %.2f EGP. Total expenses: %.2f EGP. Transactions count: %d. ",
                        totalIncome, totalExpenses, transactions.size()));
            } catch (Exception e) {}

            try {
                Optional<ClsCycle> activeCycle = _cycleService.getActiveCycle(userId);
                if (activeCycle.isPresent()) {
                    context.append(String.format("Active Budget Cycle Amount: %.2f EGP. ", activeCycle.get().get_budgetAmount()));
                } else {
                    context.append("No active budget cycle set. ");
                }
            } catch (Exception e) {}

            try {
                List<ClsSavingGoal> goals = _savingGoalService.getByUser(userId);
                if (!goals.isEmpty()) {
                    context.append("Saving Goals: ");
                    for (ClsSavingGoal g : goals) {
                        context.append(String.format("[Target: %.2f, Currently Saved: %.2f, Status: %s] ",
                                g.get_targetAmount(), g.get_currentSaved(), g.getStatus()));
                    }
                } else {
                    context.append("No saving goals. ");
                }
            } catch (Exception e) {}

            try {
                List<ClsCategory> categories = _categoryService.getByUser(userId);
                context.append(String.format("User has configured %d custom categories. ", categories.size()));
            } catch (Exception e) {

            }

            return context.toString();
        } catch (Exception e) {
            return "No financial data available.";
        }
    }

    public CompletableFuture<String> getAiResponse(String userMessage, int userId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String context = _prepareFinancialContext(userId);

                JSONObject requestBody = new JSONObject()
                        .put("model", MODEL)
                        .put("messages", new JSONArray()
                                .put(new JSONObject()
                                        .put("role", "system")
                                        .put("content", "You are a smart financial assistant inside an app called 'Spenderman'. " +
                                                "User financial summary: [" + context + "]. " +
                                                "Give short and helpful financial advice. " +
                                                "Always reply in the same language the user uses."))
                                .put(new JSONObject()
                                        .put("role", "user")
                                        .put("content", userMessage)))
                        .put("max_tokens", 500);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(API_URL))
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + API_KEY)
                        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                        .build();

                HttpResponse<String> response = _httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                switch (response.statusCode()) {
                    case 200:
                        JSONObject json = new JSONObject(response.body());
                        return json.getJSONArray("choices")
                                .getJSONObject(0)
                                .getJSONObject("message")
                                .getString("content");
                    case 429:
                        return "⚠️ AI assistant has reached its limit, please try again later.";
                    case 401:
                        return "⚠️ Invalid or expired API Key.";
                    default:
                        System.err.println("❌ Error " + response.statusCode() + ": " + response.body());
                        return "⚠️ An unexpected error occurred, please try again.";
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "⚠️ Connection error: " + e.getMessage();
            }
        });
    }
}