//! NOTE ZA JS: Ako jedna funkcija ne radi, onda funkcija posle nje takođe nece raditi

//*********** SET CURRENT YEAR *****************/
const yearEl = document.querySelector(".year");
const currentYear = new Date().getFullYear();
yearEl.textContent = currentYear;
//*********************************************/

//* STICKY NAV FOR ALL PAGES EXCEPT INDEX PAGE
// window.addEventListener("scroll", function () {
//   // document.body.classList.toggle("sticky", window.scrollY > 96);
//   document.body.classList.toggle("sticky", window.scrollY > 0);
// });
//*********************************************/

//*********** DROP DOWN MENU *****************/
let profileImgEl = document.querySelector(".profile-img");
const arrow = document.querySelector(".arrow");
let subMenu = document.getElementById("subMenu");

profileImgEl.addEventListener("click", function () {
  subMenu.classList.toggle("open-menu");
  arrow.classList.toggle("open");
});
//*********************************************/

//*********** LOG OUT POP UP *****************/

function showLogoutModal() {
    // Prevent the default action to avoid logging out immediately
    event.preventDefault();
    document.getElementById("logoutModal").style.display = "block";
}

function closeLogoutModal() {
    document.getElementById("logoutModal").style.display = "none";
}

function proceedToLogout() {
    // Only proceed to logout when "Yes" is clicked
    window.location.href = "/CineScape/auth/custom-logout";
}

// Close the modal when clicking outside of it
window.onclick = function(event) {
    const modal = document.getElementById("logoutModal");
    if (event.target == modal) {
        closeLogoutModal();
    }
}
//*********************************************/

//*********** DELETE USER/MOVIE POP UP *****************/

function showDeleteUserModal() {
    event.preventDefault();
    document.getElementById("deleteUserModal").style.display = "block";
}

function closeDeleteUserModal() {
    document.getElementById("deleteUserModal").style.display = "none";
}

function proceedToDeleteUser() {
    document.getElementById("deleteUserForm").submit();
}

function showDeleteMovieModal() {
    event.preventDefault();
    document.getElementById("deleteMovieModal").style.display = "block";
}

function closeDeleteMovieModal() {
    document.getElementById("deleteMovieModal").style.display = "none";
}

function proceedToDeleteMovie() {
    document.getElementById("deleteMovieForm").submit();
}

// Close the modal when clicking outside of it
window.onclick = function(event) {
    if (event.target.classList.contains('modal')) {
        closeDeleteUserModal();
        closeDeleteMovieModal();
    }
}

//*********************************************/
