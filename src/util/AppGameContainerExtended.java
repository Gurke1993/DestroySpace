package util;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

import de.bplaced.mopfsoft.handler.GameHandler;

public class AppGameContainerExtended extends AppGameContainer{

	private static AppGameContainerExtended instance = null;

	private AppGameContainerExtended(Game game) throws SlickException {
		super(game);
	}

	/** Initiates the instance of this class, if not yet initiated. 
	 * @param mainScreen
	 */
	public static void init() {
		if (instance == null)
		try {
			AppGameContainerExtended.setInstance(new AppGameContainerExtended(GameHandler.getInstance()));
		} catch (SlickException e) {
			Log.error(e);
		}
	}

	/** Returns an instance of this object. There can only be one instance at a time
	 * @return
	 */
	public static AppGameContainerExtended getInstance() {
		return instance;
	}

	/**Sets the current Instance
	 * @param instance
	 */
	private static void setInstance(AppGameContainerExtended instance) {
		AppGameContainerExtended.instance = instance;
	}

}
