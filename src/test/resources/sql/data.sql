-- Utenti
INSERT INTO UTENTE (ID, USERNAME, NOME, COGNOME, EMAIL, TELEFONO)
VALUES (1, 'johndoe', 'John', 'Doe', 'john@doe.com', '123456789');
INSERT INTO UTENTE (ID, USERNAME, NOME, COGNOME, EMAIL, TELEFONO)
VALUES (2, 'janedoe', 'Jane', 'Doe', 'jane@doe.com', '123456789');
INSERT INTO UTENTE (ID, USERNAME, NOME, COGNOME, EMAIL, TELEFONO)
VALUES (3, 'joedoe', 'Joe', 'Doe', 'joe@doe.com', '123456789');

-- Evento in casa, nessun invito, una proposta
INSERT INTO EVENTO (ID, DTYPE, NOME, DESCRIZIONE, INDIRIZZO, CITOFONO, ORGANIZZATORE_ID)
VALUES (1, 'EventoInCasa', 'Cena in casa', 'Esempio di evento in casa', 'indirizzo', 'Doe', 1);
INSERT INTO PROPOSTA (ID, EVENTO_ID, DATA)
VALUES (1, 1, '2022-12-31');

-- Evento in locale, un invito, due proposte
INSERT INTO EVENTO (ID, DTYPE, NOME, DESCRIZIONE, LOCALE, ORGANIZZATORE_ID)
VALUES (2, 'EventoInLocale', 'Cena al ristorante', 'Esempio di evento in locale', 'Whatever', 2);
INSERT INTO PROPOSTA (ID, EVENTO_ID, DATA)
VALUES (2, 1, '2022-12-25');
INSERT INTO PROPOSTA (ID, EVENTO_ID, DATA)
VALUES (3, 1, '2022-12-26');
INSERT INTO INVITO (UTENTE_ID, EVENTO_ID)
VALUES (1, 2);