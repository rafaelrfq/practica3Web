<#include "base.ftl">

<#macro page_body>
    <br><h1 class="text-center">${title}</h1>
    <div class="row justify-content-center">
        <div class="card w-75">
            <h3 class="card-header">Datos del Cliente</h3>
            <div class="card-body">
                <form class="form-inline" id="cartform" method="post" action="/api/carrito/procesar/">
                    <div class="form-group ml-5">
                        <label for="nombre">Nombre del Cliente:</label>
                        <#if usr == true>
                            <input type="text" class="form-control mx-sm-3" id="nombre" name="nombre" value="${usuario.nombre}" required>
                        <#else>
                            <input type="text" class="form-control mx-sm-3" id="nombre" name="nombre" required>
                        </#if>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <br>

    <div class="row justify-content-center">
        <table class="table table-bordered table-hover" style="width: 80%">
            <thead class="thead-light text-center">
            <th>Producto</th>
            <th>Precio</th>
            <th>Cantidad</th>
            <th>Total (RD$)</th>
            <th>Accion</th>
            </thead>
            <tbody class="text-center">
            <#assign total_carrito = 0>
            <#if carrito.listaProductos?size gt 0>
                <#list carrito.listaProductos as producto>
                    <tr>
                        <td>${producto.nombre}</td>
                        <td>${producto.precio}</td>
                        <td>${producto.cantidad}</td>
                        <#assign total = producto.cantidad * producto.precio>
                        <#assign total_carrito += total>
                        <td>${total}</td>
                        <td>
                            <a class="btn btn-danger" href="/api/carrito/eliminar/${producto.id}">Eliminar</a>
                        </td>
                    </tr>
                </#list>
            </#if>
                <tr class="table-info">
                    <td></td>
                    <td></td>
                    <td></td>
                    <td><b>Total General: </b></td>
                    <td>RD$ ${total_carrito}</td>
                </tr>
            </tbody>
        </table>
    </div>
    <br>
    <div class="text-center">
        <button type="submit" form="cartform" class="btn btn-primary">Procesar Compra</button>
        <a class="btn btn-danger" href="/api/carrito/limpiar">Limpiar Carrito Compra</a>
    </div>

</#macro>

<@display_page/>