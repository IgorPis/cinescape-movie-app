//*********** SHOWING SEARCH SUGGESTIONS FOR MOVIES *************************/

function showSuggestions() {
    let input = document.getElementById('movieSearch').value.trim();
    let suggestions = document.getElementById('suggestions');
    suggestions.innerHTML = '';

    if (input.length === 0) {
        suggestions.style.display = 'none';
        return;
    }

    fetch(`/CineScape/home/searchMovies?query=${input}`)
        .then(response => response.json())
        .then(data => {
            if (data.length > 0) {
                suggestions.style.display = 'block'; 
                data.forEach(movie => {
                    let div = document.createElement('div');
                    div.classList.add('suggestion-item');
                    div.innerHTML = movie;
                    div.onclick = function() {
                        document.getElementById('movieSearch').value = movie;
                        suggestions.innerHTML = '';
                        suggestions.style.display = 'none';
                    };
                    suggestions.appendChild(div);
                });
            } else {
                suggestions.style.display = 'none';
            }
        });
}
//*********************************************/


//DECLARING INPUT AND SUGGESTION CONSTANTS
const input = document.getElementById('movieSearch');
const suggestions = document.getElementById('suggestions');

//////////////////////////////////////////////////////////
// HIDING PLACEHOLDER IN SEARCHBAR AND SHOWING IT ON FOCUS

input.addEventListener('focus', () => {
    input.classList.add('focused');
});

input.addEventListener('blur', () => {
    input.classList.remove('focused');
});

//////////////////////////////////////////////////////////////
// KEEPING THE SEARCHBAR EXPANDED WHILE CLICKING ON SUGGESTION

let hasText = false;
let isFocused = false;

function expandInput() {
    input.classList.add('expanded');
}

function collapseInput() {
    setTimeout(() => {
        if (!isFocused && !hasText) {
            input.classList.remove('expanded');
        }
    }, 100);
}

input.addEventListener('focus', () => {
    isFocused = true;
    expandInput();
});

input.addEventListener('input', () => {
    hasText = input.value.trim() !== '';
});

suggestions.addEventListener('mousedown', (event) => {
    event.preventDefault(); 
    expandInput(); 
    input.value = event.target.innerText; 
    suggestions.innerHTML = ''; 
    hasText = true; 
});

document.addEventListener('click', (event) => {
    if (!input.contains(event.target) && !suggestions.contains(event.target)) {
        isFocused = false;
        collapseInput();
    }
});

input.addEventListener('blur', () => {
    isFocused = false;
    collapseInput();
});





