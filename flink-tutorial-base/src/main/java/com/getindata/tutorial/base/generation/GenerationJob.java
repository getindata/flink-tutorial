package com.getindata.tutorial.base.generation;

import com.getindata.tutorial.base.kafka.KafkaProperties;
import com.getindata.tutorial.base.model.SongEvent;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;

public class GenerationJob extends GenerationHelper<SongEvent> {
    protected GenerationJob(String topic, KafkaRecordSerializationSchema<SongEvent> serializer) {
        super(serializer);
    }

    public static void main(String[] args) throws Exception {
        GenerationJob job = new GenerationJob(
                KafkaProperties.getInputTopic(),
                KafkaRecordSerializationSchema.<SongEvent>builder()
                        .setTopic(KafkaProperties.getInputTopic())
                        .build()
        );

        job.run();
    }

    @Override
    protected SongEvent map(SongEvent event) {
        return event;
    }

    @Override
    protected Class<SongEvent> getConcreteClass() {
        return SongEvent.class;
    }
}