function formatDate(dateString) {
    const options = {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
    };
    const date = new Date(dateString);
    return date.toLocaleDateString('en-GB', options).replace(/\//g, '.') + '.';
}

document.addEventListener('DOMContentLoaded', (event) => {
    const dateElements = document.querySelectorAll('.comment-date');
    dateElements.forEach(element => {
        const dateStr = element.getAttribute('data-date');
        element.textContent = formatDate(dateStr);
    });
});
