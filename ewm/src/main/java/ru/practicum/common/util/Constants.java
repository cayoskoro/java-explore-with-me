package ru.practicum.common.util;

import org.springframework.data.domain.PageRequest;

public final class Constants {
    private Constants() {
    }

    public static PageRequest getDefaultPageRequest(int from, int size) {
        return PageRequest.of(from > 0 ? from / size : 0, size);
    }
}