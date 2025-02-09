package activemods.model;

import activemods.ActiveModsConstants;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;

import com.evacipated.cardcrawl.modthespire.steam.SteamSearch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static activemods.ActiveModsMain.modID;

public class ActiveMods
{
    public static final Logger logger = LogManager.getLogger(modID); // Used to output to the console.

    // this will only be populated after the first save
    private List<ActiveModInfo> ActiveMods;
    private Map<String, String> ModUrls;

    private boolean Initialized;

    public ActiveMods()
    {
        this.ActiveMods = new ArrayList<>();
        this.ModUrls = new HashMap<>();

        this.Initialized = false;
    }

    public boolean isInitialized()
    {
        return this.Initialized;
    }

    public List<ActiveModInfo> GetActiveMods()
    {
        if (!this.isInitialized())
        {
            this.PopulateModUrls();
            this.PopulateActiveMods();
            this.Initialized = true;
        }

        return this.ActiveMods;
    }

    public Map<String, String> GetModUrls()
    {
        return this.ModUrls;
    }

    private String InstallPathToUrl(String path)
    {
        Path p = Paths.get(path);
        p = p.getName(p.getNameCount()-1);

        return ActiveModsConstants.STEAM_WORKSHOP_BASE_URL + p;
    }

    private void PopulateModUrls()
    {
        for (SteamSearch.WorkshopInfo ws_info : Loader.getWorkshopInfos())
        {
            try
            {
                File path = new File(ws_info.getInstallPath());

                for (File file : path.listFiles())
                {
                    if (file.isFile() && file.getName().endsWith(".jar"))
                    {
                        ModInfo info = ModInfo.ReadModInfo(file);
                        String key = info.ID;
                        String value = InstallPathToUrl(ws_info.getInstallPath());
                        logger.debug("Put {} = {}", key, value);
                        ModUrls.put(key, value);
                    }
                }
            }
            catch (NullPointerException ex)
            {
                // do nothing
            }
        }

        // todo: scaffold for entering missing URLs
    }

    private void PopulateActiveMods()
    {
        for (ModInfo info: Loader.MODINFOS)
        {
            this.ActiveMods.add(new ActiveModInfo(info, this.ModUrls));
        }
    }


}
