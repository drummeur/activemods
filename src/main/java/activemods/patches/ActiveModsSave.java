package activemods.patches;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.HashMap;

// this entire file is yoinked and modified from https://github.com/daviscook477/BaseMod/blob/master/mod/src/main/java/basemod/patches/com/megacrit/cardcrawl/saveAndContinue/SaveAndContinue/Save.java
// in a perfect world, we just put our own code into that class, because it makes more sense having it there to begin with
@SpirePatch(clz = SaveAndContinue.class, method = "save", paramtypez = {SaveFile.class})
public class ActiveModsSave
{

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"params"}
    )
    public static void Insert(SaveFile save, HashMap<String, Object> params)
    {
        ArrayList<String> modNames = new ArrayList<>();

        for (ModInfo modInfo : Loader.MODINFOS)
        {
            modNames.add(modInfo.Name);
        }

        // todo: figure out if we want to sort in any way.  Right now, it just gives them in roughly the modloader order.
        //modNames.sort(null);

        params.put("activemods:active_mods", modNames);
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
