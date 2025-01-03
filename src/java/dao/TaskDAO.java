package dao;

import model.Task;
import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    private Connection connection;

    public TaskDAO() throws SQLException {
        this.connection = DBConnection.getConnection();
    }

    public boolean createTask(Task task) {
        String sql = "INSERT INTO tasks (title, description, due_date) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setDate(3, java.sql.Date.valueOf(task.getDueDate()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try (PreparedStatement stmt = connection.prepareStatement(sql); 
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setTitle(rs.getString("title"));
                task.setDescription(rs.getString("description"));

                // Converter java.sql.Date para java.time.LocalDate
                java.sql.Date dueDate = rs.getDate("due_date"); // Nome completo da classe
                if (dueDate != null) {
                    task.setDueDate(dueDate.toLocalDate());
                }

                // Converter java.sql.Timestamp para java.time.LocalDateTime
                java.sql.Timestamp createdAt = rs.getTimestamp("created_at");
                if (createdAt != null) {
                    task.setCreatedAt(createdAt.toLocalDateTime());
                }

                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public boolean deleteTask(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTask(Task task) {
        String sql = "UPDATE tasks SET title = ?, description = ?, due_date = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setDate(3, java.sql.Date.valueOf(task.getDueDate()));
            stmt.setInt(4, task.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
