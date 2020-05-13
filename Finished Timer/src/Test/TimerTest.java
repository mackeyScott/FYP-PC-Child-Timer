package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import model.Password;
import model.TimeStamp;

public class TimerTest {
	@Test
	public void PasswordHashes() {
		Password tester = new Password();
		tester.Encrypt("Default1");
		Integer testVal1 = 708924976;
		Integer testVal2 = "123!".hashCode();
		String testVal3 = tester.getPassword();
		
		System.out.println(testVal3);
		
		assertEquals("708924976", testVal3);
		assertEquals(tester.Encrypt("Default1"), testVal1);
		assertEquals(tester.Encrypt("123!"), testVal2);
	}
	
	@Test
	public void TimeStamps() {
		TimeStamp time = new TimeStamp();
		long timeStamp = time.initTime()/1000;
		
		assertEquals(time.getTimeLong(), timeStamp);
		assertEquals(time.getTimeString(), ""+timeStamp);
		time.updateTime();
		assertEquals(time.getTimeLong(), (System.currentTimeMillis()/1000));
	}

}
