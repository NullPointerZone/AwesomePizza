package com.awesomepizzasrl.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Come pizzeria "Awesome Pizza" voglio creare il mio nuovo portale per gestire gli ordini dei miei clienti.
 * Il portale non richiede la registrazione dell'utente per poter ordinare le sue pizze.
 * Il pizzaiolo vede la coda degli ordini e li può prendere in carico uno alla volta. Quando la pizza è pronta, il pizzaiolo passa all'ordine successivo.
 * L'utente riceve il suo codice d'ordine e può seguire lo stato dell'ordine fino all'evasione.

 * Come team, procediamo allo sviluppo per iterazioni.
 * Decidiamo che nella prima iterazione non sarà disponibile un'interfaccia grafica,
 * ma verranno create delle API al fine di ordinare le pizze e aggiornarne lo stato.
 * Decidiamo di utilizzare il framework Spring e Java (versione a tua scelta).
 * Decidiamo di progettare anche i test di unità sul codice oggetto di sviluppo.
 */
@SpringBootApplication
@EnableTransactionManagement
public class PlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlatformApplication.class, args);
	}

}
