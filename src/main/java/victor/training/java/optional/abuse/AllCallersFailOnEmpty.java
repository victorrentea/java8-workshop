package victor.training.java.optional.abuse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.lang.reflect.ParameterizedType;
import java.util.NoSuchElementException;

public class AllCallersFailOnEmpty {
  @Entity
  private static class Tenant {
    @Id
    Long id;
    String name;
  }

  private interface BaseRepo<T,PK> extends JpaRepository<T, PK> {
    @SuppressWarnings("unchecked")
    default T findOneById(PK id) {
      return findById(id).orElseThrow(() -> {
        Class<T> persistentClass = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return new NoSuchElementException(persistentClass.getSimpleName() + " with id " + id + " not found ");
      });
    }
  }

  private interface TenantRepo extends BaseRepo<Tenant, Long> {
//  private interface TenantRepo extends JpaRepository<Tenant, Long> {
     Tenant findByName(String name);
     @Query("SELECT x FROM Tenant x WHERE ........")
     Tenant f(String name);
  }


  private TenantRepo tenantRepo;

  public void flow1(long tenantId) {
    // daca o metoda care intoarece optionala ii face pe 100% din calleri sa arunce exceptie imediat
    //  poate e mai curat sa arunci exceptia direct dinauntrul ei. DAR daca ai cea mai mica banuiala ca exista un caz
    //  cand ar putea sa faca si .map sau .orElse
    Tenant tenant = tenantRepo.findOneById(tenantId); // throws if Optional is empty 99.9% din cazuri cand faci findById
    System.out.println("Stuff1 with tenant: " + tenant);
  }

  public void flow2(long tenantId) {
    Tenant tenant = tenantRepo.findOneById(tenantId); // done in 30 more places in code,
    System.out.println("Stuff2 with tenant: " + tenant);
  }

  public void darDacaFolosestiFinderiCUstomSauQueryuriCustom() {
    // e mai probabil sa vrei Optional inapoi ca rezultat.

  }
}
