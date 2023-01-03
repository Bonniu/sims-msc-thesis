# potential-si
Praca magisterska


### Wymagania:
* System operacyjny Linux (Windows - WSL 2.0)
* Docker

### tldr:
Aby uruchomić system, należy zbudować obrazy serwisów, a następnie uruchomić docker-compose.yml.


## Krok po kroku:
1. Przejdź do katalogu `frontend` i wykonaj polecenie `docker build -t frontend .` (~15min)
2. Przejdź do katalogu `backend` i wykonaj polecenie `docker build -t backend .` (~5min)
3. Przejdź do katalogu `ml-module` i wykonaj polecenie `docker build -t ml-module .` (~10min)
   4. Wszystkie powyższe kroki można również wykonać poprzez lokalne uruchomienie aplikacji np. z IntelliJ, 
   jednak wtedy trzeba zmienić nazwy hostów 
   w application.yml, app.module.ts i main.py
3. Przejdź do katalogu `cicd` i uruchom system przez polecenie `docker-compose up -d`
4. Jeśli nie działa front, restart kompa (cache xD)
5. Wejdź na stronę [localhost:4200/dashboard](localhost:4200/dashboard), powinien pojawić się główny widok z aplikacji
6. r


### Frontend help (dev)
`frontend` - `backend:8080`, 
`frontend-localhost` - `localhost:8080`

ale `frontend-localhost` działa, a zwykły nie, w przeciwieństwie do bazy
