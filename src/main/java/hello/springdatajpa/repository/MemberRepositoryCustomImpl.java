package hello.springdatajpa.repository;

import hello.springdatajpa.entity.Member;

import java.util.List;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {
    @Override
    public List<Member> findMemberCustom() {
        return List.of();
    }
}
