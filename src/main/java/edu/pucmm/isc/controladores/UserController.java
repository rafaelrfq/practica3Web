package edu.pucmm.isc.controladores;
import edu.pucmm.isc.objetos.CarroCompra;
import edu.pucmm.isc.objetos.Producto;
import edu.pucmm.isc.objetos.Usuario;
import edu.pucmm.isc.servicios.StoreServices;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinFreemarker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class UserController {
    private Javalin app;
    public UserController(Javalin app){
        this.app = app;
    }

    //Registro de sistemas de plantillas
    private void registroPlantillas() {
        JavalinRenderer.register(JavalinFreemarker.INSTANCE, ".ftl");
    }

    //Obtencion de instacia de tienda
    StoreServices tienda = StoreServices.getInstance();

    public void aplicarRutas() {
        app.routes(() -> {

            // Verificar si el carrito existe en la sesion antes de cargar
            before(ctx -> {
                CarroCompra carrito = ctx.sessionAttribute("carrito");
                if(carrito == null) {
                    List<Producto> productosIniciales = new ArrayList<Producto>();
                    ctx.sessionAttribute("carrito", new CarroCompra(1, productosIniciales));
                }
            });

            path("/api/", () -> {

                // Render de Login de usuarios
                // http://localhost:7000/api/usuarios/login/
                get("/usuarios/login/", ctx -> {
                    CarroCompra carrito = ctx.sessionAttribute("carrito");
                    Map<String, Object> contexto = new HashMap<>();
                    contexto.put("usr", false);
                    contexto.put("admin", false);
                    contexto.put("title", "Login de Usuario");
                    contexto.put("cantidad", carrito.getListaProductos().size());
                    ctx.render("/public/templates/login.ftl", contexto);
                });

                // Manejo de Login de usuarios
                // http://localhost:7000/api/usuarios/login/
                post("/usuarios/login/", ctx -> {
                    String usr = ctx.formParam("usuario");
                    String passw = ctx.formParam("password");
                    Usuario tmp = tienda.loginUsuario(usr, passw);
                    ctx.sessionAttribute("usuario", tmp);
                    ctx.redirect("/");
                });

                // Logout de usuarios
                // http://localhost:7000/api/usuarios/logout/
                get("/usuarios/logout/", ctx -> {
                    tienda.logoutUsuario();
                    ctx.req.getSession().invalidate();
                    ctx.redirect("/api/usuarios/login/");
                });
            });
        });
    }
}