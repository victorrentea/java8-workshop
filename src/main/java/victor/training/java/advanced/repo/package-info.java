@NonNullApi
package victor.training.java.advanced.repo;

import org.springframework.lang.NonNullApi;


// daca vrei ca orice metoda din vreun spring repo sa CRAPE in loc sa dea null,
// pune adnotarea awsta pe pachetul unde tii repo-urile Spring Data.


// => daca o metoda de repo declara ca da Order in loc de null va arunca exceptie
// daca vrei ca metoda aceea sa poata intoarce nimic=> Optional<Order>