<#include "base.ftl">

<#macro page_body>
    <br><h1 class="text-center">${title}</h1><br>
    <div class="row justify-content-center">
        <table class="table table-bordered table-hover" style="width: 80%">
            <thead class="thead-light text-center">
                <th>Producto</th>
                <th>Precio</th>
                <th>Cantidad</th>
                <th>Accion</th>
            </thead>
            <tbody class="text-center">
                <#list productos as prod>
                    <tr>
                        <td>${prod.nombre}</td>
                        <td>${prod.precio}</td>
                        <td>
                            <form method="post" id="form${prod.id}" action="/api/productos/agregar/${prod.id}">
                                <input type="number" min="1" class="form-control" id="cantidad" name="cantidad" value="1">
                            </form>
                        </td>
                        <td>
                            <button type="submit" form="form${prod.id}" class="btn btn-success">Agregar</button>
                        </td>
                    </tr>
                </#list>
            </tbody>
        </table>
    </div>
</#macro>

<@display_page/>