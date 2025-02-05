package activemods.model;

import activemods.ActiveModsConstants;
import com.evacipated.cardcrawl.modthespire.ModInfo;


import com.google.gson.annotations.SerializedName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

import java.util.Map;

import static activemods.ActiveModsMod.modID;

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
    @SerializedName(ActiveModsConstants.IS_WORKSHOP_MOD_KEY)
    public boolean IsSteamWorkshopMod;

    public static final Logger logger = LogManager.getLogger(modID);

    public ActiveModInfo(ModInfo info, Map<String, String> ModUrls)
    {
        this.ModId = info.ID;
        this.Name = info.Name;
        this.ModVersion = new ActiveModVersion(info.ModVersion);
        this.Authors = info.Authors;
        this.Credits = info.Credits;
        this.Description = info.Description;
        this.Dependencies = info.Dependencies;
        this.OptionalDependencies = info.OptionalDependencies;

        this.SteamWorkshopUrl = ModUrls.get(this.ModId);

        if (this.SteamWorkshopUrl == null)
        {
            logger.info("Missing URL for mod {}.  Add it in the ActiveMods config!", this.Name);
            this.IsSteamWorkshopMod = false;
        }
        else
        {
            this.IsSteamWorkshopMod = this.SteamWorkshopUrl.contains(ActiveModsConstants.STEAM_WORKSHOP_BASE_URL);
        }

        logger.debug("Initialized {}", this.Name);
    }
}
