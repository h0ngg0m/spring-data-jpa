package hello.springdatajpa.entity;

import jakarta.persistence.EntityManager;
import jakarta.transaction.TransactionManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class MemberTest {

    @Autowired
    EntityManager em;

    @Autowired
    PlatformTransactionManager txManager;

    @TestConfiguration
    static class Config {

        @Bean
        public PlatformTransactionManager txManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }

    @Test
    void persist() {
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionAttribute());
        Member member1 = new Member("member1");
        em.persist(member1);

        Member findMember = em.find(Member.class, member1.getId());
        log.info("findMember={}", findMember);
        txManager.commit(status);


        member1.setUsername(null);
        TransactionStatus status2 = txManager.getTransaction(new DefaultTransactionAttribute());
        em.merge(member1);
        txManager.commit(status2);
    }

}