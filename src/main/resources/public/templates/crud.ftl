<#include "base.ftl">

<#macro page_body>
    <br><h1 class="text-center">${title}</h1><br>
    <#if admin == true>
        <button type="button" class="btn btn-light" style="margin-left: 10rem" data-toggle="modal" data-target="#productoModal">
            Crear Producto
        </button>

        <div class="modal fade" id="productoModal" tabindex="-1" role="dialog" aria-labelledby="productoModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="productoModalLabel">Crear Producto</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Cerrar">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form action="/api/productos/crud/" method="post">
                            <div class="form-group">
                                <label for="nombre" class="col-form-label">Nombre:</label>
                                <input type="text" class="form-control" id="nombre" name="nombre">
                            </div>
                            <div class="form-group">
                                <label for="precio" class="col-form-label">Precio:</label>
                                <input type="number" step="any" class="form-control" id="precio" name="precio" min="1.00">
                            </div>
                            <div class="form-group row justify-content-center">
                                <button type="submit" class="btn btn-primary mx-2">Aceptar</button>
                                <button type="button" class="btn btn-secondary mx-2" data-dismiss="modal">Cancelar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <br><br>

        <div class="row justify-content-center">
            <table class="table table-bordered table-hover" style="width: 80%">
                <thead class="thead-light text-center">
                <th>ID</th>
                <th>Producto</th>
                <th>Precio</th>
                <th>Accion</th>
                </thead>
                <tbody class="text-center">
                <#list productos as prod>
                    <tr>
                        <td>${prod.id}</td>
                        <td>${prod.nombre}</td>
                        <td>${prod.precio}</td>
                        <td>
                            <a class="btn btn-primary" href="/api/productos/crud/editar/${prod.id}">Editar</a>
                            <a class="btn btn-danger" href="/api/productos/crud/eliminar/${prod.id}">Eliminar</a>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    <#else>
        <p class="text-center">Usted no tiene autorizacion para acceder a esta pagina.</p>
    </#if>
</#macro>

<@display_page/>