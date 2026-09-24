package com.foundercommandcenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends android.app.Activity {
    private static final int BG = Color.rgb(11, 17, 16);
    private static final int SURFACE = Color.rgb(18, 26, 24);
    private static final int RAISED = Color.rgb(23, 35, 31);
    private static final int BORDER = Color.rgb(39, 53, 48);
    private static final int TEXT = Color.rgb(242, 240, 233);
    private static final int MUTED = Color.rgb(148, 163, 157);
    private static final int GOLD = Color.rgb(225, 186, 103);
    private static final int GREEN = Color.rgb(119, 215, 165);
    private static final int AMBER = Color.rgb(230, 191, 103);

    private LinearLayout content;
    private SharedPreferences prefs;
    private Set<String> completed = new HashSet<>();
    private Set<String> resolved = new HashSet<>();

    private final String[] priorityIds = {"p1", "p2", "p3"};
    private final String[] priorityTitles = {"Close the enterprise design partner", "Approve the Q4 hiring plan", "Ship the activation experiment readout"};
    private final String[] priorityContexts = {"Northstar account · $120k ARR potential", "2 critical hires · Product + GTM", "Growth pod · decision needed"};
    private final String[] priorityDue = {"TODAY", "TOMORROW", "WED"};

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setStatusBarColor(BG);
        window.setNavigationBarColor(BG);
        prefs = getSharedPreferences("founder_os", MODE_PRIVATE);
        completed = new HashSet<>(prefs.getStringSet("completed", new HashSet<>()));
        resolved = new HashSet<>(prefs.getStringSet("resolved", new HashSet<>()));
        buildShell();
        showCommand();
    }

    private void buildShell() {
        LinearLayout shell = new LinearLayout(this);
        shell.setOrientation(LinearLayout.VERTICAL);
        shell.setBackgroundColor(BG);

        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(20), dp(16), dp(20), dp(12));
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.addView(content);
        shell.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));

        LinearLayout nav = new LinearLayout(this);
        nav.setGravity(Gravity.CENTER);
        nav.setPadding(dp(8), dp(7), dp(8), dp(8));
        nav.setBackgroundColor(SURFACE);
        String[] labels = {"Command", "Plan", "Team", "Finance", "Copilot"};
        String[] icons = {"▥", "◎", "♟", "▣", "✦"};
        for (int i = 0; i < labels.length; i++) {
            final int tab = i;
            LinearLayout item = new LinearLayout(this);
            item.setOrientation(LinearLayout.VERTICAL);
            item.setGravity(Gravity.CENTER);
            TextView icon = text(icons[i], 21, i == 0 ? GOLD : MUTED, false);
            TextView label = text(labels[i], 10, i == 0 ? GOLD : MUTED, false);
            item.addView(icon);
            item.addView(label);
            item.setOnClickListener(v -> { if (tab == 0) showCommand(); else if (tab == 1) showPlan(); else if (tab == 2) showTeam(); else if (tab == 3) showFinance(); else showCopilot(); });
            nav.addView(item, new LinearLayout.LayoutParams(0, dp(58), 1));
        }
        shell.addView(nav, new LinearLayout.LayoutParams(-1, dp(70)));
        setContentView(shell);
    }

    private void showCommand() {
        reset("FOUNDER OS / 24 SEP 2026", "Good evening, Founder.");
        LinearLayout hero = card(RAISED, 18);
        LinearLayout heroTop = row();
        LinearLayout heroCopy = column();
        heroCopy.addView(eyebrow("NORTH STAR / Q3"));
        heroCopy.addView(spacer(7));
        heroCopy.addView(text("Become the operating system for founder-led companies.", 20, TEXT, true));
        heroTop.addView(heroCopy, new LinearLayout.LayoutParams(0, -2, 1));
        heroTop.addView(circle("FC", GOLD));
        hero.addView(heroTop);
        hero.addView(spacer(18));
        LinearLayout heroStats = row();
        heroStats.addView(metric("QUARTER CONFIDENCE", "78%", TEXT), new LinearLayout.LayoutParams(0, -2, 1));
        heroStats.addView(metric("NEXT CHECKPOINT", "Friday · 16:00", GOLD), new LinearLayout.LayoutParams(0, -2, 1));
        hero.addView(heroStats);
        hero.addView(spacer(12));
        hero.addView(progress(78, GOLD));
        content.addView(hero);
        content.addView(spacer(14));

        LinearLayout metrics1 = row();
        metrics1.addView(metricCard("RUNWAY", "14.6 mo", "Healthy buffer", GREEN), weight());
        metrics1.addView(metricCard("REVENUE", "$86k", "18% above plan", GREEN), weight());
        content.addView(metrics1);
        content.addView(spacer(10));
        LinearLayout metrics2 = row();
        metrics2.addView(metricCard("MONTHLY BURN", "$126k", "8% below plan", GREEN), weight());
        metrics2.addView(metricCard("TEAM ENERGY", "82 / 100", "+6 this week", GOLD), weight());
        content.addView(metrics2);

        content.addView(spacer(25));
        content.addView(section("Today's critical path", ExecutiveState.openCount(3, completed) + " open"));
        LinearLayout list = card(SURFACE, 15);
        for (int i = 0; i < priorityIds.length; i++) {
            final String id = priorityIds[i];
            LinearLayout item = row(); item.setPadding(dp(14), dp(13), dp(14), dp(13));
            TextView check = circle(completed.contains(id) ? "✓" : "", completed.contains(id) ? GREEN : BORDER);
            item.addView(check);
            LinearLayout copy = column(); copy.setPadding(dp(11), 0, dp(6), 0);
            copy.addView(text(priorityTitles[i], 14, completed.contains(id) ? MUTED : TEXT, true));
            copy.addView(text(priorityContexts[i], 11, MUTED, false));
            item.addView(copy, new LinearLayout.LayoutParams(0, -2, 1));
            LinearLayout right = column(); right.setGravity(Gravity.RIGHT);
            right.addView(text(priorityDue[i], 10, GOLD, true)); right.addView(text(i == 2 ? "Maya" : "You", 10, MUTED, false));
            item.addView(right);
            item.setOnClickListener(v -> { if (completed.contains(id)) completed.remove(id); else completed.add(id); persist(); showCommand(); });
            list.addView(item); if (i < priorityIds.length - 1) list.addView(divider());
        }
        content.addView(list);
        content.addView(spacer(25));
        content.addView(section("Leadership radar", "View plan"));
        content.addView(initiativeCard("Enterprise wedge", "Turn design partners into repeatable revenue", 72, "ON TRACK", GREEN, "+18% qualified pipeline"));
        content.addView(spacer(10));
        content.addView(initiativeCard("Activation loop", "Make first value obvious in the first session", 54, "AT RISK", AMBER, "41% activation"));
        content.addView(spacer(22));
        content.addView(note("FOUNDER NOTE", "Protect the critical path. Everything else should earn its way onto the calendar."));
    }

    private void showPlan() {
        reset("STRATEGY / Q3 2026", "The plan.");
        LinearLayout objective = card(SURFACE, 18);
        LinearLayout top = row(); LinearLayout copy = column(); copy.addView(eyebrow("COMPANY OBJECTIVE")); copy.addView(spacer(7)); copy.addView(text("Make repeatable revenue feel inevitable.", 20, TEXT, true)); top.addView(copy, new LinearLayout.LayoutParams(0, -2, 1)); top.addView(text("78%", 13, GREEN, true)); objective.addView(top);
        objective.addView(text("Three initiatives. One constraint: focus. Every workstream should move pipeline, activation, or operating leverage.", 12, MUTED, false)); objective.addView(spacer(13)); objective.addView(progress(78, GOLD)); objective.addView(text("12 weeks remaining                                      Sep 30 review", 11, MUTED, false)); content.addView(objective);
        content.addView(spacer(25)); content.addView(section("Strategic initiatives", "Edit plan"));
        content.addView(initiativeCard("Enterprise wedge", "Turn design partners into repeatable revenue", 72, "ON TRACK", GREEN, "+18% qualified pipeline")); content.addView(spacer(10));
        content.addView(initiativeCard("Activation loop", "Make first value obvious in the first session", 54, "AT RISK", AMBER, "41% activation")); content.addView(spacer(10));
        content.addView(initiativeCard("Operating cadence", "One source of truth for the leadership team", 88, "ON TRACK", GREEN, "9.2/10 weekly clarity"));
        content.addView(spacer(25)); content.addView(section("Decision queue", unresolvedCount() + " waiting"));
        String[] titles = {"Hire the senior product designer now?", "Commit to the EU launch in November?", "Move analytics to the new event model?"};
        String[] contexts = {"Would move Q4 roadmap confidence from 62% to 81%.", "Legal review is clear; support coverage is the constraint.", "Two-week migration with a cleaner north-star signal."};
        for (int i = 0; i < titles.length; i++) addDecision("d" + (i + 1), titles[i], contexts[i], "By " + (26 + i * 2) + " Sep");
    }

    private void showTeam() {
        reset("PEOPLE / LEADERSHIP HEALTH", "Your team.");
        LinearLayout metrics = row(); metrics.addView(metricCard("TEAM ENERGY", "82 / 100", "+6 points this week", GREEN), weight()); metrics.addView(metricCard("1:1 RHYTHM", "92%", "On cadence", GOLD), weight()); content.addView(metrics);
        content.addView(spacer(25)); content.addView(section("Leadership team", "Weekly pulse"));
        String[][] people = {{"FC", "You", "Founder & CEO", "Enterprise wedge", "82"}, {"MC", "Maya Chen", "Head of Growth", "Pipeline quality", "91"}, {"AS", "Arjun Shah", "Product Lead", "Activation loop", "68"}, {"NW", "Nora Williams", "Customer Success", "Design partners", "76"}};
        int[] colors = {Color.rgb(221,184,106), Color.rgb(120,207,168), Color.rgb(168,183,240), Color.rgb(230,169,139)};
        for (int i = 0; i < people.length; i++) { LinearLayout person = card(SURFACE, 15); LinearLayout r = row(); r.addView(circle(people[i][0], colors[i])); LinearLayout c = column(); c.setPadding(dp(12), 0, dp(6), 0); c.addView(text(people[i][1], 15, TEXT, true)); c.addView(text(people[i][2], 11, MUTED, false)); c.addView(progress(Integer.parseInt(people[i][4]), i == 2 ? AMBER : GREEN)); r.addView(c, new LinearLayout.LayoutParams(0, -2, 1)); LinearLayout f = column(); f.setGravity(Gravity.RIGHT); f.addView(text("FOCUS", 10, MUTED, false)); f.addView(text(people[i][3], 11, TEXT, true)); r.addView(f); person.addView(r); content.addView(person); content.addView(spacer(10)); }
        content.addView(section("Cadence this week", null)); content.addView(cadence("✓", "Leadership stand-up", "Monday · 25 min · Completed", "Done", GREEN)); content.addView(spacer(10)); content.addView(cadence("→", "Founder / exec review", "Friday · 45 min · Prepare 3 decisions", "Prepare", GOLD));
    }

    private void showFinance() {
        reset("FINANCE / OPERATING MODEL", "The numbers.");
        LinearLayout cash = card(RAISED, 18); cash.addView(eyebrow("CASH POSITION")); cash.addView(text("$1.84M", 36, TEXT, true)); LinearLayout stats = row(); stats.addView(metric("CURRENT RUNWAY", "14.6 mo", GOLD), weight()); stats.addView(metric("RUNWAY FLOOR", "9.0 mo", TEXT), weight()); cash.addView(stats); cash.addView(spacer(12)); cash.addView(progress(72, GOLD)); cash.addView(text("You have room to invest, but not to lose operating discipline.", 11, MUTED, false)); content.addView(cash);
        content.addView(spacer(14)); LinearLayout m = row(); m.addView(metricCard("REVENUE", "$86k", "18% above plan", GREEN), weight()); m.addView(metricCard("MONTHLY BURN", "$126k", "8% below plan", GREEN), weight()); content.addView(m);
        content.addView(spacer(25)); content.addView(section("Operating model", "Edit assumptions")); LinearLayout model = card(SURFACE, 15); model.addView(labeledProgress("Revenue growth", "+18.4%", 68, GREEN)); model.addView(spacer(18)); model.addView(labeledProgress("Gross margin", "74%", 74, GOLD)); model.addView(spacer(18)); model.addView(labeledProgress("Headcount plan", "8 / 10", 80, AMBER)); content.addView(model);
        content.addView(spacer(25)); content.addView(section("Next finance actions", null)); content.addView(actionCard("Refresh the 13-week cash view", "Tie hiring decisions to actual collections, not forecast optimism.")); content.addView(spacer(10)); content.addView(actionCard("Model the EU launch scenario", "Decision required before the Friday executive review."));
    }

    private void showCopilot() {
        reset("FOUNDER COPILOT / PRIVATE", "Think clearly.");
        content.addView(note("OPERATING INTELLIGENCE", "Turn the noise into the next right move.\n\nAsk for synthesis across priorities, people, cash, and strategy. The answer stays inside this workspace.")); content.addView(spacer(14));
        LinearLayout prompt = card(SURFACE, 15); EditText input = new EditText(this); input.setHint("Ask your operating question..."); input.setHintTextColor(MUTED); input.setTextColor(TEXT); input.setTextSize(15); input.setMinLines(3); input.setGravity(Gravity.TOP); input.setPadding(dp(10), dp(7), dp(10), dp(7)); prompt.addView(input, new LinearLayout.LayoutParams(-1, dp(90))); Button ask = button("ASK COPILOT", GOLD); ask.setOnClickListener(v -> { String q = input.getText().toString().toLowerCase(); String answer; if (q.contains("focus") || q.contains("today")) answer = "Your highest-leverage move is close the enterprise design partner. It unlocks the enterprise wedge and keeps the team out of reactive work."; else if (q.contains("decision") || q.contains("block")) answer = unresolvedCount() + " decisions are waiting. Resolve the hiring decision first; it changes roadmap confidence by 19 points."; else if (q.contains("board")) answer = "Board-ready narrative: pipeline quality is improving, activation is the constraint, runway is healthy at 14.6 months, and the next proof point is repeatable enterprise conversion."; else answer = "I can turn this into a priority, decision, board update, or operating note. Try: What should I focus on today?"; showCopilotAnswer(answer); }); prompt.addView(ask, new LinearLayout.LayoutParams(-2, dp(44))); content.addView(prompt); content.addView(spacer(14)); TextView suggestions = text("What should I focus on today?\nTurn this week into a board update\nWhat decisions are blocking the team?", 12, MUTED, false); suggestions.setLineSpacing(dp(3), 1); content.addView(suggestions); content.addView(spacer(20)); content.addView(section("Copilot response", "Save as decision")); content.addView(actionCard("I am your founder-level operating copilot.", "Ask me to prioritize, synthesize, or turn an insight into an action.")); content.addView(spacer(18)); content.addView(section("Guardrails", null)); content.addView(actionCard("You stay in control.", "Copilot drafts and synthesizes. It never commits an external action without your confirmation."));
    }

    private void showCopilotAnswer(String answer) { Toast.makeText(this, answer, Toast.LENGTH_LONG).show(); }

    private void addDecision(String id, String title, String context, String deadline) { LinearLayout d = card(SURFACE, 15); LinearLayout r = row(); r.addView(circle("?", Color.rgb(58,47,27))); LinearLayout c = column(); c.setPadding(dp(12), 0, 0, 0); c.addView(text(title, 14, resolved.contains(id) ? MUTED : TEXT, true)); c.addView(text(context, 11, MUTED, false)); LinearLayout bottom = row(); bottom.setGravity(Gravity.CENTER_VERTICAL); bottom.addView(text(deadline, 10, AMBER, true), new LinearLayout.LayoutParams(0, dp(34), 1)); Button action = button(resolved.contains(id) ? "RESOLVED" : "RESOLVE", resolved.contains(id) ? GREEN : GOLD); action.setOnClickListener(v -> { resolved.add(id); persist(); showPlan(); }); bottom.addView(action); c.addView(bottom); r.addView(c, new LinearLayout.LayoutParams(0, -2, 1)); d.addView(r); content.addView(d); content.addView(spacer(10)); }

    private LinearLayout initiativeCard(String title, String desc, int pct, String status, int statusColor, String metric) { LinearLayout c = card(SURFACE, 15); LinearLayout top = row(); LinearLayout copy = column(); copy.addView(text(title, 16, TEXT, true)); copy.addView(text(desc, 12, MUTED, false)); top.addView(copy, new LinearLayout.LayoutParams(0, -2, 1)); top.addView(text("• " + status, 10, statusColor, true)); c.addView(top); c.addView(spacer(14)); LinearLayout p = row(); p.addView(progress(pct, statusColor), new LinearLayout.LayoutParams(0, dp(7), 1)); p.addView(text("  " + pct + "%", 12, TEXT, true)); c.addView(p); c.addView(spacer(10)); c.addView(text(metric, 11, GREEN, true)); return c; }
    private LinearLayout cadence(String icon, String title, String sub, String action, int color) { LinearLayout c = card(SURFACE, 15); LinearLayout r = row(); r.addView(circle(icon, color)); LinearLayout copy = column(); copy.setPadding(dp(12), 0, 0, 0); copy.addView(text(title, 14, TEXT, true)); copy.addView(text(sub, 11, MUTED, false)); r.addView(copy, new LinearLayout.LayoutParams(0, -2, 1)); r.addView(text(action, 11, color, true)); c.addView(r); return c; }
    private LinearLayout labeledProgress(String label, String value, int pct, int color) { LinearLayout c = column(); LinearLayout r = row(); r.addView(text(label, 13, MUTED, false), new LinearLayout.LayoutParams(0, -2, 1)); r.addView(text(value, 14, TEXT, true)); c.addView(r); c.addView(spacer(8)); c.addView(progress(pct, color)); return c; }
    private LinearLayout actionCard(String title, String sub) { LinearLayout c = card(SURFACE, 15); c.addView(text(title, 14, TEXT, true)); c.addView(text(sub, 11, MUTED, false)); return c; }
    private LinearLayout note(String label, String body) { LinearLayout n = card(Color.rgb(36,31,19), 15); n.addView(text(label, 11, GOLD, true)); n.addView(spacer(8)); n.addView(text(body, 15, TEXT, false)); return n; }

    private void reset(String eyebrow, String title) { content.removeAllViews(); content.addView(eyebrow(eyebrow)); content.addView(text(title, 30, TEXT, true)); content.addView(spacer(22)); }
    private TextView section(String title, String action) { LinearLayout holder = row(); holder.setGravity(Gravity.CENTER_VERTICAL); TextView t = text(title, 17, TEXT, true); holder.addView(t, new LinearLayout.LayoutParams(0, dp(32), 1)); if (action != null) holder.addView(text(action, 11, GOLD, true)); content.addView(holder); return t; }
    private LinearLayout card(int color, int radius) { LinearLayout c = column(); c.setPadding(dp(15), dp(15), dp(15), dp(15)); GradientDrawable bg = new GradientDrawable(); bg.setColor(color); bg.setCornerRadius(dp(radius)); bg.setStroke(dp(1), BORDER); c.setBackground(bg); return c; }
    private Button button(String label, int color) { Button b = new Button(this); b.setText(label); b.setTextSize(10); b.setTextColor(BG); b.setTypeface(Typeface.DEFAULT, Typeface.BOLD); b.setAllCaps(false); b.setPadding(dp(12), 0, dp(12), 0); GradientDrawable bg = new GradientDrawable(); bg.setColor(color); bg.setCornerRadius(dp(22)); b.setBackground(bg); return b; }
    private TextView circle(String label, int color) { TextView t = text(label, 11, BG, true); t.setGravity(Gravity.CENTER); GradientDrawable bg = new GradientDrawable(); bg.setColor(color); bg.setShape(GradientDrawable.OVAL); t.setBackground(bg); t.setLayoutParams(new LinearLayout.LayoutParams(dp(32), dp(32))); return t; }
    private LinearLayout metric(String label, String value, int color) { LinearLayout c = column(); c.addView(text(label, 10, MUTED, false)); c.addView(text(value, 16, color, true)); return c; }
    private LinearLayout metricCard(String label, String value, String delta, int color) { LinearLayout c = card(SURFACE, 14); c.addView(text(label, 10, MUTED, false)); c.addView(text(value, 22, TEXT, true)); c.addView(text(delta, 11, color, false)); return c; }
    private TextView progress(int pct, int color) { TextView p = new TextView(this); p.setText(""); p.setBackgroundColor(BORDER); p.setMinimumHeight(dp(7)); p.setLayoutParams(new LinearLayout.LayoutParams(-1, dp(7))); return p; }
    private TextView divider() { TextView d = new TextView(this); d.setBackgroundColor(BORDER); d.setLayoutParams(new LinearLayout.LayoutParams(-1, dp(1))); return d; }
    private TextView eyebrow(String s) { return text(s, 11, MUTED, true); }
    private TextView text(String s, float size, int color, boolean bold) { TextView t = new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(color); t.setTypeface(Typeface.DEFAULT, bold ? Typeface.BOLD : Typeface.NORMAL); t.setPadding(0, dp(2), 0, dp(2)); return t; }
    private LinearLayout row() { LinearLayout l = new LinearLayout(this); l.setOrientation(LinearLayout.HORIZONTAL); return l; }
    private LinearLayout column() { LinearLayout l = new LinearLayout(this); l.setOrientation(LinearLayout.VERTICAL); return l; }
    private TextView spacer(int dp) { TextView s = new TextView(this); s.setHeight(dp(dp)); return s; }
    private LinearLayout.LayoutParams weight() { return new LinearLayout.LayoutParams(0, -2, 1); }
    private int unresolvedCount() { return 3 - resolved.size(); }
    private void persist() { prefs.edit().putStringSet("completed", new HashSet<>(completed)).putStringSet("resolved", new HashSet<>(resolved)).apply(); }
    private int dp(int value) { return Math.round(value * getResources().getDisplayMetrics().density); }
}
