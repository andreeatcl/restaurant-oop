# To Do List

## To do:

### Clasa Restaurant
- maybe CRUD produse?
- rapoarte cerinta
  - valoarea totala a comenzilor dintr-o anumita zi
  - nr total de comenzi realizate intr-o anumita perioada
  - produsele cele mai vandute (pe baza cantitatilor comandate)
  - valoarea totala a vanzatilor pentru un anumit produs
  - comenzile realizate intr-o anumita zi
  - valoarea medie a unei comenzi
- rapoarte extra (TBD) 
  - vanzari / zilele sapt (double[])
  - comenzi / ora (int[])
  - produsele cele mai vandute (pe baza venitului generat)
  - produsele cele mai vandute (pe baza cantitatilor comandate) pe categorii
  - numarul medii de kcal a unor comenzi (?) (pe intervale orare?)

### Main
- meniu utilizare aplicatie:
  1. afisare meniu
  2. adaugare produs
  3. afisare comenzi
  4. inregistrare produs
  5. modificare comanda
  6. stergere comanda
  7. rapoarte 
  8. iesire din aplicatie

# Done:

### Clasa Produs
- constructori, getters, setters
- input validation
- custom toString() pentru afisare in meniu

### Clasa Comanda
- constructori, getters, setters
- input validation
- afisare detaliata

### Clasa Restaurant
- getters
- mic refactor pe partea de produse (folosim direct meniu)
- helper main incarcare comenzi/produse
- autoincrement ids
- add comanda/produs
- afisare meniu
- afisare comenzi
- CRUD comenzi

### Clasa FisierUtils
- incarcare/salvare produse.json
- incarare/salvare comenzi.json

### Exceptii
- ProdusInexistentException
- DateInvalideException
- CampGolException
- ComandaGoalaException

### Diverse
- verificare id-uri duplicate pt citire din fisier + autoincrementare
- verificare produse/comenzi goale