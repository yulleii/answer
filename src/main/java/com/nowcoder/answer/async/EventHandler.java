package com.nowcoder.answer.async;

import java.util.List;

public interface EventHandler {
    void doHandle(EventModel model);

    List<EventType>getSupportEventTypes();
}
