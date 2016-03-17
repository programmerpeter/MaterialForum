<%@page contentType="text/html" pageEncoding="utf-8"%>
<nav>
    <div class="nav-wrapper">
        <a href="/" class="brand-logo">
            &nbsp;&nbsp;&nbsp;
            <c:choose>
                <c:when test="${empty sessionScope.user}">Witaj na ${header.host} nieznajomy!</c:when>
                <c:otherwise>Witaj na ${header.host} ${sessionScope.user.nick}!</c:otherwise>
            </c:choose>
        </a>
        <ul id="nav-mobile" class="right hide-on-med-and-down">
            <c:choose>
                <c:when test="${empty sessionScope.user}">
                    <li><a href="#login" class="modal-trigger">Zaloguj się</a></li>
                    <li><a href="#register" class="modal-trigger">Zarejestruj się</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="/logout/" class="modal-trigger">Wyloguj się</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>
<div id="login" class="modal">
    <form id="loginForm" action="/login/" method="post" novalidate="novalidate">
        <div class="modal-content">
            <h5>Logowanie</h5>
            <div class="row">
                <div class="input-field col s12">
                    <input name="nickOrEmail" type="text" placeholder="Nick lub e-mail" />
                    <label>Nick lub e-mail:</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s12">
                    <input name="password" type="password" placeholder="Hasło" />
                    <label>Hasło:</label>
                </div>
            </div>
        </div>
        <div class="modal-footer">
            <button type="submit" class="modal-action waves-effect waves-green btn-flat">Zaloguj się</button>
        </div>
    </form>
</div>

<div id="register" class="modal">
    <form id="registerForm" action="/register/" method="post" novalidate="novalidate">
        <div class="modal-content">
            <h5>Rejestracja</h5>
            <div class="row">
                <div class="input-field col s12">
                    <input name="nick" type="text" placeholder="Nick" />
                    <label>Nick:</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s12">
                    <input name="password" type="password" placeholder="Hasło" />
                    <label>Hasło:</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s12">
                    <input name="email" type="email" placeholder="E-mail" />
                    <label>E-mail:</label>
                </div>
            </div> 
        </div>
        <div class="modal-footer">
            <button type="submit" class="modal-action waves-effect waves-green btn-flat">Zarejestruj się</button>
        </div>
    </form>
</div>