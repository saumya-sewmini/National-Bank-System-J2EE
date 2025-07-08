package lk.sau.app.auth.ejb;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lk.sau.app.core.model.User;
import lk.sau.app.core.service.UserService;

@Stateless
public class UserSessionBean implements UserService {
    @PersistenceContext(unitName = "BankPU")
    private EntityManager em;

    @Override
    public User getUserById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public User getUserByEmail(String email) {
        return em.createNamedQuery("User.findByEmail", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public void registerUser(User user) {
        em.persist(user);
    }

    @RolesAllowed({"CUSTOMER", "OFFICER", "ADMIN"})
    @Override
    public void updateUser(User user) {
        em.merge(user);
    }

    @RolesAllowed({"CUSTOMER", "OFFICER", "ADMIN"})
    @Override
    public void deleteUser(Long id) {
        em.remove(em.find(User.class, id));
    }

    @Override
    public boolean validate(String email, String password) {
        try {
            System.out.println(email);
            User user = em.createNamedQuery("User.findByEmail", User.class)
                    .setParameter("email", email).getSingleResult();

            return user != null && user.getPassword().equals(password);
        }catch (NoResultException e){
            e.printStackTrace();
        }
        return false;
    }
}
