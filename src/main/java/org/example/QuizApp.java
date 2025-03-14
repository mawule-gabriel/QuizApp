package org.example;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.InetSocketAddress;
import java.util.Random;

public class QuizApp {

    // List to store quiz questions
    private static final List<Question> questions = QuestionBank.getQuestions();
    private static int currentScore = 0;
    private static int totalQuestions = 0;
    private static Question currentQuestion = null;
    private static boolean quizCompleted = false; // Track if quiz is completed

    static class Question {
        int id;
        String questionText;
        List<String> options;
        String correctAnswer;

        Question(int id, String questionText, List<String> options, String correctAnswer) {
            this.id = id;
            this.questionText = questionText;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }
    }

    public static void main(String[] args) throws Exception {
        // Use port 5000 as default or use the PORT environment variable
        String port = System.getenv("PORT");
        int serverPort = (port != null && !port.isEmpty()) ? Integer.parseInt(port) : 5000;

        // Create HTTP server listening on the specified port
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        server.createContext("/", new QuizHandler());
        server.createContext("/answer", new AnswerHandler());
        server.createContext("/reset", new ResetHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started at http://localhost:" + serverPort + "/");
    }

    // Parse URL encoded parameters
    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> parameters = new HashMap<>();
        if (formData != null && !formData.isEmpty()) {
            String[] pairs = formData.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                String key = URLDecoder.decode(keyValue[0], "UTF-8");
                String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1], "UTF-8") : "";
                parameters.put(key, value);
            }
        }
        return parameters;
    }

    // Extract form data from HTTP request
    private static String extractFormData(HttpExchange exchange) throws IOException {
        InputStreamReader reader = new InputStreamReader(exchange.getRequestBody());
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder formData = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            formData.append(line);
        }
        return formData.toString();
    }

    // Handler for resetting the quiz
    static class ResetHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Reset quiz state
            currentScore = 0;
            totalQuestions = 0;
            currentQuestion = null;
            quizCompleted = false;

            // Redirect back to the main page
            exchange.getResponseHeaders().set("Location", "/");
            exchange.sendResponseHeaders(303, -1);
        }
    }

    // Handler for processing answers
    static class AnswerHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                String formData = extractFormData(exchange);
                Map<String, String> parameters = parseFormData(formData);

                if (parameters.containsKey("answer") && currentQuestion != null) {
                    String userAnswer = parameters.get("answer");
                    totalQuestions++;

                    if (userAnswer.equals(currentQuestion.correctAnswer)) {
                        currentScore++;
                    }
                }

                // Once all questions are answered, mark the quiz as completed
                if (totalQuestions == questions.size()) {
                    quizCompleted = true;
                }

                // Clear the current question to force a new question on the next load
                currentQuestion = null;

                // Redirect back to the main page
                exchange.getResponseHeaders().set("Location", "/");
                exchange.sendResponseHeaders(303, -1);
            }
        }
    }

    // Main handler to manage the requests
    static class QuizHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // If the quiz is completed, show the result (Pass/Fail)
            if (quizCompleted) {
                String result = (currentScore / (double) totalQuestions) >= 0.7 ? "Pass" : "Fail";

                StringBuilder html = new StringBuilder();
                html.append("<!DOCTYPE html>");
                html.append("<html lang='en'>");
                html.append("<head>");
                html.append("<meta charset='UTF-8'>");
                html.append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                html.append("<title>Quiz Completed</title>");
                html.append("<style>");
                html.append("* { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Arial', sans-serif; }");
                html.append("body { background-color: #f0f2f5; display: flex; justify-content: center; align-items: center; min-height: 100vh; margin: 0; }");
                html.append(".quiz-container { background-color: white; border-radius: 10px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); padding: 30px; width: 100%; max-width: 500px; }");
                html.append("h1 { color: #2c3e50; text-align: center; margin-bottom: 20px; }");
                html.append(".score { text-align: center; margin-bottom: 20px; font-size: 18px; color: #7f8c8d; }");
                html.append(".reset-btn { display: block; width: 100%; padding: 10px; background-color: #e74c3c; color: white; border: none; border-radius: 8px; margin-top: 20px; cursor: pointer; }");
                html.append(".reset-btn:hover { background-color: #c0392b; }");
                html.append("</style>");
                html.append("</head>");
                html.append("<body>");
                html.append("<div class='quiz-container'>");
                html.append("<h1>You ").append(result).append("!</h1>");
                html.append("<p>Your marks: ").append(currentScore).append(" / ").append(totalQuestions).append("</p>");

                // Reset button
                html.append("<form method='POST' action='/reset'>");
                html.append("<button type='submit' class='reset-btn'>Reset Quiz</button>");
                html.append("</form>");

                html.append("</div>");
                html.append("</body>");
                html.append("</html>");

                // Respond with the HTML content
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                byte[] responseBytes = html.toString().getBytes("UTF-8");
                exchange.sendResponseHeaders(200, responseBytes.length);
                OutputStream os = exchange.getResponseBody();
                os.write(responseBytes);
                os.close();

                return; // End execution here if quiz is completed
            }

            // If the quiz isn't completed, serve a question
            if (currentQuestion == null && !questions.isEmpty()) {
                Random random = new Random();
                currentQuestion = questions.get(random.nextInt(questions.size()));
            }

            // HTML structure for the Quiz page
            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html>");
            html.append("<html lang='en'>");
            html.append("<head>");
            html.append("<meta charset='UTF-8'>");
            html.append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            html.append("<title>Quiz App</title>");
            html.append("<style>");
            html.append("* { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Arial', sans-serif; }");
            html.append("body { background-color: #f0f2f5; display: flex; justify-content: center; align-items: center; min-height: 100vh; margin: 0; }");
            html.append(".quiz-container { background-color: white; border-radius: 10px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); padding: 30px; width: 100%; max-width: 500px; }");
            html.append("h1 { color: #2c3e50; text-align: center; margin-bottom: 20px; }");
            html.append(".score { text-align: center; margin-bottom: 20px; font-size: 18px; color: #7f8c8d; }");
            html.append(".question { background-color: #ecf0f1; padding: 20px; border-radius: 8px; margin-bottom: 20px; }");
            html.append(".options { display: grid; grid-template-columns: 1fr 1fr; gap: 15px; }");
            html.append(".option { background-color: #3498db; color: white; border: none; padding: 15px; border-radius: 8px; cursor: pointer; transition: background-color 0.3s; }");
            html.append(".option:hover { background-color: #2980b9; }");
            html.append(".reset-btn { display: block; width: 100%; padding: 10px; background-color: #e74c3c; color: white; border: none; border-radius: 8px; margin-top: 20px; cursor: pointer; }");
            html.append(".reset-btn:hover { background-color: #c0392b; }");
            html.append("</style>");
            html.append("</head>");
            html.append("<body>");
            html.append("<div class='quiz-container'>");

            // Display score
            html.append("<div class='score'>Score: ").append(currentScore).append(" / ").append(totalQuestions).append("</div>");

            // Display current question
            if (currentQuestion != null) {
                html.append("<div class='question'>").append(currentQuestion.questionText).append("</div>");
                html.append("<form method='POST' action='/answer' class='options'>");

                // Shuffle options to prevent pattern recognition
                List<String> shuffledOptions = new ArrayList<>(currentQuestion.options);
                java.util.Collections.shuffle(shuffledOptions);

                for (String option : shuffledOptions) {
                    html.append("<button type='submit' name='answer' value='")
                            .append(option)
                            .append("' class='option'>")
                            .append(option)
                            .append("</button>");
                }
                html.append("</form>");
            }

            // Reset button
            html.append("<form method='POST' action='/reset'>");
            html.append("<button type='submit' class='reset-btn'>Reset Quiz</button>");
            html.append("</form>");

            html.append("</div>");
            html.append("</body>");
            html.append("</html>");

            // Respond with the HTML content
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            byte[] responseBytes = html.toString().getBytes("UTF-8");
            exchange.sendResponseHeaders(200, responseBytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(responseBytes);
            os.close();
        }
    }
}
