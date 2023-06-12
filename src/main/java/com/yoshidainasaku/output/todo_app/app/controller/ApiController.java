package com.yoshidainasaku.output.todo_app.app.controller;

import com.yoshidainasaku.output.todo_app.domain.repository.TaskItem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ApiController {
    @GetMapping("/api/v1/tasks")
    public ArrayList<TaskItem> showTaskList() {
        // ToDO: DBからデータを取得してJsonでデータを返す
        ArrayList<TaskItem> taskItems = new ArrayList<>();
        taskItems.add(new TaskItem("1", "歯医者に連絡をする", "2023-08-12", false));

        // 試しにVueに文字列を格納した変数を渡す
        return taskItems;
    }
}
