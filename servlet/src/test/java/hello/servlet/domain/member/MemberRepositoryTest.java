package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ResourceBundle;

import static org.assertj.core.api.Assertions.*;

class MemberRepositoryTest {

//    MemberRepository memberRepository = new MemberRepository(); 싱글톤 설정으로 객체 생성 불가.
    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach //  테스트가 끝날 때마다
    void afterEach() {
        memberRepository.clearStore();
    }
    
    @Test
    void save() {
        // given
        Member member = new Member("OhJeongGil", 30);

        // when
        Member savedMember = memberRepository.save(member);

        // then
        Member foundMember = memberRepository.findById(savedMember.getId());
        assertThat(foundMember).isEqualTo(savedMember); // assertj

    }


    @Test
    void findAll() {
        // given
        Member member1 = new Member("member1", 20);
        Member member2 = new Member("member2", 30);
        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> memberList = memberRepository.findAll();

        // then
        assertThat(memberList.size()).isEqualTo(2);
        assertThat(memberList).contains(member1, member2);

    }

    
    
    
}
