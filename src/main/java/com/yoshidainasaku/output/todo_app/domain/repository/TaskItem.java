package com.yoshidainasaku.output.todo_app.domain.repository;

public record TaskItem(String id, String task, String deadline, boolean done) {}
