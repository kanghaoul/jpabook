package jpabook.start;

import javax.persistence.*;
import java.util.List;

/**
 * @author holyeye
 */
public class JpaMain {

    public static void main(String[] args) {

        //엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성

        EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

        try {


            tx.begin(); //트랜잭션 시작
            //logic(em);  //비즈니스 로직
            //logic_reg(em);
            //logic_mod(em);
            logic_del(em);
            tx.commit();//트랜잭션 커밋 => INSERT SQL을 데이터베이스에 보낸다.

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

    public static void logic(EntityManager em) {

        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("지한");
        member.setAge(2);

        //등록
        em.persist(member);

        //수정
        member.setAge(20);

        //한 건 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember=" + findMember.getUsername() + ", age=" + findMember.getAge());

        //목록 조회
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members.size=" + members.size());

        //삭제
        em.remove(member);

    }

    public static void logic_reg(EntityManager em) {
        String memId1 = "MemberA";
        Member memberA = new Member();
        memberA.setId(memId1);
        memberA.setUsername("하울");
        memberA.setAge(30);

        String memId2 = "MemberB";
        Member memberB = new Member();
        memberB.setId(memId2);
        memberB.setUsername("정희");
        memberB.setAge(31);
        // 비영속 상태. 객체만 생성 된 상태이다.

        em.persist(memberA);
        em.persist(memberB);
        // 여기 까지 영속상태 INSERT SQL을 데이터베이스에 보내지 않는다.
    }

    public static void logic_mod(EntityManager em) {
        // 영속 엔티티 조회
        Member memberB = em.find(Member.class, "MemberB");

        System.out.println(memberB.toString());

        // 영속 엔티티 데이터 수정
        memberB.setAge(30);
    }

    public static void logic_del(EntityManager em) {
        // 삭제 대상 영속성 엔티티 조회
        Member memberA = em.find(Member.class, "MemberA");
        em.remove(memberA); // 엔티티 삭제
    }
}
