package com.getindata;

import com.getindata.tutorial.base.generation.GenerationHelper;
import com.getindata.tutorial.base.kafka.KafkaProperties;
import com.getindata.tutorial.base.model.SongEvent;
import com.getindata.tutorial.base.model.SongEventAvro;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.formats.avro.registry.confluent.ConfluentRegistryAvroSerializationSchema;

public class GenerationJobAvro extends GenerationHelper<SongEventAvro> {

    protected GenerationJobAvro(KafkaRecordSerializationSchema<SongEventAvro> serializer) {
        super(serializer);
    }

    public static void main(String[] args) throws Exception {
        GenerationJobAvro job = new GenerationJobAvro(
                KafkaRecordSerializationSchema.<SongEventAvro>builder()
                        .setTopic(KafkaProperties.getInputAvroTopic())
                        .setValueSerializationSchema(
                                ConfluentRegistryAvroSerializationSchema.forSpecific(
                                        SongEventAvro.class,
                                        SongEventAvro.class.getSimpleName(),
                                        KafkaProperties.getSchemaRegistryUrl())
                        )
                        .build()
        );

        job.run();
    }

    @Override
    protected SongEventAvro map(SongEvent event) {
        // FIXME: pass constructor parameters properly
        return new SongEventAvro(
                // event.getSongId(),
                // event.getTimestamp(),
                // event.getType().toString(),
                // event.getUserId()
        );
    }

    @Override
    protected Class<SongEventAvro> getConcreteClass() {
        return SongEventAvro.class;
    }
}