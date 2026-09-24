package com.foundercommandcenter;

import java.util.HashSet;
import java.util.Set;

public final class ExecutiveState {
    private ExecutiveState() {}

    public static Set<String> completePriority(Set<String> completed, String id) {
        Set<String> next = new HashSet<>(completed);
        next.add(id);
        return next;
    }

    public static Set<String> resolveDecision(Set<String> resolved, String id) {
        Set<String> next = new HashSet<>(resolved);
        next.add(id);
        return next;
    }

    public static int openCount(int total, Set<String> completed) {
        return Math.max(0, total - completed.size());
    }
}
