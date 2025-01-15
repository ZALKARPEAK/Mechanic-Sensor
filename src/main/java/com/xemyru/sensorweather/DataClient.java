package com.xemyru.sensorweather;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataClient {

    private static final String BASE_URL = "http://localhost:8080";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Random random = new Random();

    public static void main(String[] args) throws IOException, InterruptedException {
        int sensorId = registerSensor("MySensor");
        System.out.println("Зарегистрирован сенсор с ID: " + sensorId);

        sendMeasurements(sensorId, 1000);
        System.out.println("Отправлено 1000 измерений");

        List<Measurement> measurements = getAllMeasurements();
        System.out.println("Получено " + measurements.size() + " измерений:");
    }

    private static int registerSensor(String sensorName) throws IOException, InterruptedException {
        SensorRegistrationRequest requestBody = new SensorRegistrationRequest(sensorName);
        String jsonBody = mapper.writeValueAsString(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/sensor/registration"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
           SensorResponse sensorResponse = mapper.readValue(response.body(), SensorResponse.class);
            return sensorResponse.getId();
        } else {
            throw new IOException("Ошибка регистрации сенсора: " + response.statusCode() + ", " + response.body());
        }

    }

    private static void sendMeasurements(int sensorId, int count) throws IOException, InterruptedException {
        List<MeasurementRequest> measurements = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            double value = 20 + random.nextDouble() * 20;
            boolean raining = random.nextBoolean();
            measurements.add(new MeasurementRequest(value, raining, sensorId));
        }
        String jsonBody = mapper.writeValueAsString(measurements);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/measurement/add"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Ошибка отправки измерений: " + response.statusCode() + ", " + response.body());
        }
    }

    private static List<Measurement> getAllMeasurements() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/measurement"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

         if (response.statusCode() == 200) {
              return mapper.readValue(response.body(), new TypeReference<List<Measurement>>() {});
         } else {
             throw new IOException("Ошибка получения измерений: " + response.statusCode() + ", " + response.body());
         }
    }

    static class SensorRegistrationRequest {
        private String name;

        public SensorRegistrationRequest(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    static class SensorResponse{
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    static class MeasurementRequest {
        private double value;
        private boolean raining;
        private int sensorId;

        public MeasurementRequest(double value, boolean raining, int sensorId) {
            this.value = value;
            this.raining = raining;
            this.sensorId = sensorId;
        }

         public MeasurementRequest() {
        }


        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public boolean isRaining() {
            return raining;
        }

        public void setRaining(boolean raining) {
            this.raining = raining;
        }

        public int getSensorId() {
            return sensorId;
        }

        public void setSensorId(int sensorId) {
            this.sensorId = sensorId;
        }
    }

   static class Measurement {
        private int id;
        private double value;
        private boolean raining;
        private int sensorId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public boolean isRaining() {
            return raining;
        }

        public void setRaining(boolean raining) {
            this.raining = raining;
        }

        public int getSensorId() {
            return sensorId;
        }

        public void setSensorId(int sensorId) {
            this.sensorId = sensorId;
        }

        @Override
        public String toString() {
            return "Measurement{" +
                   "id=" + id +
                   ", value=" + value +
                   ", raining=" + raining +
                   ", sensorId=" + sensorId +
                   '}';
        }
    }
}