# persist vs merge
```java
@Transactional
@Override
public <S extends T> S save(S entity) {
    if (entityInformation.isNew(entity)) {
        em.persist(entity);
        return entity;
    } else {
        return em.merge(entity);
    }
}
```
- 파라미터로 들어온 entity가 새로운 엔티티인지 확인하고, 새로운 엔티티라면 persist를 호출하고, 새로운 엔티티가 아니라면 merge를 호출한다.
- persist는 엔티티를 영속화하는 메서드이다. 엔티티를 영속화한다는 것은 엔티티를 영속성 컨텍스트에 저장하는 것을 의미한다.
- merge는 내부적으로 우선 select 쿼리를 날려서 존재하는 엔티티인지 확인한다.
  - 해당 엔티티가 존재하면 파라미터로 넘어온 엔티티의 값으로 업데이트하고 영속성 컨텍스트에 저장하고 반환한다. (이때 파라미터로 넘어온 엔티티의 값으로 업데이트 되는 것을 주의해야 한다.)
  - 해당 엔티티가 존재하지 않으면 persist 처럼 새로운 엔티티로 인식하고 영속성 컨텍스트에 저장하고 반환한다.
- 엔티티 업데이트가 필요한 경우에는 트랜잭션 커밋 시점에 변경 감지(Dirty Checking)가 일어나도록 하자.
- `결론적으로 merge는 사용하지 말자. persist를 사용하자.`
