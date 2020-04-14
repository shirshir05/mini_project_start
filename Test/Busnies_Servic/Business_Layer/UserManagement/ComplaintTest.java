package Busnies_Servic.Business_Layer.UserManagement;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class ComplaintTest {

    /**
     * Test - CT1
     */
    @RunWith(Parameterized.class)
    public static class Complaint1{

        public String des;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Hello"},
                    {""},
                    {null}

            });
        }
        public Complaint1(String des) {
            this.des = des;

        }

        @Test
        public void Complaint1Test() {
            Complaint complaint = new Complaint(des);
            assertEquals(complaint.getDescription(),(des));
            assertEquals(complaint.getAnswer(),"Complaint has not been answered");
            assertFalse(complaint.isAnswered());
        }
    }//Complaint1

    /**
     * Test - CT2
     */
    @RunWith(Parameterized.class)
    public static class answerComplaint{

        public String des;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Hello"},
                    {""},
                    {null}

            });
        }
        public answerComplaint(String des) {
            this.des = des;
        }

        @Test
        public void answerComplaintTest() {
            Complaint complaint = new Complaint(des);
            assertEquals(complaint.getDescription(),(des));
            assertEquals(complaint.getAnswer(),"Complaint has not been answered");
            assertFalse(complaint.isAnswered());
            complaint.answerComplaint("ans");
            assertTrue(complaint.isAnswered());
            assertEquals(complaint.getAnswer(),"ans");
        }
    }//answerComplaint





}