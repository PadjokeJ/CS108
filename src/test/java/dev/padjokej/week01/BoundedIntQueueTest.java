package dev.padjokej.week01;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;

/// Test class to check if Bounded Int Queues behave as expected
///
/// @author Jonatan Pfister
class BoundedIntQueueTest {
    @Test
    void boundedQueueThrowsOnCreateNegativeCapacity() {
        assertThrows(IllegalArgumentException.class, () -> {new BoundedIntQueueBuggy(-1);});
    }

    @Test
    void addLastThrowsOnFull() {
        assertThrows(IllegalStateException.class, () -> {
            var b = new BoundedIntQueueBuggy(0);
            b.addLast(1);
        });
    }

    @Test
    void getFirstThrowsOnEmpty() {
        assertThrows(IllegalStateException.class, () -> {
            var b = new BoundedIntQueueBuggy(0);
            int a = b.removeFirst();
        });
    }

    @Test
    void getFirstGetsFirstElement() {
        BoundedIntQueueBuggy b = new BoundedIntQueueBuggy(2);
        b.addLast(0);
        b.addLast(1);

        assertEquals(0, b.removeFirst());
    }

    @Test
    void isFullReturnsFullOnEmptyCapacity() {
        assertTrue(() -> {
            BoundedIntQueueBuggy b = new BoundedIntQueueBuggy(0);
            return b.isFull();
        });
    }

    @Test
    void isFullReturnsAccordingToItsOtherFunctions() {
        BoundedIntQueueBuggy b = new BoundedIntQueueBuggy(1);
        b.addLast(10);
        assertEquals(b.capacity() == b.size(), b.isFull());
    }

    @Test
    void isFullReturnsFalseOnEmpty() {
        BoundedIntQueueBuggy b = new BoundedIntQueueBuggy(1);

        assertFalse(b.isFull());
    }

    @Test
    void isEmptyReturnsTrueOnZeroCapacity() {
        assertTrue(new BoundedIntQueueBuggy(0).isEmpty());
    }

    @Test
    void isEmptyReturnsFalseOnFull() {
        BoundedIntQueueBuggy b = new BoundedIntQueueBuggy(1);
        b.addLast(1);
        assertFalse(b.isEmpty());
    }

    @Test
    void capacityReturnsMaximumCapacityOfQueue() {
        RandomGenerator rng = new Random();
        int capacity = rng.nextInt(0, 1000);
        BoundedIntQueueBuggy b = new BoundedIntQueueBuggy(capacity);
        assertEquals(capacity, b.capacity());
    }

    @Test
    void sizeOfQueueIncreasesOnElementAdd() {
        BoundedIntQueueBuggy b = new BoundedIntQueueBuggy(10);

        assertEquals(0, b.size());

        b.addLast(1);
        assertEquals(1, b.size());

        b.addLast(1);
        assertEquals(2, b.size());

        b.addLast(1);
        assertEquals(3, b.size());

        b.addLast(1);
        assertEquals(4, b.size());

        b.addLast(1);
        assertEquals(5, b.size());
    }
}