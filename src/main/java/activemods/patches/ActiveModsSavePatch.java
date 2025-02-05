package activemods.patches;

import activemods.ActiveModsConstants;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static activemods.ActiveModsMod.ActiveModsInstance;
import static activemods.ActiveModsMod.modID;


// this entire file is yoinked and modified from https://github.com/daviscook477/BaseMod/blob/master/mod/src/main/java/basemod/patches/com/megacrit/cardcrawl/saveAndContinue/SaveAndContinue/Save.java
// in a perfect world, we just put our own code into that class, because it makes more sense having it there to begin with
@SpirePatch(clz = SaveAndContinue.class, method = "save", paramtypez = {SaveFile.class})
public class ActiveModsSavePatch
{

    public static final Logger logger = LogManager.getLogger(modID);

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"params"}
    )
    public static void Insert(SaveFile save, HashMap<String, Object> params)
    {
        params.put(ActiveModsConstants.ACTIVE_MODS_KEY, ActiveModsInstance.GetActiveMods());
    }

    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher("com.google.gson.GsonBuilder", "create");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
