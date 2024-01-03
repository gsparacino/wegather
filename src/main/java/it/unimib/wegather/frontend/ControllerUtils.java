package it.unimib.wegather.frontend;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ControllerUtils {

    /**
     * Restituisce una stringa di "redirect" alla view indicata.
     * Quando questa stringa è a sua volta restituita da un metodo @RequestMapping di un controller,
     * Spring MVC redirige automaticamente l'utente al controller associato alla view fornita in input.
     */
    public static String redirect(@NonNull Views view) {
        return "redirect:/" + view.getId();
    }

    /**
     * Restituisce una stringa di "redirect" alla view indicata, parametrizzata con il PathParam fornito in input.
     * Quando questa stringa è a sua volta restituita da un metodo @RequestMapping di un controller,
     * Spring MVC redirige automaticamente l'utente al controller associato alla view fornita in input.
     */
    public static String redirectWithPathParam(@NonNull Views view, @NonNull Long id) {
        return redirect(view) + "/" + id;
    }

    /**
     * Restituisce una stringa che rappresenta l'id della view fornita in input.
     * Quando questa stringa è a sua volta restituita da un metodo @RequestMapping di un controller,
     * Spring MVC "compila" e restituisce all'utente la view fornita in input.
     */
    public static String viewId(@NonNull Views view) {
        return view.getId();

    }

    /**
     * Restituisce una stringa di "redirect" alla view indicata, parametrizzata con il PathParam fornito in input.
     * Quando questa stringa è a sua volta restituita da un metodo @RequestMapping di un controller,
     * Spring MVC redirige automaticamente l'utente al controller associato alla view fornita in input.
     */
    public static String redirectWithQueryParam(@NonNull Views view, @NonNull Long id) {
        return redirect(view) + "/?id=" + id;
    }


    /**
     * Restituisce una stringa di "redirect" alla view indicata, parametrizzata con il PathParam fornito in input.
     * Quando questa stringa è a sua volta restituita da un metodo @RequestMapping di un controller,
     * Spring MVC redirige automaticamente l'utente al controller associato alla view fornita in input.
     */
    public static String redirectWithData(@NonNull Views view, @NonNull String username) {
        return redirect(view) + "?username=" + username;
    }
}
