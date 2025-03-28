//*********** SET CURRENT YEAR *****************/
const yearEl = document.querySelector(".year");
const currentYear = new Date().getFullYear();
yearEl.textContent = currentYear;
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
    event.preventDefault();
    document.getElementById("logoutModal").style.display = "block";
}

function closeLogoutModal() {
    document.getElementById("logoutModal").style.display = "none";
}

function proceedToLogout() {
    window.location.href = "/CineScape/auth/custom-logout";
}

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

window.onclick = function(event) {
    if (event.target.classList.contains('modal')) {
        closeDeleteUserModal();
        closeDeleteMovieModal();
    }
}

//*********************************************/
