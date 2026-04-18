# MINIM 1 per Carla Luján

## Part I
En aquesta part he implementat les qüestions bàsiques del mínim. He de mencionar que com a id de vols i avions he ficat F1, F2,... i P1, P2,P3... en comptes de en els vols ficar els identificadors de vols normals als aeroports com VTXXX perquè aixì em resultava més intuitiu.
Com a id de persones per les maletes, he ficat noms.
### Funcions implementades
    - afegir avions
    - consultar avions per id
    - afegir vols
    - consultar vols per id
    - facturacio de maletes en checkInLuggage
    - consulta de maletes per vol
    - netejar dades amb clears
    - tests basics per comprovar

### Errors detectats
    - en aquest punt de part I finalitzada, no funciona el REST perquè tinc reciclada la del exemple que vaig realitzar de Llibreria, pero ho arreglaré més tard en la part II, on ja explicare els canvis fets
    - tenia errors de compilacions en FlightManagerImpl perquè utilitzava luggagesbyFlight quan en realitat la B havia de ser majuscula
    - també tenia un bug amb ordre incorrecte de origin i destination, pero gràcies a la compilació del copilot ho he pogut detectar

## Part II
En aquesta part he implementat la capa REST del projecte a travez de la classe 'FlightsService', connectant els endpoints HTTP amb el FlightManager
### Funcionalidades implementadas
    - ja he netejat el REST del anterior minim que tenia pujat com a base
    - es poden crear avions mitjançant POST /flights/planes
    - podem consultar avions mitjançant GET /flights/planes/{id}
    - es poden crear vols mitjançant POST /flights
    - podem consultar vols amb GET /flights/{id}
    - podem facturar maletes en un vol amb POST /flights/{flightId}/luggages
    - podem consultar maletes associades a un vol amb GET /flights/{flightId}/luggages
    - eliminació de dades en memòria
    - en aquesta part m'he ajudat de la IA en moments puntuals per poder implementar-ho, a més de que partia com ja he dit de la base de la llibreria

### Errors trobats
    - els endpoints serveixen per consultar dades especifiques, però no donen llistes completes dels vols, maletes o avions. no és un error com a tal pero no està implementat
    - nomes hi ha una excepció comú que és la de FlightNotFound, però la crido quan vull comprovar si un avió existeix

## Actualització del projecte
he afegit endpoints nous bàsics:
- obtenir tots els avions
- obtenir tots els vols
- obtenir totes les maletes

he arreglat un error que havia el test amb les maletes. com que l'estructura és una pila, la llista que estava utilitzant no respetava l'ordre de fons a exterior, i donava error al testGetLuggageByFlight. ho he corregit tornant una llista inversa, que seria l'ordre real de descàrrega.

### base URI

http://localhost:8080/dsaApp/

### endpoints
    - `http://localhost:8080/dsaApp/flights/F1`
    - `http://localhost:8080/dsaApp/flights/planes/P1`
    - `http://localhost:8080/dsaApp/flights/F1/luggages`

## High-Level Server Structure

```mermaid
graph TD
    A[HTTP Client] --> B[Grizzly Server]
    B --> C[Static Content Handler<br/>/public]
    C --> D[Public Files<br/>index.html]
    C --> E[Swagger UI<br/>/public/swagger]
    B --> F[REST API Handler<br/>/dsaApp]
    F --> G[TracksService<br/>TextService]
```

## Main Architecture

```mermaid
graph TD
    A[HTTP Client] --> B[TracksService]
    A --> C[TextService]
    B --> D[TracksManager]
    D --> E[Track Model]
    F[Main] --> G[Grizzly Server]
    G --> B
    G --> C
    H[MyExceptionMapper] --> G
```

### Main Components:
- **FlightService**: REST service that exposes endpoints to manage flights (GET, POST, etc.).
- **TextService**: Simple REST service for text responses.
- **FlightManager**: Interface and implementation for the business logic of flights management.
- **Fligh**: Data model representing a flight, with an id, the id of the plane assignated, time of arrival and leaving, origin and destination.
- **Plane**: Data model representing a plane, with an id, model and company.
- **Luggage**: Data model representing a luggage in a flight, with the user id owner, the id of the luggage and .
- **MyExceptionMapper**: Exception mapper to handle API errors.
- **FlightNotFoundException**: Custom exception for flights or planes not found.
