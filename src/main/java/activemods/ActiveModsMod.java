package activemods;

import activemods.ui.ModTextInput;
import basemod.*;
import basemod.interfaces.PostInitializeSubscriber;
import activemods.util.*;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglFileHandle;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;
import activemods.model.ActiveMods;

import java.util.*;

@SpireInitializer
public class ActiveModsMod implements PostInitializeSubscriber
{
    public static ModInfo info;
    public static String modID; // Edit your pom.xml to change this

    public static ActiveMods ActiveModsInstance;

    private static float LABEL_X = 475.0f;
    private static float LABEL_Y = 700.0f;

    static
    {
        loadModInfo();
    }

    private static final String resourcesFolder = checkResourcesPath();
    public static final Logger logger = LogManager.getLogger(modID); // Used to output to the console.

    // This is used to prefix the IDs of various objects like cards and relics,
    // to avoid conflicts between different mods using the same name for things.
    public static String makeID(String id)
    {
        return modID + ":" + id;
    }

    // This will be called by ModTheSpire because of the @SpireInitializer annotation at the top of the class.
    public static void initialize()
    {
        new ActiveModsMod();
    }

    public ActiveModsMod()
    {
        // todo: read in custom URL data

        if (ActiveModsInstance == null)
        {
            ActiveModsInstance = new ActiveMods();
        }

        BaseMod.subscribe(this); // This will make BaseMod trigger all the subscribers at their appropriate times.
        logger.info("{} subscribed to BaseMod.", modID);
    }

    @Override
    public void receivePostInitialize()
    {
        Texture badgeTexture = TextureLoader.getTexture(imagePath("badge.png"));

        ModPanel settingsPanel = new ModPanel();

        // todo: mod config page

        ModLabel buttonLabel = new ModLabel("Coming soon!", LABEL_X, LABEL_Y, settingsPanel, (me) ->
        {
            // leaving this for now even though it doesn't do anything
        });

        settingsPanel.addUIElement(buttonLabel);

        BaseMod.registerModBadge(badgeTexture, info.Name, String.join(", ", info.Authors), info.Description, settingsPanel);
    }

    public static String imagePath(String file)
    {
        return resourcesFolder + "/images/" + file;
    }

    /**
     * Checks the expected resources path based on the package name.
     */
    private static String checkResourcesPath()
    {
        String name = ActiveModsMod.class.getName(); //getPackage can be iffy with patching, so class name is used instead.
        int separator = name.indexOf('.');
        if (separator > 0)
        {
            name = name.substring(0, separator);
        }

        FileHandle resources = new LwjglFileHandle(name, Files.FileType.Internal);
        if (resources.child("images").exists())
        {
            return name;
        }

        throw new RuntimeException("\n\tFailed to find resources folder; expected it to be named \"" + name + "\"." +
                " Either make sure the folder under resources has the same name as your mod's package, or change the line\n" +
                "\t\"private static final String resourcesFolder = checkResourcesPath();\"\n" +
                "\tat the top of the " + ActiveModsMod.class.getSimpleName() + " java file.");
    }

    /**
     * This determines the mod's ID based on information stored by ModTheSpire.
     */
    private static void loadModInfo()
    {
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo) ->
        {
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
            {
                return false;
            }
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(), Collections.emptySet());
            return initializers.contains(ActiveModsMod.class.getName());
        }).findFirst();
        if (infos.isPresent())
        {
            info = infos.get();
            modID = info.ID;
        }
        else
        {
            throw new RuntimeException("Failed to determine mod info/ID based on initializer.");
        }
    }
}
