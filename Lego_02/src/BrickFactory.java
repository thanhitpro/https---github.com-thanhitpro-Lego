import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Dictionary;
import java.util.Hashtable;


public class BrickFactory {
	@SuppressWarnings("rawtypes")
	private Dictionary<String, Class> m_RegisterBricks = new Hashtable<String, Class>();
	@SuppressWarnings("rawtypes")
	public void registerBrick (String brickName, Class brickClass) {
		m_RegisterBricks.put(brickName, brickClass);
	}
	
	public Brick createBrick(String brickName) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		//Class brickClass = (Class)m_RegisterBricks.get(brickName);
		//Constructor<Brick> brickConstructor = Class.forName(brickClass.getName());
		@SuppressWarnings("unchecked")
		Class<Brick> brickClass = (Class<Brick>)m_RegisterBricks.get(brickName);
		Constructor<Brick> brickConstructor = brickClass.getConstructor();
		Brick brick = brickConstructor.newInstance();
		return brick;
	}
}
