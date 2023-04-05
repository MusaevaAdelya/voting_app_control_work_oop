<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <link rel="stylesheet" href="/styles/style.css"/>
    <link rel="stylesheet" href="/node_modules/bootstrap-icons/font/bootstrap-icons.css">
    <link rel="stylesheet" href="/node_modules/bootstrap/dist/css/bootstrap.min.css">
    <title>Voting App</title>
</head>

<body>
<main>
    <section class="top-bar">
        <#if isAdmin>
            <#if endOfVoting>
                <form action="/renew-voting" method="post" role="form">
                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                               value="${(_csrf.token)!'--no-token--'}"/>
                    </#if>
                    <button type="submit" class="bar-btn"><i class="bi bi-arrow-counterclockwise"></i>Возобновить
                    </button>
                </form>
            <#else>
                <form action="/end-voting" method="post" role="form">
                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                               value="${(_csrf.token)!'--no-token--'}"/>
                    </#if>
                    <button type="submit" class="bar-btn"><i class="bi bi-patch-check-fill"></i>Закончить голосование
                    </button>
                </form>

            </#if>
        </#if>


        <form action="/logout" method="post" role="form">
            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                       value="${(_csrf.token)!'--no-token--'}"/>
            </#if>

            <div class="nav-item d-sm-inline  ms-3">
                <button class="bar-btn" type="submit"><i class="bi bi-door-closed-fill"></i>Выйти</button>
            </div>
        </form>

    </section>

    <#if !(endOfVoting) && isAdmin>
        <section class="createCandidate">
            <form action="/add-candidate" method="post" enctype="multipart/form-data"
                  modelAttribute="newCandidate">
                <h2>Добавьте нового кандидата</h2>

                <div class="input-group w-100">
                    <span class="input-group-text">Имя и фамилия</span>
                    <input name="firstName" type="text" aria-label="First name" class="form-control" required>
                    <input name="lastName" type="text" aria-label="Last name" class="form-control" required>
                </div>
                <div class="mb-3 mt-5 w-100">
                    <input name="image" class="form-control" type="file" accept="image/*" required>
                </div>
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                           value="${(_csrf.token)!'--no-token--'}"/>
                </#if>
                <button type="submit" class="btn-primary add-candidate-btn">Добавить кандидата</button>
            </form>
        </section>
    </#if>



    <#if endOfVoting>
        <section class="main-container">
            <h2 class="title mt-5">Победитель: <span class="color-primary">${winner.firstName} ${winner.lastName}</span>🎉
            </h2>

            <div class="card-container">
                <#list candidates as candidate>

                    <div class="candidate-card <#if candidate.winner>selected</#if>">
                        <div class="img-container">
                            <img src="${candidate.image}" alt="candidate's picture">
                        </div>

                        <div class="my-auto">
                            <div class="candidate-name"><i class="bi bi-person-fill"></i> ${candidate.votes} голосов
                            </div>
                            <div class="candidate-name">${candidate.firstName} ${candidate.lastName}</div>
                        </div>
                    </div>
                </#list>

            </div>

        </section>

    <#else>
        <#if isAdmin>
            <section class="main-container">
                <h2 class="title">Кандидаты</h2>

                <div class="card-container">
                    <#list candidates as candidate>
                        <div class="candidate-card " onclick="selectCandidate(event, ${candidate.id})">
                            <div class="img-container">
                                <img src="${candidate.image}" alt="candidate's picture">
                            </div>

                            <div class="my-auto">
                                <div class="candidate-name"><i class="bi bi-person-fill"></i> ${candidate.votes}
                                    голосов
                                </div>
                                <div class="candidate-name">${candidate.firstName} ${candidate.lastName}</div>
                            </div>
                        </div>
                    </#list>
                </div>
            </section>
        <#else>
            <#if alreadyVoted>
                <section class="main-container">
                    <h2 class="title">Спасибо за голосование!!!</h2>
                    <img src="/images/done-icon.svg" alt="done" class="done">
                </section>
            <#else>
                <form action="/vote" method="post" onsubmit="handleSubmit(event)" id="form">
                    <section class="main-container">
                        <h2 class="title">Кандидаты</h2>

                        <div class="card-container">
                            <#list candidates as candidate>
                                <div class="candidate-card " onclick="selectCandidate(event, ${candidate.id})">
                                    <div class="img-container">
                                        <img src="${candidate.image}" alt="candidate's picture">
                                    </div>

                                    <div class="my-auto">
                                        <div class="candidate-name">${candidate.firstName} ${candidate.lastName}</div>
                                    </div>
                                </div>
                            </#list>
                        </div>
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                   value="${(_csrf.token)!'--no-token--'}"/>
                        </#if>
                        <input type="hidden" name="candidateId" id="candidateIdInput">
                        <button type="submit" class="vote-btn btn-primary" id="vote-btn">Проголосовать</button>

                    </section>
                </form>
            </#if>
        </#if>


    </#if>


</main>
<script src="/node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="/scripts/script.js"></script>
</body>

</html>