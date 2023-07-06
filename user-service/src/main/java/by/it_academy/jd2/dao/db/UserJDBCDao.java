package by.it_academy.jd2.dao.db;

import by.it_academy.jd2.dao.api.IUserDao;
import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.service.exceptions.UpdateEntityException;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserJDBCDao implements IUserDao {

    private EntityManagerFactory emFactory;

    public UserJDBCDao(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
    }

    @Override
    public List<UserEntity> get() {
        List<UserEntity> list;

        try (EntityManager em = emFactory.createEntityManager()) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);
            Root<UserEntity> userRoot = criteria.from(UserEntity.class);

            criteria.select(userRoot);

            list = em.createQuery(criteria).getResultList();

        } catch (PersistenceException e) {
            throw new RuntimeException("Ошибка выполнения запроса", e);
        }

        return list;
    }

    @Override
    public UserEntity get(Long id) {
        UserEntity item = new UserEntity();
        try (EntityManager em = emFactory.createEntityManager()) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);
            Root<UserEntity> user = criteria.from(UserEntity.class);
            criteria.select(user)
                    .where(cb.equal(user.get("id"), id));
            item = em.createQuery(criteria).getSingleResult();

        } catch (NoResultException | NonUniqueResultException e) {
            return item;
        } catch (PersistenceException e) {
            throw new RuntimeException("Ошибка выполнения запроса", e);
        }
        return item;
    }

    @Override
    public UserEntity add(UserEntity item) {
        try (EntityManager em = emFactory.createEntityManager()) {
            EntityTransaction tr = em.getTransaction();
            tr.begin();
            em.persist(item);
            tr.commit();
            em.refresh(item);
            return item;
        } catch (PersistenceException e) {
            throw new RuntimeException("Ошибка выполнения запроса", e);
        }
    }

//    @Override
//    public void remove(Long id) {
//        try (EntityManager em = emFactory.createEntityManager()) {
//            EntityTransaction tr = em.getTransaction();
//            tr.begin();
//
//            UserEntity user = em.find(UserEntity.class, id);
//
//            if (user != null) {
//                em.remove(user);
//            }
//
//            tr.commit();
//        } catch (PersistenceException e) {
//            throw new PersistenceException("Ошибка выполнения запроса", e);
//        }
//    }
//

    @Override
    public void remove(UserEntity item) {
        try (EntityManager em = emFactory.createEntityManager()) {
            EntityTransaction tr = em.getTransaction();
            tr.begin();

            UserEntity user = em.find(UserEntity.class, item.getId());

            if (user != null) {
                em.lock(user, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
                em.remove(user);
            }

            tr.commit();
        } catch (OptimisticLockException e) {
            throw new UpdateEntityException("Объект был изменен другим пользователем, попробуйте еще раз", e);
        } catch (PersistenceException e) {
            throw new RuntimeException("Ошибка выполнения запроса", e);
        }
    }


    @Override
    public void update(UserEntity item) {
        try (EntityManager em = emFactory.createEntityManager()) {
            EntityTransaction tr = em.getTransaction();
            tr.begin();

            UserEntity user = em.find(UserEntity.class, item.getId(), LockModeType.OPTIMISTIC);

            if (user != null) {
                user.setFirstName(item.getFirstName());
                user.setLastName(item.getLastName());
                user.setMail(item.getMail());
                user.setTelegram(item.getTelegram());
                user.setPosition(item.getPosition());
                user.setRole(item.getRole());
                user.setStatus(item.getStatus());
                user.setNotificationWay(item.getNotificationWay());

                em.merge(user);
            }

            tr.commit();
        } catch (OptimisticLockException e) {
            throw new UpdateEntityException("Объект был изменен другим пользователем, попробуйте еще раз", e);
        } catch (PersistenceException e) {
            throw new RuntimeException("Ошибка выполнения запроса", e);
        }
    }

    @Override
    public List<UserEntity> filterByName(String text) {
        List<UserEntity> list;

        try (EntityManager em = emFactory.createEntityManager()) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);
            Root<UserEntity> locRoot = criteria.from(UserEntity.class);

            criteria.select(locRoot).where(cb.like(locRoot.get("firstName"), "%" + text + "%"));
            list = em.createQuery(criteria).getResultList();

        } catch (PersistenceException e) {
            throw new RuntimeException("Ошибка выполнения запроса", e);
        }
        return list;
    }
}
