package it.unimib.wegather.authentication;

import it.unimib.wegather.frontend.Views;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import static it.unimib.wegather.frontend.ControllerUtils.viewId;

/**
 * Implementazione di @{@link Filter} che verifica se la sessione dell'utente è attiva.
 * In caso contrario, redirige la richiesta alla pagina di login.
 */
@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements Filter {

    private final UserContext userContext;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;

        // Se è una richiesta per la pagina di login...
        if (isLoginRequest(servletRequest)) {
            // Gestisci la richiesta seguendo il flusso normale
            chain.doFilter(request, response);
        } else {
            // Leggi il valore del cookie contenente lo username, se presente
            Optional<String> username = getUsernameFromCookie(servletRequest);

            // Se l'utente è già autenticato...
            if (username.isPresent()) {
                // Salva lo username dell'utente nel Bean UserContext
                username.ifPresent(userContext::setUsername);
                // Gestisci la richiesta seguendo il flusso normale
                chain.doFilter(request, response);
            } else {
                // Altrimenti redirigi la richiesta all'URL "/login"
                HttpServletResponse servletResponse = (HttpServletResponse) response;
                servletResponse.sendRedirect(viewId(Views.LOGIN));
            }
        }

    }

    /**
     * Controlla se la request corrisponde alla pagina di login o di register
     *
     * @param servletRequest servlet request object
     * @return {@code true} se la request è una registrazione o una login, {@code false} altrimenti
     */
    private boolean isLoginRequest(@NonNull HttpServletRequest servletRequest) {
        String requestURI = servletRequest.getRequestURI();
        String loginUrl = "/" + viewId(Views.LOGIN);
        String registerUrl = "/" + viewId(Views.REGISTER);
        return (loginUrl.equalsIgnoreCase(requestURI) || registerUrl.equalsIgnoreCase(requestURI));
    }

    /**
     * Verifica se nella richiesta è incluso un cookie "username", ed eventualmente ne salva il valore in UserContext
     *
     * @return Un <code>Optional</code> contenente il valore del cookie se l'utente è già autenticato,
     * un <code>Optional</code> vuoto in tutti gli altri casi
     */
    private Optional<String> getUsernameFromCookie(@NonNull HttpServletRequest servletRequest) {
        Optional<String> usernameCookie = Optional.empty();
        Cookie[] cookies = servletRequest.getCookies();
        if (cookies != null) {
            usernameCookie = Stream.of(cookies)
                    .filter(cookie -> StringUtils.equalsIgnoreCase("username", cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst();
        }
        return usernameCookie;
    }

}
