package service;

import dao.TaskDAO;
import model.Task;
import java.sql.SQLException;
import java.util.List;

public class TaskService {
    private TaskDAO taskDAO;

    public TaskService() throws SQLException {
        this.taskDAO = new TaskDAO();
    }

    public void createTask(Task task) throws SQLException {
        taskDAO.createTask(task);
    }

    public List<Task> getAllTasks() throws SQLException {
        return taskDAO.getAllTasks();
    }

    public void updateTask(Task task) throws SQLException {
        taskDAO.updateTask(task);
    }

    public void deleteTask(int id) throws SQLException {
        taskDAO.deleteTask(id);
    }
}