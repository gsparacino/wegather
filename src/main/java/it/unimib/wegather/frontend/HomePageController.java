package it.unimib.wegather.frontend;

import it.unimib.wegather.frontend.lista.ListaController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static it.unimib.wegather.frontend.ControllerUtils.redirect;

@Controller
@RequiredArgsConstructor
public class HomePageController {

    /**
     * Redirige al @{@link ListaController}
     */
    @GetMapping("/")
    public String getHome(Model model) {
        return redirect(Views.LISTA);
    }

}
