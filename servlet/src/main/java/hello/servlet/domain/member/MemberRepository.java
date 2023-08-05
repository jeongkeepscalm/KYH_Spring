package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/** 동시성 문제가 고려되어 있지 않음. 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려. **/
public class MemberRepository {


    // static 으로 설정을 해놨기에, MemberRepository객체를 아무리 많이 생성해도, store, squence는 하나만 사용됨.
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    // SingleTon 으로 만듦.
    private static final MemberRepository instance = new MemberRepository();
    
    // 무조건 getInstance()를 사용하게 끔 설정.
    public static MemberRepository getInstance() {
        return instance;
    }

    // SingleTon 으로 만들 때는, private 으로 생성자 막음.
    private MemberRepository() {}

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

//    public List<Member> findAll() {
//        List<Member> memberList = new ArrayList<Member>();
//        for (int i = 0; i < store.size(); i++) {
//            Member member = store.get(i);
//            memberList.add(member);
//        }
//        return memberList;
//    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }


}
