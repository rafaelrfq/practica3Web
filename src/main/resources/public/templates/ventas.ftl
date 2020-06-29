<#include "base.ftl">

<#macro page_body>
    <br><h1 class="text-center">${title}</h1><br>
    <#if admin == true>
        <#list ventas as venta>
            <h5 style="margin-left: 10rem">${venta.nombreCliente} ${venta.fechaCompra?date}</h5>

            <div class="row justify-content-center">
                <table class="table table-bordered table-hover" style="width: 80%">
                    <thead class="thead-light text-center">
                    <th>ID</th>
                    <th>Producto</th>
                    <th>Precio</th>
                    <th>Cantidad</th>
                    <th>Total (RD$)</th>
                    </thead>
                    <tbody class="text-center">
                    <#assign total_general = 0>
                    <#list venta.listaProductos as producto>
                        <#assign total = producto.cantidad * producto.precio>
                        <#assign total_general += total>
                        <tr>
                            <td>${producto.id}</td>
                            <td>${producto.nombre}</td>
                            <td>${producto.precio}</td>
                            <td>${producto.cantidad}</td>
                            <td>${total}</td>
                        </tr>
                    </#list>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td><b>Total General: </b>${total_general}</td>
                    </tr>
                    </tbody>
                </table>
            </div><br>
        </#list>
    <#else>
        <p class="text-center">Usted no tiene autorizacion para acceder a esta pagina.</p>
    </#if>
</#macro>

<@display_page/>