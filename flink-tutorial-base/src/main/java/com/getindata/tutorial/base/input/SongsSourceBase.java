package com.getindata.tutorial.base.input;

import com.getindata.tutorial.base.input.utils.MergedIterator;
import com.getindata.tutorial.base.input.utils.UserSessions;
import com.getindata.tutorial.base.model.EnrichedSongEvent;
import com.getindata.tutorial.base.model.SongEvent;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * A source that generates artificial traffic.
 */
public abstract class SongsSourceBase<T> extends RichParallelSourceFunction<T> {

    private boolean isRunning = true;

    private final int numberOfUsers;

    private final Duration sessionGap;

    private final Duration outOfOrderness;

    private final int speed;

    /**
     * Creates a source that generates {@link SongEvent}s. You can configure with few parameters
     *
     * @param numberOfUsers  number of users for which the events will be generated
     * @param sessionGap     gap in time between last event in a single user session and the next session
     * @param outOfOrderness time that events for users with event id will be delayed in contrast to other users events
     * @param speed          speed of events generation (max 100). The smaller the faster events will be generated
     */
    public SongsSourceBase(int numberOfUsers, Duration sessionGap, Duration outOfOrderness, int speed) {
        this.numberOfUsers = numberOfUsers;
        this.sessionGap = sessionGap;
        this.outOfOrderness = outOfOrderness;
        this.speed = Math.min(Math.max(1, speed), 100);
    }

    public SongsSourceBase() {
        this(10, Duration.ofMinutes(20), Duration.ofMinutes(5), 10);
    }

    protected abstract T mapEvent(EnrichedSongEvent event);

    @Override
    public void run(SourceContext<T> sourceContext) throws Exception {
        final List<Iterator<EnrichedSongEvent>> sessions = IntStream.rangeClosed(1, numberOfUsers)
                .filter(
                        i -> i % getRuntimeContext().getNumberOfParallelSubtasks() == getRuntimeContext().getIndexOfThisSubtask()
                )
                .mapToObj(
                        i -> new UserSessions(i, sessionGap, Instant.now().toEpochMilli()).getSongs().iterator()
                )
                .collect(toList());

        final MergedIterator<EnrichedSongEvent> mergedIterator = new MergedIterator<>(sessions,
                Comparator.comparingLong(songEvent -> {
                            if (songEvent.getUserId() % 2 == 0) {
                                return songEvent.getTimestamp();
                            } else {
                                return songEvent.getTimestamp() + outOfOrderness.toMillis();
                            }
                        }
                ));

        while (isRunning && mergedIterator.hasNext()) {
            sourceContext.collect(mapEvent(mergedIterator.next()));
            Thread.sleep(20 * speed);
        }

    }

    @Override
    public void cancel() {
        this.isRunning = false;
    }
}
