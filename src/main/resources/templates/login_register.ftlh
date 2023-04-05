<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/styles/login_register.css">
    <title>Voting App</title>
</head>

<body>
    <div class="container" id="container">
        <div class="form-container sign-up-container">
            <form action="/register" method="post"  role="form" modelAttribute="registerForm">
                <h1>Создайте аккаунт</h1>
                <input name="email" type="email" placeholder="Эл.почта" class="m-top"/>
                <input name="password" type="password" placeholder="Пароль" class="m-bottom" />
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                           value="${(_csrf.token)!'--no-token--'}"/>
                </#if>
                <button type="submit">Создать</button>
            </form>
        </div>
        <div class="form-container sign-in-container">
            <form action="/login" method="post"  role="form">
                <h1>Войдите в свой аккаунт</h1>
                <input name="username" type="email" placeholder="Эл.почта" class="m-top" />
                <input name="password" type="password" placeholder="Пароль" class="m-bottom" />
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                           value="${(_csrf.token)!'--no-token--'}"/>
                </#if>
                <button type="submit">Войти</button>
            </form>
        </div>
        <div class="overlay-container">
            <div class="overlay">
                <div class="overlay-panel overlay-left">
                    <h1>С возвращением!</h1>
                    <p style="margin:2rem 0">Войдите в свой аккаунт, чтобы начать голосовать</p>
                    <button class="ghost" id="signIn">Войти в аккаунт</button>
                </div>
                <div class="overlay-panel overlay-right">
                    <h1>Привет!</h1>
                    <p style="margin:2rem 0">Нет аккаунта? Создайте новый аккаунт, чтобы начать голосовать</p>
                    <button class="ghost" id="signUp">Создать аккаунт</button>
                </div>
            </div>
        </div>
    </div>

    <script src="/scripts/login_register.js"></script>
</body>

</html>