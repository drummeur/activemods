package activemods.model;

import activemods.ActiveModsConstants;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.steam.SteamSearch;
import com.evacipated.cardcrawl.modthespire.steam.SteamWorkshop;

import com.google.gson.annotations.SerializedName;

import java.io.*;

import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class ActiveModInfo implements Serializable
{
    @SerializedName(ActiveModsConstants.MODID_KEY)
    public String ModId;
    @SerializedName(ActiveModsConstants.NAME_KEY)
    public String Name;
    @SerializedName(ActiveModsConstants.DESCRIPTION_KEY)
    public String Description;
    @SerializedName(ActiveModsConstants.AUTHORS_KEY)
    public String[] Authors;
    @SerializedName(ActiveModsConstants.CREDITS_KEY)
    public String Credits;
    @SerializedName(ActiveModsConstants.MOD_VERSION_KEY)
    public ActiveModVersion ModVersion;
    @SerializedName(ActiveModsConstants.DEPENDENCIES_KEY)
    public String[] Dependencies;
    @SerializedName(ActiveModsConstants.OPTIONAL_DEPENDENCIES_KEY)
    public String[] OptionalDependencies;
    @SerializedName(ActiveModsConstants.STEAM_WORKSHOP_URL_KEY)
    public String SteamWorkshopUrl;

    public ActiveModInfo(ModInfo info)
    {
        this.ModId = info.ID;
        this.Name = info.Name;
        this.ModVersion = new ActiveModVersion(info.ModVersion);
        this.Authors = info.Authors;
        this.Credits = info.Credits;
        this.Description = info.Description;
        this.Dependencies = info.Dependencies;
        this.OptionalDependencies = info.OptionalDependencies;
        this.SteamWorkshopUrl = this.FindSteamWorkshopUrl(info);
    }

    private String FindSteamWorkshopUrl(ModInfo info)
    {
        return null;
        /*
        String url = null;
        if (info.isWorkshop)
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
                            try (ZipFile zf = new ZipFile(file))
                            {
                                zf.getEntry(ActiveModsConstants.MTS_FILENAME);
                            }
                        }
                    }
                }
                catch (NullPointerException | IOException ex)
                {
                    // do nothing
                }
            }
        }
        return url;
        */
    }
}
