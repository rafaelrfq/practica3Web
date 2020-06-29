<#include "base.ftl">

<#macro page_body>
    <br><br><br><br>
    <div class="row justify-content-center">
        <div class="card w-25">
            <h3 class="card-header text-center">${title}</h3>
            <div class="card-body">
                <form method="post" action="/api/usuarios/login/">
                    <div class="form-group">
                        <label for="usuario">Nombre de usuario:</label>
                        <input type="text" class="form-control" id="usuario" name="usuario" required>
                        <label for="password">Password:</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>
                    <div class="text-center">
                        <button type="submit" class="btn btn-primary">Iniciar Sesion</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <br>
</#macro>

<@display_page/>