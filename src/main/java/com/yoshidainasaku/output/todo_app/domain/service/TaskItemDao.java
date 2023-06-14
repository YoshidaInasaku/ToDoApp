package com.yoshidainasaku.output.todo_app.domain.service;

import com.yoshidainasaku.output.todo_app.domain.repository.TaskItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskItemDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TaskItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // タスク一覧を取得する
    public List<TaskItem> getAllTaskItems() {
        String query = "SELECT * FROM tasklist;";
        return jdbcTemplate.query(query, new DataClassRowMapper<>(TaskItem.class));
    }

    // タスクを登録する
    public void addTaskItem(TaskItem taskItem) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("tasklist");
        BeanPropertySqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(taskItem);
        simpleJdbcInsert.execute(beanPropertySqlParameterSource);
    }

    // タスクを削除する
    public void deleteTaskItem(String id) {
        String query = "DELETE FROM tasklist WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    // タスクを更新する
    public void updateTaskItem(TaskItem taskItem) {
        String query = """
                UPDATE tasklist
                    SET task = ?,
                        deadline = ?,
                        done = ?
                    WHERE
                        id = ?
                """;
        jdbcTemplate.update(query,
                taskItem.task(),
                taskItem.deadline(),
                taskItem.done(),
                taskItem.id());
    }
}
