package com.example.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@SpringBootApplication
public class TaskManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }
}

@RestController
@RequestMapping("/tasks")
class TaskController {
    private List<Task> taskList = new ArrayList<>();
    private int idCounter = 1;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskList;
    }

    @PostMapping
    public Task createTask(@RequestBody Map<String, String> payload) {
        Task t = new Task();
        t.id = idCounter++;
        t.title = payload.get("title");
        t.description = payload.get("description");
        t.status = "pending";
        t.creationDate = new Date();
        t.lastUpdated = new Date();
        taskList.add(t);
        return t;
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable int id, @RequestBody Map<String, String> payload) {
        for (Task t : taskList) {
            if (t.id == id) {
                t.title = payload.get("title") != null ? payload.get("title") : t.title;
                t.description = payload.get("description") != null ? payload.get("description") : t.description;
                t.status = payload.get("status") != null ? payload.get("status") : t.status;
                t.lastUpdated = new Date();
                return t;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable int id) {
        Iterator<Task> it = taskList.iterator();
        while (it.hasNext()) {
            Task t = it.next();
            if (t.id == id) {
                it.remove();
                return "Deleted";
            }
        }
        return "Not found";
    }

    @GetMapping("/search")
    public List<Task> search(@RequestParam String query) {
        List<Task> result = new ArrayList<>();
        for (Task t : taskList) {
            if (t.title.contains(query) || t.description.contains(query)) {
                result.add(t);
            }
        }
        return result;
    }

    @GetMapping("/find")
    public List<Task> find(@RequestParam String q) {
        List<Task> r = new ArrayList<>();
        for (Task t : taskList) {
            if (t.title.contains(q) || t.description.contains(q)) {
                r.add(t);
            }
        }
        return r;
    }

    @PostMapping("/bulk")
    public List<Task> createBulkTasks(@RequestBody List<Map<String, String>> payloads) {
        List<Task> created = new ArrayList<>();
        for (Map<String, String> payload : payloads) {
            Task t = new Task();
            t.id = idCounter++;
            t.title = payload.get("title");
            t.description = payload.get("description");
            t.status = "pending";
            t.creationDate = new Date();
            t.lastUpdated = new Date();
            taskList.add(t);
            created.add(t);
        }
        return created;
    }
}

class Task {
    public int id;
    public String title;
    public String description;
    public String status;
    public Date creationDate;
    public Date lastUpdated;

    public Task() {}

    public Task(int id, String title, String description, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.creationDate = new Date();
        this.lastUpdated = new Date();
    }

    public String summary() {
        return id + ": " + title + " (" + status + ")";
    }
}  
