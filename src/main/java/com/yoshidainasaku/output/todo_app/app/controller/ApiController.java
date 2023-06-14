package com.yoshidainasaku.output.todo_app.app.controller;

import com.yoshidainasaku.output.todo_app.domain.repository.TaskItem;
import com.yoshidainasaku.output.todo_app.domain.service.TaskItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class ApiController {
    private final TaskItemDao taskItemDao;

    @Autowired
    public ApiController(TaskItemDao taskItemDao) {
        this.taskItemDao = taskItemDao;
    }

    @GetMapping("/api/v1/tasks")
    public List<TaskItem> showTaskList() {
        return taskItemDao.getAllTaskItems();
    }

    @PostMapping("/api/v1/add-task")
    public void addTaskItem(@RequestParam("task") String task,
                                @RequestParam("deadline") String deadline) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        TaskItem taskItem = new TaskItem(id, task, deadline, false);
        taskItemDao.addTaskItem(taskItem);
    }

    @PostMapping("/api/v1/update-task")
    public void updateTaskItem(@RequestParam("id") String id,
                               @RequestParam("task") String task,
                               @RequestParam("deadline") String deadline,
                               @RequestParam("done") boolean done) {
        taskItemDao.updateTaskItem(
                new TaskItem(id, task, deadline, done));
    }

    @PostMapping("/api/v1/delete-task")
    public void deleteTaskItem(@RequestParam("id") String id) {
        taskItemDao.deleteTaskItem(id);
    }
}
