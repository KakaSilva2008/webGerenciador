document.querySelector("#saveTaskForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Previne o envio padrão do formulário

    const title = document.querySelector("input[name='title']").value.trim();
    const description = document.querySelector("textarea[name='description']").value.trim();
    const dueDate = document.querySelector("input[name='due_date']").value;

    if (!title || !description || !dueDate) {
        alert('Todos os campos são obrigatórios!');
        return;
    }

    // Requisição ao backend
    fetch('http://localhost:8080/webGerenciador/create-task', { 
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
            window.location.reload(); // Recarrega a página para atualizar a lista
        } else {
            alert("Erro ao salvar a tarefa. Tente novamente.");
        }
    })
    .catch(error => console.error("Erro na requisição:", error));
});

