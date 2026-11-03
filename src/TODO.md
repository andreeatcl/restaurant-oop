# To Do List

## To do:

### Clasa Restaurant
- metode CRUD produse
- generare id-uri?
- inregistrare comanda
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

### Clasa FisierUtils
- check exceptions?
- verificare id-uri duplicate?
- verificare produse inexistente?

### Main
- diverse...

# Done:

### Clasa Produs
- constructori, getters, setters
- input validation

### Clasa Comanda
- constructori, getters, setters
- input validation

### Clasa Restaurant
- getters

### Clasa FisierUtils
- incarcare/salvare produse.json
- incarare/salvare comenzi.json

### Exceptii
- ProdusInexistentException
- DateInvalideException
- CampGolException
- ComandaGoalaException