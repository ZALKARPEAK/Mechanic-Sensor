-- Flyway Migration Script: V2__Create_sensors_and_measurements_tables.sql

DROP TABLE IF EXISTS measurements CASCADE;
DROP TABLE IF EXISTS sensors CASCADE;

CREATE TABLE IF NOT EXISTS sensors (
                                       id SERIAL PRIMARY KEY,
                                       name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS measurements (
                                            id SERIAL PRIMARY KEY,
                                            value DOUBLE PRECISION NOT NULL,
                                            raining BOOLEAN NOT NULL,
                                            sensor_id INT NOT NULL
);

ALTER TABLE measurements
    ADD CONSTRAINT fk_measurements_sensor_id
        FOREIGN KEY (sensor_id) REFERENCES sensors(id);