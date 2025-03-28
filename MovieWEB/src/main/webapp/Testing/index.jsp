<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

    <link rel="icon" href="images/favicon-64.png" />
    <link rel="apple-touch-icon" href="images/apple-touch-icon.png" />
    <link rel="manifest" href="manifest.webmanifest" />

    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap"
      rel="stylesheet"
    />

    <link rel="stylesheet" href="css/general.css" />
    <link rel="stylesheet" href="css/style.css" />
    <link rel="stylesheet" href="css/queries.css" />

    <script defer src="js/script.js"></script>
<title>Insert title here</title>
</head>
<body>
    <header class="header">
      <a href="#">
        <img
          class="logo"
          alt="CineScape logo"
          src="images/cinescape-logo.png"
        />
      </a>

      <nav class="main-nav">
        <ul class="main-nav-list">
          <li>
            <form class="search-form" action="/search" method="get">
              <input
                class="input-search"
                type="text"
                name="title"
                placeholder="Search movies"
              />
              <button class="btn-search" type="submit">
                <ion-icon class="icon" name="search-outline"></ion-icon>
              </button>
            </form>
          </li>
          <li><a class="main-nav-link" href="browse.html">Movies</a></li>
          <li><a class="main-nav-link" href="#">My list</a></li>
          <li>
            <a class="main-nav-link" href="#">Browse by languages</a>
          </li>
          <li>
            <a class="main-nav-link nav-sign-up" href="sign-up.html">Sign up</a>
          </li>
        </ul>
      </nav>
    </header>

    <main>
      <section class="section-hero">
        <div class="hero">
          <div class="hero-text-box">
            <h1 class="heading-primary">
              Escape into the unknown. It starts in the comfort of your home
            </h1>
            <p class="hero-description">
              CineScape is an exciting streaming service for quick and easy
              movie watching. Discover new films, watch trailers, and create
              your own personal watchlist.
            </p>
            <a class="btn btn--full margin-right-sm" href="#">Join the club</a>
            <a class="btn btn--outline" href="#pricing">Learn more &darr;</a>
            <div class="customers-box">
              <div class="customer-imgs">
                <img
                  src="images/customers/user-1.jpg"
                  alt="User's profile picture"
                />
                <img
                  src="images/customers/user-2.jpg"
                  alt="User's profile picture"
                />
                <img
                  src="images/customers/user-3.jpg"
                  alt="User's profile picture"
                />
                <img
                  src="images/customers/user-4.jpg"
                  alt="User's profile picture"
                />
                <img
                  src="images/customers/user-5.jpg"
                  alt="User's profile picture"
                />
                <img
                  src="images/customers/user-6.jpg"
                  alt="User's profile picture"
                />
              </div>
              <p class="customer-text">
                <span>50+ million</span>
                CineScape enjoyers!
              </p>
            </div>
          </div>
          <div class="hero-img-box">
            <img
              class="hero-img"
              src="images/hero-min.png"
              alt="Escape to the unknown"
            />
          </div>
        </div>
      </section>

      <section class="section-pricing" id="pricing">
        <div class="container">
          <span class="subheading">Pricing</span>
          <h2 class="heading-secondary">
            Enjoying movies without breaking the bank
          </h2>
        </div>

        <div class="container grid grid--3-cols margin-bottom-md">
          <div class="pricing-plan pricing-plan--starter">
            <header class="plan-header">
              <p class="plan-name">Basic</p>
              <p class="plan-price"><span>$</span>4.99</p>
              <p class="plan-text">per month.</p>
            </header>
            <ul class="list">
              <li class="list-item">
                <ion-icon class="list-icon" name="checkmark-outline"></ion-icon>
                <span>Unlimited movies</span>
              </li>
              <li class="list-item">
                <ion-icon class="list-icon" name="checkmark-outline"></ion-icon>
                <span>Watch on 1 device</span>
              </li>
              <li class="list-item">
                <ion-icon class="list-icon" name="checkmark-outline"></ion-icon>
                <span>Watch in HD</span>
              </li>
              <li class="list-item">
                <ion-icon class="list-icon" name="checkmark-outline"></ion-icon>
                <span>Download on 1 device</span>
              </li>
              <li class="list-item">
                <ion-icon class="list-icon" name="close-outline"></ion-icon>
              </li>
            </ul>
            <div class="plan-sing-up">
              <a href="#" class="btn btn--full">Start watching</a>
            </div>
          </div>

          <div class="pricing-plan pricing-plan--starter">
            <header class="plan-header">
              <p class="plan-name">Standard</p>
              <p class="plan-price"><span>$</span>7.99</p>
              <p class="plan-text">per month.</p>
            </header>
            <ul class="list">
              <li class="list-item">
                <ion-icon class="list-icon" name="checkmark-outline"></ion-icon>
                <span>Unlimited movies</span>
              </li>
              <li class="list-item">
                <ion-icon class="list-icon" name="checkmark-outline"></ion-icon>
                <span>Watch on 2 devices</span>
              </li>
              <li class="list-item">
                <ion-icon class="list-icon" name="checkmark-outline"></ion-icon>
                <span>Watch in Full HD</span>
              </li>
              <li class="list-item">
                <ion-icon class="list-icon" name="checkmark-outline"></ion-icon>
                <span>Download on 2 devices</span>
              </li>
              <li class="list-item">
                <ion-icon class="list-icon" name="close-outline"></ion-icon>
              </li>
            </ul>
            <div class="plan-sing-up">
              <a href="#" class="btn btn--full">Start watching</a>
            </div>
          </div>

          <div class="pricing-plan pricing-plan--complete">
            <header class="plan-header">
              <p class="plan-name premium">Premium</p>
              <p class="plan-price premium"><span>$</span>9.99</p>
              <p class="plan-text premium">per month.</p>
            </header>
            <ul class="list">
              <li class="list-item premium">
                <ion-icon class="list-icon" name="checkmark-outline"></ion-icon>
                <span>Unlimited movies</span>
              </li>
              <li class="list-item premium">
                <ion-icon class="list-icon" name="checkmark-outline"></ion-icon>
                <span>Watch on 4 devices</span>
              </li>
              <li class="list-item premium">
                <ion-icon class="list-icon" name="checkmark-outline"></ion-icon>
                <span>Watch in Ultra HD</span>
              </li>
              <li class="list-item premium">
                <ion-icon class="list-icon" name="checkmark-outline"></ion-icon>
                <span>Download on 6 devices</span>
              </li>
              <li class="list-item premium">
                <ion-icon class="list-icon" name="checkmark-outline"></ion-icon>
                <span>CineScape spatial audio</span>
              </li>
            </ul>
            <div class="plan-sing-up">
              <a href="#" class="btn btn--premium">Start watching</a>
            </div>
          </div>
        </div>

        <section class="container grid">
          <aside class="plan-details">
            Prices include all applicable taxes. You can cancel at any time.
          </aside>
        </section>

        <div class="container grid grid--4-cols">
          <div class="feature">
            <ion-icon class="feature-icon" name="pricetag-outline"></ion-icon>
            <p class="feature-title">Never be bored again!</p>
            <p class="feature-text">
              Our subscriptions are easily affordable for everyone to enjoy.
            </p>
          </div>
          <div class="feature">
            <ion-icon class="feature-icon" name="bookmark-outline"></ion-icon>
            <p class="feature-title">Make lists</p>
            <p class="feature-text">
              Add movies to your list so you can access them easily anytime.
            </p>
          </div>
          <div class="feature">
            <ion-icon class="feature-icon" name="tv-outline"></ion-icon>
            <p class="feature-title">Sharing is caring</p>
            <p class="feature-text">
              You can share your account with friends and family. Up to 4
              devices with premium.
            </p>
          </div>
          <div class="feature">
            <ion-icon class="feature-icon" name="pause-outline"></ion-icon>
            <p class="feature-title">Pause anytime</p>
            <p class="feature-text">
              Busy schedule? Pause your movie, and when you're free continue
              where you left of.
            </p>
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
              src="images/cinescape-logo.png"
            />
          </a>

          <ul class="social-links">
            <li>
              <a class="footer-link" href="#"
                ><ion-icon class="social-icon" name="logo-instagram"></ion-icon
              ></a>
            </li>
            <li>
              <a class="footer-link" href="#"
                ><ion-icon class="social-icon" name="logo-facebook"></ion-icon
              ></a>
            </li>
            <li>
              <a class="footer-link" href="#"
                ><ion-icon class="social-icon" name="logo-twitter"></ion-icon
              ></a>
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
              326 Broderick St., 3nd Floor, Los Angeles, CA 93107
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
            <li><a class="footer-link" href="#">Create account</a></li>
            <li><a class="footer-link" href="#">Sign in</a></li>
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
            <li><a class="footer-link" href="#">Privacy & terms</a></li>
          </ul>
        </nav>
      </div>
    </footer>

    <script
      type="module"
      src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"
    ></script>
    <script
      nomodule
      src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"
    ></script>




	<a href="/CineScape/obican/getLang">Get langs</a> <br>
	<a href="/CineScape/obican/getGenres">Get genres</a> <br>
	<a href="/CineScape/obican/top10Movies">Get Most popular</a> <br>
	<a href="/CineScape/SaveMovieImg.jsp">Save movie with img path</a> <br>
	<a href="/CineScape/FindMovie.jsp">Find movie</a> <br>
</body>
</html>