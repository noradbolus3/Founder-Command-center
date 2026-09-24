package com.foundercommandcenter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ExecutiveStateTest {
    @Test public void completingPriorityDoesNotMutateOriginal() {
        Set<String> original = new HashSet<>();
        Set<String> next = ExecutiveState.completePriority(original, "p1");
        assertTrue(next.contains("p1"));
        assertFalse(original.contains("p1"));
    }

    @Test public void resolvingDecisionIsIdempotent() {
        Set<String> once = ExecutiveState.resolveDecision(Collections.singleton("d1"), "d1");
        assertEquals(1, once.size());
    }

    @Test public void openCountNeverGoesNegative() {
        assertEquals(3, ExecutiveState.openCount(3, Collections.emptySet()));
        assertEquals(0, ExecutiveState.openCount(3, new HashSet<>(java.util.Arrays.asList("p1", "p2", "p3", "p4"))));
    }
}
