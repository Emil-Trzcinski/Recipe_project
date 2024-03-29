# Recipe Project
Aplikacji webowa umożliwiająca zarządzanie pomysłami na dzienny jadłospis.

Podstawowe założenia:

Utworzenie użytkowania i dostarczenie przepisów na śniadania, obiady i kolacje.
Każdy użytkownik może wygenerować zestaw dla siebie, np. tylko śniadanie, lub zestaw składający się z dwóch (śniadanie i obiad) lub trzech (śniadanie, obiad, kolacja) posiłków.

W ramach aplikacji zostanie utworzony użytkownik z unikalnym identyfikatorem, za pomocą którego będzie mógł odpytać aplikację. 
Identyfikator jest niezbędny do odpytania aplikacji o poszczególne zestawy.

Wymagane parametry rejestracji użytkownika:
- login składający się z więcej niż 4 znaków

Wymagane parametry zapytań dla oczekiwań użytkownika:
- całkowita dzienna kaloryczność, przewidziano zakres na poszczególny posiłek od 300 do 900 kcal
- całkowity czas przygotowania posiłków z minimalnym czasem przygotowania potrawy wynoszącym 20 min
- ilość posiłków w zakresie od 1 do 3,  tzn. śniadanie, obiad i kolacja

Aplikacja zwraca przepisy w zakresie od 0,7 do 1 wartości kaloryczności podanej przez użytkownika, oraz posiada górny limit ilość Kcal na posiłek (900kcal).

Zakres został dodany ze względu na wykorzystanie zew. API:
https://rapidapi.com/apidojo/api/tasty/
które posiada ograniczenie darmowych 500 zapytań, ograniczoną liczbę przepisów z dużą kalorycznością, oraz niestety ogromną liczbę przepisów, które mają braki w danych.

Opracowane przepisy nie muszą być zapisywane przez użytkownika, ponieważ wielokrotne odpytanie z tymi samymi parametrami zwraca te same przepisy.
W ramach odpowiedzi na zapytanie użytkownika zwracane będą dane w formacie JSON. Poniżej lista danych:

- Użytkownik
- Identyfikator
- Nazwa
- Kaloryczność posiłku
- Sposób przygotowania posiłku
- Całkowity czas przygotowania posiłku
- Czas przygotowania posiłku
- Czas gotowania
- Potrzebne składniki
- Link do przykładowego zdjęcia
- Liczba porcji którą można zrobić z jednego przepisu
- Lista tagów związanych z posiłkiem
- Całkowity czas przygotowania wszystkich potraw
- Całkowity czas gotowania
- Lista zakupowa wszystkich składników bez powtórzeń



**Uwaga!**

Ze wzglądu na pracę z zew. Api został wprowadzony limit zapytań do 5 requestów na 1 sec.

**Aplikacja została napisana przy użyciu Spring Boot 2.6.4, Javy 17 oraz Gardle 7.0.**

W celu rozpoczęcia pracy z Api należy zarejestrować się w

https://rapidapi.com/apidojo/api/tasty/

i uzyskać: „X-RapidAPI-Key”
jest niezbędny do wykonania zapytania do tego właśnie api.
Uzyskany klucz należy dodać do enum „**ApiRequestEnums**” w polu : **HEADER_KEY_VALUE**
Znajduje się on w pakiecie "API" > "REQUEST"

W celu uruchomienia aplikacji należy pobrać repozytorium np. wykorzystując Intelij

klikając File > New > Project from Version Control. Następnie dodając link do adresu URL

Pobrany projekt należy zbudować klikając symbol „młotka”.

Po zbudowaniu projektu można już go uruchomić naciskając strzałkę „play”.

Aplikacja domyślnie startuje na porcie: 8080 i właśnie na tym porcie będzie prowadzony nasłuch zapytań.
W tym celu należy skorzystać z kolekcji wywołań, np. korzystając z aplikacji Postman.

Do którego importujemy przygotowaną w pliku kolekcję:

Recipe.postman_collection.json

Znajduje się ona w folderze resources.

Aplikacja korzysta z bazy danych H2 i jest uruchamiana "in memory", aby ułatwić uruchamianie.

Dane do uruchomienia znajdują się w "application.properties" w folderze resources.

Testy integracyjne wymagają dodanego  „X-RapidAPI-Key”, w przeciwnym przypadku nie zadziałają.




