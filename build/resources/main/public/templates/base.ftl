<#ftl encoding="utf-8">
<#macro page_head>
    <title>No title in page_head</title>
</#macro>

<#macro page_body>
    <h1>No page_body</h1>
</#macro>

<#macro display_page>
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <title>Carrito de Compras - Javalin</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
        <@page_head/>
    </head>
    <body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="/api/productos/listar/">E-Market<img src="/img/logo_cart.png" width="40" height="40"></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">
                <#if admin == true>
                    <li class="nav-item inline">
                        <a class="nav-link" href="/api/productos/listar/">Comprar</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/api/ventas/listar/">Ventas Realizadas</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/api/productos/crud/">Administrar Productos</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/api/carrito/">Carrito (${cantidad})</a>
                    </li>
                    <li>
                        <a class="nav-link" href="/api/usuarios/logout/">Bienvenido ${usuario.nombre}. Logout</a>
                    </li>
                <#elseif usr == true>
                    <li class="nav-item inline">
                        <a class="nav-link" href="/api/productos/listar/">Comprar</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/api/carrito/">Carrito (${cantidad})</a>
                    </li>
                    <li>
                        <a class="nav-link" href="/api/usuarios/logout/">Bienvenido ${usuario.nombre}. Logout</a>
                    </li>
                <#else>
                    <li class="nav-item inline">
                        <a class="nav-link" href="/api/productos/listar/">Comprar</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/api/carrito/">Carrito (${cantidad})</a>
                    </li>
                    <li class="nav-item inline">
                        <a class="nav-link" href="/api/usuarios/login/">Login</a>
                    </li>
                </#if>
            </ul>
        </div>
    </nav>
    <@page_body/>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
    </body>
    </html>
</#macro>