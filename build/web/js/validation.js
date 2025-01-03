document.getElementById('task-form')?.addEventListener('submit', function (e) {
    const title = document.getElementById('title').value.trim();
    const dueDate = document.getElementById('due-date').value;

    if (!title || !dueDate) {
        e.preventDefault();
        alert('Todos os campos são obrigatórios!');
    }
});
