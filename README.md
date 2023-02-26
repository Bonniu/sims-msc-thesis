# Praca magisterska - SIMS (Security Intelligent Monitoring System) 


## Wymagania:
* System operacyjny Linux lub Windows z zainstalowanym WSL 2.0
* Docker
* Docker-compose

## Uruchamianie systemu SIMS krok po kroku:
1. Przejdź do katalogu `frontend` i wykonaj polecenie `docker build -t sims-frontend .`
2. Przejdź do katalogu `backend` i wykonaj polecenie `docker build -t sims-backend .`
3. Przejdź do katalogu `ml-module` i wykonaj polecenie `docker build -t sims-ml-module .`
   * Wszystkie powyższe kroki można również wykonać poprzez lokalne uruchomienie aplikacji np. z IntelliJ
4. Przejdź do katalogu `cicd` i uruchom system przez polecenie `docker-compose up -d`
5. Wejdź na stronę [localhost:4200/dashboard](localhost:4200/dashboard), powinien pojawić się główny widok z aplikacji

Jeśli powyższe działania nie przyniosą efektu, należy zmienić zmodyfikować plik etc/hosts, aby zawierał poniższe wpisy:
```
127.0.0.1 sims-frontend
127.0.0.1 sims-db-backend
127.0.0.1 sims-kafka
127.0.0.1 sims-kafka-ui
127.0.0.1 sims-zookeeper
127.0.0.1 sims-ml-module
127.0.0.1 sims-backend
```

Jeśli dalej wystąpią problemy, należy uruchomić poszczególne komponenty w IDE, np. w IntelliJ

Przygotowany system zawiera przykładowe działanie systemu, bez 'podpinania' go do prawdziwej aplikacji, którą mogłby monitorować.
Zawiera logi w odpowiednich katalogach `backend/logs` oraz `backend/logsV`, które zawierają przygotowane logi z danej aplikacji.
Aby sprawdzić, jak zachowałby się system SIMS, należy wykonać zapytanie GET na adres [sims-backend:8080/mlTest](sims-backend:8080/mmlTest)
lub [sims-backend:8080/mlInvalid](sims-backend:8080/mlInvalid), w zależności od celu (poprawne/niepoprawne działanie).


