package tech.dock.desafio.api.infrastructure.fixture;

import org.jeasy.random.EasyRandom;

public class Fixture {
    static EasyRandom easyRandom = new EasyRandom();

    @SuppressWarnings("unchecked")
    public static <T> T make(final T mockClass) {
        return (T) easyRandom.nextObject(mockClass.getClass());
    }
}