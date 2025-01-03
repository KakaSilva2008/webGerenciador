package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.TaskDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDate;
import java.util.List;
import model.Task;
import java.sql.SQLException;
import java.time.LocalDateTime;
import util.GsonAdapters;


@WebServlet("/tasks")
public class ServletTask extends HttpServlet {

    private TaskDAO taskDao;

    @Override
    public void init() {
        try {

            taskDao = new TaskDAO();

        } catch (SQLException ex) {
            Logger.getLogger(ServletTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletTask</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServletTask at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            System.out.println("Iniciando a busca de tarefas...");

            // Obter todas as tarefas do DAO
            var tasks = taskDao.getAllTasks();
            System.out.println("Tarefas obtidas: " + tasks.size());

            // Configurar Gson com adaptadores
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new GsonAdapters.LocalDateAdapter())
                    .registerTypeAdapter(LocalDateTime.class, new GsonAdapters.LocalDateTimeAdapter())
                    .create();

            // Converter tarefas para JSON
            String json = gson.toJson(tasks);

            // Escrever o JSON na resposta
            response.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Erro ao buscar tarefas.\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
        // Obter os dados enviados pelo frontend

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String dueDateStr = request.getParameter("dueDate");

        System.out.println("Data: " + request.getParameter("due_date"));
        // Validação dos parâmetros
        if (title == null || description == null || dueDateStr == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Parâmetros inválidos!");
            return;
        }

        try {
            // Conversão da data
            LocalDate dueDate = LocalDate.parse(dueDateStr);

            // Criar a tarefa
            Task task = new Task();
            task.setTitle(title);
            task.setDescription(description);
            task.setDueDate(dueDate);

            // Salvar no banco de dados usando TaskDao
            TaskDAO taskDao = new TaskDAO();
            taskDao.createTask(task);

            // Resposta de sucesso
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("Tarefa criada com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erro ao criar a tarefa.");
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");

        if (idParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("ID da tarefa é obrigatório para exclusão.");
            return;
        }

        int id = Integer.parseInt(idParam);
        boolean success = taskDao.deleteTask(id);

        if (success) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Tarefa não encontrada.");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        Task updatedTask = gson.fromJson(reader, Task.class);

        boolean success = taskDao.updateTask(updatedTask);

        if (success) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Falha ao atualizar a tarefa.");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
