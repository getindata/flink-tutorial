package com.getindata.tutorial.base.kafka;


import com.getindata.tutorial.base.utils.EnvironmentChecker;

import static com.getindata.tutorial.base.utils.EnvironmentChecker.isRunningFromIntellij;

public class KafkaProperties {

    private static final String INPUT_TOPIC = "input";
    private static final String INPUT_AVRO_TOPIC = "input-avro";
    private static final String OUTPUT_AVRO_TOPIC = "output-avro";
    private static final String OUTPUT_SQL_AVRO_TOPIC = "output-sql-avro";
    private static final String GROUP_ID = "tutorial-1";
    private static final String BOOTSTRAP_SERVERS = "kafka:9092";
    private static final String BOOTSTRAP_SERVERS_FOR_HOST = "localhost:29092";
    private static final String SCHEMA_REGISTRY_URL = "http://schema-registry:8082";
    private static final String SCHEMA_REGISTRY_URL_FOR_HOST = "http://localhost:8082";

    public static String getInputTopic() {
        return INPUT_TOPIC;
    }

    public static String getInputAvroTopic() {
        return INPUT_AVRO_TOPIC;
    }

    public static String getOutputAvroTopic() {
        return OUTPUT_AVRO_TOPIC;
    }

    public static String getOutputSqlAvroTopic() {
        return OUTPUT_SQL_AVRO_TOPIC;
    }

    public static String getGroupId() {
        return GROUP_ID;
    }

    public static String getBootstrapServers() {
        return isRunningFromIntellij() ? BOOTSTRAP_SERVERS_FOR_HOST : BOOTSTRAP_SERVERS;
    }

    public static String getSchemaRegistryUrl() {
        return isRunningFromIntellij() ? SCHEMA_REGISTRY_URL_FOR_HOST : SCHEMA_REGISTRY_URL;
    }
}
