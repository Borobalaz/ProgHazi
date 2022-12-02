package gravitysim;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestClass {
	
	private ObjectSet os;
	
	@Before
	public void setup() {
		os = new ObjectSet();
		os.addMassObj(new MassObject());
		os.addMovObj(new MovingObject());
	}
	
	@Test
	public void testParseVec2() {
		vec2 test = new vec2(2.0f, 5.0f);
		vec2 tmp = vec2.parseVec2(test.toString());
		Object exp[] = {2.0f, 5.0f};
		Object act[] = {tmp.x, tmp.y};
		Assert.assertArrayEquals(exp, act);
	}
	
	@Test
	public void testObjectSetAdd() {
		
		Assert.assertEquals(2, os.getMassObjects().size());
		Assert.assertEquals(1, os.getMovObjects().size());
	}
	
	@Test
	public void testSerializationSize() throws IOException {
		
		os.save("test");
		ObjectSet test = new ObjectSet();
		test.load("test.xml");
		
		Assert.assertEquals(os.getMassObjects().size(), test.getMassObjects().size());
	}
	
	@Test
	public void testSerializationValuesMassObject() throws IOException {
		
		os.save("test");
		ObjectSet test = new ObjectSet();
		test.load("test.xml");
		
		Assert.assertEquals(os.getMassObjects().get(0).getMass(), test.getMassObjects().get(0).getMass());
		Assert.assertEquals(os.getMassObjects().get(0).getRadius(), test.getMassObjects().get(0).getRadius());
		Assert.assertEquals(os.getMassObjects().get(0).getPos().x, test.getMassObjects().get(0).getPos().x, 0.00001f);
		Assert.assertEquals(os.getMassObjects().get(0).getPos().y, test.getMassObjects().get(0).getPos().y, 0.00001f);
		Assert.assertEquals(os.getMassObjects().get(0).getColor(), test.getMassObjects().get(0).getColor());
	}
	
	@Test
	public void testSerializationValuesMovingObject() throws IOException {
		
		os.save("test");
		ObjectSet test = new ObjectSet();
		test.load("test.xml");
		
		Assert.assertEquals(os.getMovObjects().get(0).getMass(), test.getMovObjects().get(0).getMass());
		Assert.assertEquals(os.getMovObjects().get(0).getRadius(), test.getMovObjects().get(0).getRadius());
		Assert.assertEquals(os.getMovObjects().get(0).getPos().x, test.getMovObjects().get(0).getPos().x, 0.00001f);
		Assert.assertEquals(os.getMovObjects().get(0).getPos().y, test.getMovObjects().get(0).getPos().y, 0.00001f);
		Assert.assertEquals(os.getMovObjects().get(0).getColor(), test.getMovObjects().get(0).getColor());
		Assert.assertEquals(os.getMovObjects().get(0).getVelo(), test.getMovObjects().get(0).getVelo(), 0.00001f);
		Assert.assertEquals(os.getMovObjects().get(0).getDir().x, test.getMovObjects().get(0).getDir().x, 0.00001f);
		Assert.assertEquals(os.getMovObjects().get(0).getDir().y, test.getMovObjects().get(0).getDir().y, 0.00001f);
	}
	
	@Test 
	public void testVec2Addition() {
		
		vec2 a = new vec2(4.5f, 7.25f);
		Assert.assertEquals(a.x + 3, a.plus(new vec2(3,4)).x, 0.00001);
		Assert.assertEquals(a.y + 4, a.plus(new vec2(3,4)).y, 0.00001);
	}
	
	@Test 
	public void testVec2Subtraction() {
		
		vec2 a = new vec2(4.5f, 7.25f);
		Assert.assertEquals(a.x - 3, a.minus(new vec2(3,4)).x, 0.00001);
		Assert.assertEquals(a.y - 4, a.minus(new vec2(3,4)).y, 0.00001);
	}
	
	@Test 
	public void testVec2Dot() {
		
		vec2 a = new vec2(0, 1);
		vec2 b = new vec2(3, 4);
		Assert.assertEquals(4f, a.dot(b), 0.00001);
		
	}
	
	@Test 
	public void testVec2Length() {
		
		vec2 a = new vec2(3, 4);
		Assert.assertEquals(5, a.length(), 0.00001);
		
	}
	
	@Test 
	public void testVec2Normalize() {
		
		vec2 a = new vec2(0, 4);
		Assert.assertEquals(0, a.normalize().x, 0.00001);
		Assert.assertEquals(1, a.normalize().y, 0.00001);
		
	}
	
	@Test 
	public void testObjectSetNotNull() throws IOException {
		
		ObjectSet tmp = new ObjectSet(); // empty
		tmp.save("test2");
		os.load("test2.xml");
		Assert.assertNotNull(os.getMassObjects());
		Assert.assertNotNull(os.getMovObjects());
	}
	
	@Test(expected=NullPointerException.class)
	public void testFileNotFound() throws IOException, FileNotFoundException {
		
		ObjectSet tmp = new ObjectSet(); // empty
		tmp.save("test2");
		os.load("test3.xml");

	}
}
