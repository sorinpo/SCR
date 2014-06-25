// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package sorinpo.scr.edu.model;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import sorinpo.scr.edu.model.Info;

privileged aspect Info_Roo_Finder {
    
    public static Long Info.countFindInfoesByUserIdAndYear(Long userId, int year) {
        if (userId == null) throw new IllegalArgumentException("The userId argument is required");
        EntityManager em = Info.entityManager();
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM Info AS o WHERE o.userId = :userId AND o.year = :year", Long.class);
        q.setParameter("userId", userId);
        q.setParameter("year", year);
        return ((Long) q.getSingleResult());
    }
    
    public static TypedQuery<Info> Info.findInfoesByUserIdAndYear(Long userId, int year) {
        if (userId == null) throw new IllegalArgumentException("The userId argument is required");
        EntityManager em = Info.entityManager();
        TypedQuery<Info> q = em.createQuery("SELECT o FROM Info AS o WHERE o.userId = :userId AND o.year = :year", Info.class);
        q.setParameter("userId", userId);
        q.setParameter("year", year);
        return q;
    }
    
    public static TypedQuery<Info> Info.findInfoesByUserIdAndYear(Long userId, int year, String sortFieldName, String sortOrder) {
        if (userId == null) throw new IllegalArgumentException("The userId argument is required");
        EntityManager em = Info.entityManager();
        String jpaQuery = "SELECT o FROM Info AS o WHERE o.userId = :userId AND o.year = :year";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        TypedQuery<Info> q = em.createQuery(jpaQuery, Info.class);
        q.setParameter("userId", userId);
        q.setParameter("year", year);
        return q;
    }
    
}
