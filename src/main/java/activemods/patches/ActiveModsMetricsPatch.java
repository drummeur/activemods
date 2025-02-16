package activemods.patches;

import activemods.ActiveModsConstants;
import activemods.ActiveModsMain;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.metrics.Metrics;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

@SpirePatch(clz = Metrics.class, method = "gatherAllData", paramtypez = {boolean.class, boolean.class, MonsterGroup.class})
public class ActiveModsMetricsPatch
{
    public static final Logger logger = LogManager.getLogger(ActiveModsMain.modID);

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"params"}
    )
    public static void Insert(Metrics metrics, boolean death, boolean trueVictor, MonsterGroup monsters, HashMap<String, Object> params)
    {
        params.put(ActiveModsConstants.ACTIVE_MODS_KEY, ActiveModsMain.GetActiveModInfos());
    }

    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(com.megacrit.cardcrawl.characters.AbstractPlayer.class, "getPrefs");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
