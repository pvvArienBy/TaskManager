package by.it_academy.jd2.dao.db;

import by.it_academy.jd2.dao.api.IUserDao;
import by.it_academy.jd2.dao.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

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
                    .where(cb.equal(user.get("id"),id));
            item = em.createQuery(criteria).getSingleResult();

        } catch (NoResultException | NonUniqueResultException e ) {
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

    @Override
    public void remove(Long id) {
        try (EntityManager em = emFactory.createEntityManager()) {
            EntityTransaction tr = em.getTransaction();
            tr.begin();
            UserEntity user = em.find(UserEntity.class, id);

            if (user != null) {
                em.remove(user);
            }

            tr.commit();
        } catch (PersistenceException e) {
            throw new PersistenceException("Ошибка выполнения запроса", e);
            //  throw new RuntimeException("Ошибка выполнения запроса", e);
        }
    }

    @Override
    public void update(UserEntity item) {
        try (EntityManager em = emFactory.createEntityManager()) {
            EntityTransaction tr = em.getTransaction();
            tr.begin();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<UserEntity> query = cb.createQuery(UserEntity.class);
            Root<UserEntity> root = query.from(UserEntity.class);

            query.select(root).where(
                    cb.equal(root.get("id"), item.getId()));

            TypedQuery<UserEntity> typedQuery = em.createQuery(query);
            UserEntity user = typedQuery.getSingleResult();
            if (user != null) {
                user.setFirstName(item.getFirstName());
                user.setLastName(item.getLastName());
                user.setPosition(item.getPosition());
                user.setRole(item.getRole());
                user.setStatus(item.getStatus());

                em.merge(user);
            }

            tr.commit();

        } catch (NoResultException e) {
            throw new EntityNotFoundException("Объект не найден", e);
        } catch (NonUniqueResultException e) {
            throw new NonUniqueResultException("Найдено несколько объектов, уточните запрос", e);
        } catch (PersistenceException e) {
            throw new RuntimeException("Ошибка выполнения запроса", e);
        }
    }
}