package klh.edu;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class DemoClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Result rs=JUnitCore.runClasses(JunitClass.class);
		for(Failure failure:rs.getFailures()) {
			System.out.println(failure.toString());
		}
System.out.println("Result is"+rs.wasSuccessful());
	}

}
