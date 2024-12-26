document.getElementById('task-form').addEventListener('submit', function (e) {
    e.preventDefault(); // Impede o comportamento padrão do formulário.

    // Obter os valores dos campos.
    const title = document.getElementById('title').value.trim();
    const description = document.getElementById('description').value.trim();
    const dueDate = document.getElementById('due-date').value;

    // Verificar se todos os campos foram preenchidos.
    if (!title || !description || !dueDate) {
        alert('Todos os campos são obrigatórios!');
        return;
    }

    // Simular o salvamento da tarefa (aqui você pode integrar com um back-end, se necessário).
    // Para esta etapa, apenas armazenamos os dados no `localStorage`.
    const tasks = JSON.parse(localStorage.getItem('tasks')) || [];
    tasks.push({ title, description, dueDate });
    localStorage.setItem('tasks', JSON.stringify(tasks));

    // Redirecionar para a página de tarefas com mensagem de sucesso.
    window.location.href = 'tasks.html?success=true';
});
