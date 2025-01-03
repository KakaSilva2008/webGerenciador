
document.getElementById('task-form').addEventListener('submit', function (e) {
    e.preventDefault(); // Previne o envio padrão do formulário

    const title = document.getElementById('title').value.trim();
    const description = document.getElementById('description').value.trim();
    const dueDate = document.getElementById('duedate').value;

    if (!title || !description || !dueDate) {
        alert('Todos os campos são obrigatórios!');
        return;
    }

    // Enviar a tarefa ao backend
    fetch('/webGerenciador/tasks', { 
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({
            title: title,
            description: description,
            due_date: dueDate
        })
    })
    .then(response => {
        if (response.ok) {
            alert("Tarefa salva com sucesso!");
            window.location.href = 'tasks.html'; // Redireciona para a lista de tarefas
        } else {
            alert("Erro ao salvar a tarefa. Tente novamente.");
        }
    })
    .catch(error => console.error("Erro na requisição:", error));
});
