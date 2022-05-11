//package victor.training.java.advanced;
//
//import org.springframework.test.annotation.Timed;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.support.TransactionTemplate;
//
//import java.beans.Transient;
//
//public class OOP_vs_FP_Patterns {
//
////    @Timed
//    @Transactional // AOP de spring se bazeaza pe creerea de subclase (EXTENDS) care override p-a ta si face commit, fta()
//    public void oop() {
//        INSERT1
//        INSERT2
//    }
//
//    public void fp() {
//        TransactionTemplate transactionTemplate = new TransactionTemplate();
////        transactionTemplate.setPropagationBehaviorName("REQUIRES_NEW");
//        transactionTemplate.executeWithoutResult(s -> doInTx());
//    }
//
//    private void doInTx() {
//        INSERT1
//        INSERT2
//    }
//
//
//}
