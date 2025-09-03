package test.utils;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class TestRailReporter {

    private static final String BASE_URL = "https://eldiyarqa88.testrail.io/";
    private static final String USER = "musibei9@gmail.com";
    private static final String API_KEY = "n7dquGLPvLhzcgzfSi/b-tJXuMYdIRpu/3J5Oo23r"; // замените на реальный API Key
    public static final String RUN_ID = "1"; // замените на реальный цифровой ID Test Run

    public static void updateResult(String runId, String caseId, int statusId, String comment) {
        try {
            String url = BASE_URL + "index.php?/api/v2/add_result_for_case/" + runId + "/" + caseId;

            JSONObject payload = new JSONObject();
            payload.put("status_id", statusId);
            payload.put("comment", comment);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Basic " +
                            Base64.getEncoder().encodeToString((USER + ":" + API_KEY).getBytes()))
                    .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("TestRail response: " + response.statusCode());
            System.out.println(response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

