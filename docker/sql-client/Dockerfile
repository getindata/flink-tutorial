ARG FLINK_VERSION
FROM flink:$FLINK_VERSION-scala_2.12-java11
ARG FLINK_VERSION

RUN mkdir -p /opt/sql-client/lib/
RUN mkdir -p /opt/sql-client/conf/

# FIXME: $FLINK_VERSION does not work
# Download connector libraries
RUN wget -P /opt/sql-client/lib/ https://repo.maven.apache.org/maven2/org/apache/flink/flink-sql-connector-kafka_2.12/$FLINK_VERSION/flink-sql-connector-kafka_2.12-$FLINK_VERSION.jar; \
    wget -P /opt/sql-client/lib/ https://repo.maven.apache.org/maven2/org/apache/flink/flink-sql-avro-confluent-registry/$FLINK_VERSION/flink-sql-avro-confluent-registry-$FLINK_VERSION.jar;

COPY sql-client.sh /opt/sql-client/
COPY sql-client-conf.yaml /opt/sql-client/conf/

WORKDIR /opt/sql-client/
ENV SQL_CLIENT_HOME /opt/sql-client/

COPY docker-entrypoint.sh /
ENTRYPOINT ["/docker-entrypoint.sh"]
