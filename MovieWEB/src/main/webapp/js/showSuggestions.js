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

// Add the 'focused' class when the input is focused
input.addEventListener('focus', () => {
    input.classList.add('focused');
});

// Remove the 'focused' class when the input loses focus
input.addEventListener('blur', () => {
    input.classList.remove('focused');
});

//////////////////////////////////////////////////////////////
// KEEPING THE SEARCHBAR EXPANDED WHILE CLICKING ON SUGGESTION

// Variables to track whether the input has text and whether it is focused
let hasText = false;
let isFocused = false;

// Function to expand the input
function expandInput() {
    input.classList.add('expanded');
}

// Function to collapse the input
function collapseInput() {
    // Delay collapsing to ensure clicks on suggestions are registered
    setTimeout(() => {
        // Only collapse if no text and not focused
        if (!isFocused && !hasText) {
            input.classList.remove('expanded');
        }
    }, 100); // reduced delay from 200ms to 100ms, cuz it took too long to collapse
}

// Expand the input when it is focused
input.addEventListener('focus', () => {
    isFocused = true;
    expandInput();
});

// Update the hasText flag based on input content
input.addEventListener('input', () => {
    hasText = input.value.trim() !== '';
});

// Keep the input expanded when clicking on suggestions
suggestions.addEventListener('mousedown', (event) => {
    event.preventDefault(); // Prevent losing focus on mousedown
    expandInput(); // Ensure it stays expanded
    input.value = event.target.innerText; // Set the input value to the clicked suggestion
    suggestions.innerHTML = ''; // Clear suggestions if needed
    hasText = true; // Set flag to true when selecting a suggestion
});

// Collapse the input when clicking outside of it or suggestions
document.addEventListener('click', (event) => {
    if (!input.contains(event.target) && !suggestions.contains(event.target)) {
        isFocused = false;
        collapseInput();
    }
});

// Handle blur event to update focus status
input.addEventListener('blur', () => {
    isFocused = false;
    collapseInput();
});





