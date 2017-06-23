import org.junit.Ignore;
import static org.junit.Assert.assertEquals;

/**
 * Created by Hui on 2017/5/26.
 */
public class TestJunit{
    @org.junit.Test(timeout=10)
    @Ignore
    public void a(){
        System.out.println("a");
    }

    @org.junit.Test
    @Ignore
    public void b(){
        assertEquals("1", "1");
        System.out.println("b");
    }
}
