<#include "base.ftl">

<#macro page_body>
    <br><h1 class="text-center">${title}</h1><br>
    <#if admin == true>
        <div class="card bg-light mb-3 mx-auto" style="max-width: 25rem;">
            <div class="card-header">${title}</div>
            <div class="card-body">
                <form method="post" action="/api/productos/crud/editar/${prod.id}">
                    <div class="form-group">
                        <label for="nombre" class="col-form-label">Nombre:</label>
                        <input type="text" class="form-control" id="nombre" name="nombre" value="${prod.nombre}">
                    </div>
                    <div class="form-group">
                        <label for="precio" class="col-form-label">Precio:</label>
                        <input type="number" step="any" class="form-control" id="precio" name="precio" min="1.00" value="${prod.precio?string("0")}">
                    </div>
                    <div class="form-group row justify-content-center">
                        <button type="submit" class="btn btn-primary mx-2">Aceptar</button>
                        <a class="btn btn-secondary" href="/api/productos/crud/">Regresar</a>
                    </div>
                </form>
            </div>
        </div>
    <#else>
        <p class="text-center">Usted no tiene autorizacion para acceder a esta pagina.</p>
    </#if>
</#macro>

<@display_page/>