package edu.pucmm.isc.controladores;

import edu.pucmm.isc.objetos.CarroCompra;
import edu.pucmm.isc.objetos.Producto;
import edu.pucmm.isc.objetos.VentasProducto;
import edu.pucmm.isc.servicios.StoreServices;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinFreemarker;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.*;

public class TemplateController {
    private Javalin app;
    public TemplateController(Javalin app){
        this.app = app;
    }

    //Obtencion de instacia de tienda
    StoreServices tienda = StoreServices.getInstance();

    //Registro de sistemas de plantillas
    private void registroPlantillas() {
        JavalinRenderer.register(JavalinFreemarker.INSTANCE, ".ftl");
    }

    //Variables usadas en varias rutas
    CarroCompra carrito;

    //Uso de rutas para mostrar templates
    public void aplicarRutas() {
        app.routes(() -> {

            // Verificar si el carrito existe en la sesion antes de cargar
            before(ctx -> {
                carrito = ctx.sessionAttribute("carrito");
                if(carrito == null) {
                    List<Producto> productosIniciales = new ArrayList<Producto>();
                    ctx.sessionAttribute("carrito", new CarroCompra(1, productosIniciales));
                }
            });

            path("/api/", () -> {

                // CRUD de productos
                // http://localhost:7000/api/crud
                get("/productos/crud", ctx -> {
                    Map<String, Object> contexto = new HashMap<>();
                    contexto.put("title", "CRUD Productos");
                    contexto.put("productos", tienda.getListaProductos());
                    contexto.put("cantidad", carrito.getListaProductos().size());
                    contexto.put("usr", tienda.getUsr());
                    contexto.put("admin", tienda.getAdm());
                    contexto.put("usuario", ctx.sessionAttribute("usuario"));
                    ctx.render("/public/templates/crud.ftl", contexto);
                });

                // Crear nuevo producto
                // http://localhost:7000/api/crud
                post("/productos/crud", ctx -> {
                   //Obtener informacion de la form
                   //int id = tienda.getListaProductos().get(tienda.getListaProductos().size() - 1).getId() + 1;
                   String nombre = ctx.formParam("nombre");
                   BigDecimal precio = new BigDecimal(ctx.formParam("precio"));
                   Producto tmp = new Producto(1, nombre, precio);
                   tienda.insertarProductoDB(tmp);
                   ctx.redirect("/api/productos/crud");
                });

                // Editar un producto existente
                // http://localhost:7000/api/productos/crud/editar/:id
                get("/productos/crud/editar/:id", ctx -> {
                    Producto tmp = tienda.getProductoPorID(ctx.pathParam("id", Integer.class).get());
                    Map<String, Object> contexto = new HashMap<>();
                    contexto.put("title", "Editar Producto");
                    contexto.put("prod", tmp);
                    contexto.put("cantidad", carrito.getListaProductos().size());
                    contexto.put("usr", tienda.getUsr());
                    contexto.put("admin", tienda.getAdm());
                    contexto.put("usuario", ctx.sessionAttribute("usuario"));
                    ctx.render("/public/templates/editar_prod.ftl", contexto);
                });

                // Realizar edicion de un producto existente
                // http://localhost:7000/api/productos/crud/editar/
                post("/productos/crud/editar/:id", ctx -> {
                    int id = ctx.pathParam("id", Integer.class).get();
                    String nombre = ctx.formParam("nombre");
                    BigDecimal precio = new BigDecimal(ctx.formParam("precio"));
                    Producto producto = new Producto(id, nombre, precio);
                    tienda.actualizarProducto(producto);
                    ctx.redirect("/api/productos/crud");
                });

                // Borrar un producto existente
                // http://localhost:7000/api/productos/crud/eliminar/:id
                get("/productos/crud/eliminar/:id", ctx -> {
                    Producto tmp = tienda.getProductoPorID(ctx.pathParam("id", Integer.class).get());
                    tienda.eliminarProducto(tmp);
                    ctx.redirect("/api/productos/crud");
                });

                // Listado de productos
                // http://localhost:7000/api/productos
                get("/productos/listar/", ctx -> {
                    Map<String, Object> contexto = new HashMap<>();
                    contexto.put("title", "Listado de Productos");
                    contexto.put("productos", tienda.getListaProductos());
                    contexto.put("cantidad", carrito.getListaProductos().size());
                    contexto.put("usr", tienda.getUsr());
                    contexto.put("admin", tienda.getAdm());
                    contexto.put("usuario", ctx.sessionAttribute("usuario"));
                    ctx.render("/public/templates/productos.ftl", contexto);
                });

                // Agregar producto del listado al carrito
                // http://localhost:7000/api/productos/agregar/:id
                post("/productos/agregar/:id", ctx -> {
                    Producto preprod = tienda.getProductoPorID(ctx.pathParam("id", Integer.class).get());
                    int cantidad = Integer.parseInt(ctx.formParam("cantidad"));
                    Producto producto = new Producto(preprod.getId(), preprod.getNombre(), preprod.getPrecio(), cantidad);
                    carrito.getListaProductos().add(producto);
                    ctx.redirect("/api/productos/listar/");
                });

                // Carrito de compras
                // http://localhost:7000/api/carrito
                get("/carrito/", ctx -> {
                    Map<String, Object> contexto = new HashMap<>();
                    contexto.put("title", "Carrito de Compra");
                    contexto.put("carrito", carrito);
                    contexto.put("cantidad", carrito.getListaProductos().size());
                    contexto.put("usr", tienda.getUsr());
                    contexto.put("admin", tienda.getAdm());
                    contexto.put("usuario", ctx.sessionAttribute("usuario"));
                    ctx.render("/public/templates/carrito.ftl", contexto);
                });

                // Limpiar carrito
                // http://localhost:7000/api/carrito/limpiar
                get("/carrito/limpiar", ctx -> {
                    tienda.setCarrito(carrito);
                    tienda.limpiarCarrito();
                    ctx.sessionAttribute("carrito", tienda.getCarrito());
                    ctx.redirect("/api/carrito/");
                });

                // Eliminar producto del carrito
                // http://localhost:7000/api/carrito/eliminar/:id
                get("/carrito/eliminar/:id", ctx -> {
                    tienda.setCarrito(carrito);
                    Producto tmp = tienda.getProductoEnCarrito(ctx.pathParam("id", Integer.class).get());
                    tienda.getCarrito().borrarProducto(tmp);
                    ctx.sessionAttribute("carrito", tienda.getCarrito());
                    ctx.redirect("/api/carrito/");
                });

                // Procesar compra del carrito
                // http://localhost:7000/api/carrito/procesar
                post("/carrito/procesar/", ctx -> {
                    tienda.setCarrito(carrito);
                    String nombreCliente = ctx.formParam("nombre");
                    VentasProducto venta = new VentasProducto(1, new Date(), nombreCliente, tienda.getCarrito().getListaProductos());
                    tienda.procesarVenta(venta);
                    tienda.limpiarCarrito();
                    ctx.sessionAttribute("carrito", tienda.getCarrito());
                    ctx.redirect("/api/carrito/");
                });

                // Listado de ventas realizadas
                // http://localhost:7000/api/ventas
                get("/ventas/listar", ctx -> {
                    Map<String, Object> contexto = new HashMap<>();
                    contexto.put("title", "Listado de Ventas Realizadas");
                    contexto.put("ventas", tienda.getListaVentas());
                    contexto.put("cantidad", carrito.getListaProductos().size());
                    contexto.put("usr", tienda.getUsr());
                    contexto.put("admin", tienda.getAdm());
                    contexto.put("usuario", ctx.sessionAttribute("usuario"));
                    ctx.render("/public/templates/ventas.ftl", contexto);
                });
            });
        });
    }
}
