<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <meta
      name="description"
      content="CineScape is an streaming service for quick and easy movie watching!"
    />

    <link rel="icon" href="${pageContext.request.contextPath}/images/favicon-64.png" />
    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/images/apple-touch-icon.png" />
    <link rel="manifest" href="${pageContext.request.contextPath}/manifest.webmanifest" />

    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap"
      rel="stylesheet"
    />

    <link
      rel="stylesheet"
      href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"
    />

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/general.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/movie-details.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/queries.css" />

	<script defer type="text/javascript" src="${pageContext.request.contextPath}/js/showSuggestions.js"></script>
    <script defer type="text/javascript" src="${pageContext.request.contextPath}/js/dateFormat.js"></script>
    <script defer type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>


    <title>Movie Details for: ${movieById.title}</title>
  </head>
  <body>
    <header class="header">
      <a href="/CineScape/index.jsp">
        <img
          class="logo"
          alt="CineScape logo"
          src="${pageContext.request.contextPath}/images/cinescape-logo.png"
        />
      </a>

      <nav class="main-nav">
        <ul class="main-nav-list">
          <li>
            <form class="search-form" action="/CineScape/home/getByTitle" method="get">
              <input
                class="input-search"
                type="text"
                name="title"
                placeholder="Search by title.."
                id="movieSearch"
                onkeyup="showSuggestions()"
                required
              />
              <div id="suggestions" class="suggestions"></div>
              <button class="btn-search" type="submit">
                <ion-icon class="icon" name="search"></ion-icon>
              </button>
            </form>
          </li>
          <security:authorize access="isAuthenticated()">
           <c:choose>
                <c:when test="${not empty loggedUser and loggedUser.roleDisplayName == 'ADMIN'}">
                    <li class="li-add-movie">
                        <a class="main-nav-link" href="/CineScape/admin/getGenreLang">
                          <div class="add-mov-box">
                            <ion-icon class="add-movie-btn" name="add-circle"></ion-icon>
                            <p>Add Movie</p>
                          </div>
                        </a>
                    </li>
                    <li>
                        <a class="main-nav-link" href="/CineScape/admin/getAllUsers">All Users</a>
                    </li>
                </c:when>
            </c:choose>
          </security:authorize>
          <li><a class="main-nav-link" href="/CineScape/home/getAllMovies">Movies</a></li>
          <security:authorize access="isAuthenticated()">
          <li><a class="main-nav-link" href="/CineScape/my-list/showFavorites">My List</a></li>
          </security:authorize>
          <li>
            <a class="main-nav-link" href="/CineScape/home/getLangs"
              >Browse by Languages</a
            >
          </li>
        <c:choose>
            <c:when test="${not empty loggedUser}">
                <!-- User is logged in, show profile and hide sign-up link -->
                <li class="li-sign-up" style="display: none;">
                    <a class="main-nav-link nav-sign-up" href="/CineScape/auth/getAllRoles">Sign up</a>
                </li>
                <li class="li-profile">
                    <div class="account-dropdown-button">
                        <img
                          class="profile-img"
                          src="${loggedUser.imagePath}"
                          alt="Profile img"
                          onclick="toggleMenu()"
                        />
                        <span class="arrow"></span>

                        <div class="sub-menu-wrap" id="subMenu">
                            <div class="sub-menu">
                                <div class="user-info">
                                    <img
                                      src="${loggedUser.imagePath}"
                                      height="42px"
                                      width="42px"
                                    />
                                    <h3>${loggedUser.firstName} ${loggedUser.lastName}</h3>
                                </div>
                                <hr />

                                <a href="/CineScape/user-profile-user.jsp" class="sub-menu-link">
                                    <span class="sub-menu-link-icon material-symbols-outlined">
                                        person
                                    </span>
                                    <p>My Profile</p>
                                    <span class="arrow-span">&gt;</span>
                                </a>
                                <a href="/CineScape/edit-profile-user.jsp" class="sub-menu-link">
                                    <span class="sub-menu-link-icon material-symbols-outlined">
                                        edit
                                    </span>
                                    <p>Edit Profile</p>
                                    <span class="arrow-span">&gt;</span>
                                </a>
                                <a href="javascript:void(0);" class="sub-menu-link" onclick="showLogoutModal()">
                                    <span class="sub-menu-link-icon material-symbols-outlined">
                                        logout
                                    </span>
                                    <p>Log out</p>
                                    <span class="arrow-span">&gt;</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </li>
            </c:when>
            <c:otherwise>
                <!-- User is not logged in, show sign-up link and hide profile -->
                <li class="li-sign-up">
                    <a class="main-nav-link nav-sign-up" href="/CineScape/auth/getAllRoles">Sign up</a>
                </li>
                <li class="li-profile" style="display: none;">
                    <div class="account-dropdown-button">
                        <!-- Profile info is hidden when not logged in -->
                    </div>
                </li>
            </c:otherwise>
        </c:choose>
        </ul>
      </nav>
    </header>


    <main>
      <section class="section-movie" id="cta">
        <div class="movie-container">
          <div class="title-heading-box">
            <h2 class="title-heading">${movieById.title}</h2>
            <c:if test="${userRole == 'ADMIN'}">
	            <div class="btns-box">
	              <a href="/CineScape/admin/editMoviePage/${movieById.idMovie}" class="btn-box">
	                <span class="edit-icon material-symbols-outlined"> edit </span>
	                <p>Edit Movie</p>
	              </a>
	              <form id="deleteMovieForm" action="/CineScape/admin/deleteMovie/${movieById.idMovie}" method="post">
	                <button type="submit" class="del-btn" onclick="showDeleteMovieModal()">
	                  <span class="edit-icon material-symbols-outlined">
	                    delete
	                  </span>
	                  <p>Delete Movie</p>
	                </button>
	              </form>
	            </div>
            </c:if>
          </div>
          <div class="parent">
            <div class="movie-poster-details">
              <div class="movie-poster">
                <img src="${movieById.posterPath}" alt="Movie poster image" />
              </div>
              <div class="movie-details">
                <ul class="list">
                  <li class="list-item">
                    <ion-icon
                      class="list-icon"
                      name="hourglass-outline"
                    ></ion-icon>
                    <span>${duration}</span>
                  </li>
                  <li class="list-item">
                    <ion-icon
                      class="list-icon"
                      name="calendar-outline"
                    ></ion-icon>
                    <span>${movieById.releaseYear}</span>
                  </li>
                  <li class="list-item">
                    <ion-icon class="list-icon" name="eye-outline"></ion-icon>
                    <span>${movieById.views} views</span>
                  </li>
                  <li class="list-item">
                    <ion-icon
                      class="list-icon"
                      name="videocam-outline"
                    ></ion-icon>
                    <span>${movieById.genre.name}</span>
                  </li>
                  <li class="list-item">
                    <ion-icon
                      class="list-icon"
                      name="language-outline"
                    ></ion-icon>
                    <span>${movieById.language.name}</span>
                  </li>
                  <c:if test="${!isFavorite}">
	                  <li class="list-item">
	                    <form
	                      class="bookmark-form"
	                      action="/CineScape/my-list/addToFavorites/${movieById.idMovie}"
	                      method="post"
	                    >
	                      <button class="fav-btn">
	                        <span
	                          class="bookmark-icon list-icon material-symbols-outlined"
	                        >
	                          bookmark
	                        </span>
	                        <span class="btn--text">Save</span>
	                      </button>
	                    </form>
	                  </li>
                  </c:if>
                  <c:if test="${isFavorite}">
	                  <li class="list-item">
	                    <form
	                      class="bookmark-form"
	                      action="/CineScape/my-list/removeFromFavorites/${movieById.idMovie}"
	                      method="post"
	                    >
	                      <button type="submit" class="fav-btn">
	                        <span
	                          class="bookmark-icon list-icon material-symbols-outlined"
	                        >
	                          bookmark_added
	                        </span>
	                        <span class="btn--text">Remove</span>
	                      </button>
	                    </form>
	                  </li>
                  </c:if>
                </ul>
              </div>
            </div>
            <div class="movie-trailer">
              <iframe
                width="560"
                height="315"
                src="https://www.youtube.com/embed/${movieById.trailerKey}?si=Wq6MawXWAmhoKva0"
                title="YouTube video player"
                frameborder="0"
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
                referrerpolicy="strict-origin-when-cross-origin"
                allowfullscreen
              ></iframe>
            </div>
          </div>
          <div class="parent2">
            <div class="description">
              <p>
				${movieById.description}
              </p>
            </div>
          </div>
          <c:if test="${!empty msgComment}">
          	<p class="zero-comment-msg">${msgComment}</p>
          </c:if>
          <c:if test="${!empty comments}">
          	<p class="zero-comment-msg">Comments:</p>
          </c:if>
          <div class="parent3">
            <div class="comments-box">
			<c:if test="${!empty comments}">
			    <c:forEach items="${comments}" var="c" varStatus="status">
			        <div class="one-comment-box">
			            <div class="img-container-sm">
			                <img src="${c.appUser.imagePath}" height="40px" width="40px" alt="User's profile image"/>
			            </div>
			            <div class="comment-info">
			                <div class="user-box">
			                    <div class="comment-username">@${c.appUser.username}</div>
			                    <div class="delete-comment-info">
			                        <c:if test="${loggedUser.getRoleDisplayName() == 'ADMIN' || loggedUser.getIdUser() == c.appUser.idUser}">
			                            <form action="/CineScape/user/deleteComment/${movieById.idMovie}" method="post">
			                                <input type="hidden" name="idComment" value="${c.idComment}"/>
			                                <button class="delete-comment-btn" type="submit">Delete</button>
			                            </form>
			                        </c:if>
			                        <div class="comment-date" data-date="${c.formattedDate}"></div>
			                    </div>
			                </div>
			                <div class="content-box">
			                    ${c.text}
			                </div>
			            </div>
			        </div>
			    </c:forEach>
			</c:if>
            </div>
            <div class="form-comment-box">
              <form
                class="form-comment"
                action="/CineScape/user/saveComment/${movieById.idMovie}"
                method="post"
              >
                <textarea
                  rows="7"
                  placeholder="Add a comment..."
                  name="text"
                  required
                ></textarea>
                <button class="comment-btn" type="submit">Comment</button>
              </form>
            </div>
          </div>
          <div class="parent4">
            <h2 class="secondary-heading">Movies of the same genre:</h2>
            <c:if test="${!empty relatedMovies}">
             <div class="movie-cards">
              <c:forEach items="${relatedMovies}" var="m">
               <div class="movie-card">
                <a class="movie-details-link" data-movie-id="${m.idMovie}" href="/CineScape/user/getMovieDetails?idMovie=${m.idMovie}">
                  <img
                    src="${m.posterPath}"
                    class="movie-img"
                    alt="Movie poster image"
                  />
                </a>
               </div>
              </c:forEach>
             </div>
            </c:if>
          </div>
        </div>
      </section>
    </main>

    <footer class="footer">
      <div class="container grid grid--footer">
        <div class="logo-col">
          <a href="#" class="footer-logo">
            <img
              class="logo"
              alt="CineScape logo"
              src="${pageContext.request.contextPath}/images/cinescape-logo.png"
            />
          </a>

          <ul class="social-links">
            <li>
              <a data-social="Twitter" style="--accent-color: black" href="#">
                <svg
                  role="img"
                  viewBox="0 0 24 24"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <title>X</title>
                  <path
                    d="M18.901 1.153h3.68l-8.04 9.19L24 22.846h-7.406l-5.8-7.584-6.638 7.584H.474l8.6-9.83L0 1.154h7.594l5.243 6.932ZM17.61 20.644h2.039L6.486 3.24H4.298Z"
                  />
                </svg>
              </a>
            </li>
            <li>
              <a
                data-social="Facebook"
                style="--accent-color: #106bff"
                href="#"
              >
                <svg
                  role="img"
                  viewBox="0 0 24 24"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <title>Facebook</title>
                  <path
                    d="M9.101 23.691v-7.98H6.627v-3.667h2.474v-1.58c0-4.085 1.848-5.978 5.858-5.978.401 0 .955.042 1.468.103a8.68 8.68 0 0 1 1.141.195v3.325a8.623 8.623 0 0 0-.653-.036 26.805 26.805 0 0 0-.733-.009c-.707 0-1.259.096-1.675.309a1.686 1.686 0 0 0-.679.622c-.258.42-.374.995-.374 1.752v1.297h3.919l-.386 2.103-.287 1.564h-3.246v8.245C19.396 23.238 24 18.179 24 12.044c0-6.627-5.373-12-12-12s-12 5.373-12 12c0 5.628 3.874 10.35 9.101 11.647Z"
                  />
                </svg>
              </a>
            </li>
            <li>
              <a
                data-social="Instagram"
                style="--accent-color: #fe107c"
                href="#"
              >
                <svg
                  role="img"
                  viewBox="0 0 24 24"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <title>Instagram</title>
                  <path
                    d="M7.0301.084c-1.2768.0602-2.1487.264-2.911.5634-.7888.3075-1.4575.72-2.1228 1.3877-.6652.6677-1.075 1.3368-1.3802 2.127-.2954.7638-.4956 1.6365-.552 2.914-.0564 1.2775-.0689 1.6882-.0626 4.947.0062 3.2586.0206 3.6671.0825 4.9473.061 1.2765.264 2.1482.5635 2.9107.308.7889.72 1.4573 1.388 2.1228.6679.6655 1.3365 1.0743 2.1285 1.38.7632.295 1.6361.4961 2.9134.552 1.2773.056 1.6884.069 4.9462.0627 3.2578-.0062 3.668-.0207 4.9478-.0814 1.28-.0607 2.147-.2652 2.9098-.5633.7889-.3086 1.4578-.72 2.1228-1.3881.665-.6682 1.0745-1.3378 1.3795-2.1284.2957-.7632.4966-1.636.552-2.9124.056-1.2809.0692-1.6898.063-4.948-.0063-3.2583-.021-3.6668-.0817-4.9465-.0607-1.2797-.264-2.1487-.5633-2.9117-.3084-.7889-.72-1.4568-1.3876-2.1228C21.2982 1.33 20.628.9208 19.8378.6165 19.074.321 18.2017.1197 16.9244.0645 15.6471.0093 15.236-.005 11.977.0014 8.718.0076 8.31.0215 7.0301.0839m.1402 21.6932c-1.17-.0509-1.8053-.2453-2.2287-.408-.5606-.216-.96-.4771-1.3819-.895-.422-.4178-.6811-.8186-.9-1.378-.1644-.4234-.3624-1.058-.4171-2.228-.0595-1.2645-.072-1.6442-.079-4.848-.007-3.2037.0053-3.583.0607-4.848.05-1.169.2456-1.805.408-2.2282.216-.5613.4762-.96.895-1.3816.4188-.4217.8184-.6814 1.3783-.9003.423-.1651 1.0575-.3614 2.227-.4171 1.2655-.06 1.6447-.072 4.848-.079 3.2033-.007 3.5835.005 4.8495.0608 1.169.0508 1.8053.2445 2.228.408.5608.216.96.4754 1.3816.895.4217.4194.6816.8176.9005 1.3787.1653.4217.3617 1.056.4169 2.2263.0602 1.2655.0739 1.645.0796 4.848.0058 3.203-.0055 3.5834-.061 4.848-.051 1.17-.245 1.8055-.408 2.2294-.216.5604-.4763.96-.8954 1.3814-.419.4215-.8181.6811-1.3783.9-.4224.1649-1.0577.3617-2.2262.4174-1.2656.0595-1.6448.072-4.8493.079-3.2045.007-3.5825-.006-4.848-.0608M16.953 5.5864A1.44 1.44 0 1 0 18.39 4.144a1.44 1.44 0 0 0-1.437 1.4424M5.8385 12.012c.0067 3.4032 2.7706 6.1557 6.173 6.1493 3.4026-.0065 6.157-2.7701 6.1506-6.1733-.0065-3.4032-2.771-6.1565-6.174-6.1498-3.403.0067-6.156 2.771-6.1496 6.1738M8 12.0077a4 4 0 1 1 4.008 3.9921A3.9996 3.9996 0 0 1 8 12.0077"
                  />
                </svg>
              </a>
            </li>
            <li>
              <a data-social="YouTube" style="--accent-color: red" href="#">
                <svg
                  role="img"
                  viewBox="0 0 24 24"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <title>YouTube</title>
                  <path
                    d="M23.498 6.186a3.016 3.016 0 0 0-2.122-2.136C19.505 3.545 12 3.545 12 3.545s-7.505 0-9.377.505A3.017 3.017 0 0 0 .502 6.186C0 8.07 0 12 0 12s0 3.93.502 5.814a3.016 3.016 0 0 0 2.122 2.136c1.871.505 9.376.505 9.376.505s7.505 0 9.377-.505a3.015 3.015 0 0 0 2.122-2.136C24 15.93 24 12 24 12s0-3.93-.502-5.814zM9.545 15.568V8.432L15.818 12l-6.273 3.568z"
                  />
                </svg>
              </a>
            </li>
          </ul>

          <p class="copyright">
            Copyright &copy; 1996-<span class="year">2027</span> by CineScape,
            Inc. All rights reserved.
          </p>
        </div>

        <div class="address-col">
          <p class="footer-heading">Contact us</p>
          <address class="contacts">
            <p class="address">
              326 Broderick St., 3rd Floor, Los Angeles, CA 93107
            </p>
            <p>
              <a class="footer-link" href="tel:415-201-6370">435-221-6573</a
              ><br />
              <a class="footer-link" href="cinescape.la@cinescape.com"
                >cinescape.la@cinescape.com</a
              >
            </p>
          </address>
        </div>

        <nav class="nav-col">
          <p class="footer-heading">Account</p>
          <ul class="footer-nav">
            <li>
              <a class="footer-link" href="/CineScape/auth/getAllRoles">Create account</a>
            </li>
            <li><a class="footer-link" href="/CineScape/auth/login">Log in</a></li>
            <li><a class="footer-link" href="#">iOS app</a></li>
            <li><a class="footer-link" href="#">Android app</a></li>
          </ul>
        </nav>

        <nav class="nav-col">
          <p class="footer-heading">Company</p>
          <ul class="footer-nav">
            <li><a class="footer-link" href="#">About CineScape</a></li>
            <li><a class="footer-link" href="#">For Businesses</a></li>
            <li><a class="footer-link" href="#">Jobs</a></li>
            <li><a class="footer-link" href="#">Terms of use</a></li>
          </ul>
        </nav>

        <nav class="nav-col">
          <p class="footer-heading">Resources</p>
          <ul class="footer-nav">
            <li><a class="footer-link" href="#">Gift Cards</a></li>
            <li><a class="footer-link" href="#">Help center</a></li>
            <li><a class="footer-link" href="#">Privacy &amp; terms</a></li>
          </ul>
        </nav>
      </div>
    </footer>
    
     <div id="logoutModal" class="modal">
       <div class="modal-content">
            <span class="close-button" onclick="closeLogoutModal()">&times;</span>
            <div class="modal-text-btn-box">
                <p class="modal-text">Are you sure you want to log out?</p>
                <div class="modal-buttons">
                    <button class="yes" onclick="proceedToLogout()">Yes</button>
                    <button class="no" onclick="closeLogoutModal()">No</button>
                </div>
            </div>
        </div>
    </div>
    
    <div id="deleteMovieModal" class="modal">
	   <div class="modal-content">
	      <span class="close-button" onclick="closeDeleteMovieModal()">&times;</span>
	      <div class="modal-text-btn-box">
	         <p class="modal-text">Are you sure you want to delete this movie?</p>
	         <div class="modal-buttons">
	            <button class="yes" onclick="proceedToDeleteMovie()">Yes</button>
	            <button class="no" onclick="closeDeleteMovieModal()">No</button>
	         </div>
	      </div>
	   </div>
	</div>
    

    <script
      type="module"
      src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"
    ></script>
    <script
      nomodule
      src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"
    ></script>
  </body>
</html>
